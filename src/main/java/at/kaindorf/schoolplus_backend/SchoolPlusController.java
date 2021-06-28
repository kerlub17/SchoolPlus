package at.kaindorf.schoolplus_backend;

import at.kaindorf.schoolplus_backend.api_methods.*;
import at.kaindorf.schoolplus_backend.beans.Klasse;
import at.kaindorf.schoolplus_backend.beans.Lesson;
import at.kaindorf.schoolplus_backend.beans.Subject;
import at.kaindorf.schoolplus_backend.beans.Task;
import at.kaindorf.schoolplus_backend.beans.Person;
import at.kaindorf.schoolplus_backend.beans.Room;
import at.kaindorf.schoolplus_backend.bl.CSV_Access;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Managed den Zugriff auf die API und das Mapping für die verschiedenen Services des Programmes.
 * Bildet die Schnittstelle zum Frontend von School+
 * @author Luca Kern BHIF17
 */
@RestController
public class SchoolPlusController
{
  private final AtomicLong counter = new AtomicLong();
  private static String sessionId;

  private static final String SCHOOL_DEFAULT="htbla_kaindorf";
  
  private static String school = "null";
  private static String username = "null";
  private static String password = "null";
  private static String klasse = "null";
  private static String person = "null";
    
  private static List<Subject> subjects = new LinkedList<>();
  private static List<Person> teachers = new LinkedList<>();
  private static List<Klasse> klassen = new LinkedList<>();
  private static List<Room> rooms = new LinkedList<>();
  
  private static List<Lesson> day = new LinkedList<>();

  private static List<Task> tasks = new LinkedList<>();

  public static void setSubjects(List<Subject> subjects)
  {
    SchoolPlusController.subjects = subjects;
  }
  
  public static void setTeachers(List<Person> teachers)
  {
    SchoolPlusController.teachers = teachers;
  }
  
  public static void setKlassen(List<Klasse> klassen)
  {
    SchoolPlusController.klassen = klassen;
  }
  
  public static void setRooms(List<Room> rooms)
  {
    SchoolPlusController.rooms = rooms;
  }
  
  public static void setDay(List<Lesson> day)
  {
    SchoolPlusController.day = day;
  }

  public static void setTasks(List<Task> tasks)
  {
    SchoolPlusController.tasks = tasks;
  }

  /**
   * Damit ein Lehrer nur mit seiner ID herausgefunden werden kann.
   * Man bekommt von der API nur die ID und über eine Liste und CSV kann man die dazugehörige Namen holen.
   * @param id
   * @return
   */
  public static Person getTeacherById(int id)
  {
    for (Person teacher : teachers)
    {
      if(teacher.getId() == id)
      {
        return teacher;
      }
    }
    return null;
  }

  /**
   * Damit ein Lehrer nur mit seiner ID herausgefunden werden kann.
   * Man bekommt von der API nur die ID und über eine Liste kann man die dazugehörige Werte bekommen.
   * @param id
   * @return
   */
  public static Subject getSubjectById(int id)
  {
    for (Subject subject : subjects)
    {
      if(subject.getId() == id)
      {
        return subject;
      }
    }
    return null;
  }

  /**
   * Damit eine Klasse nur mit seiner ID herausgefunden werden kann.
   * Man bekommt von der API nur die ID und über eine Liste kann man die dazugehörige Werte bekommen.
   * @param id
   * @return
   */
  public static Klasse getKlasseById(int id)
  {
    for (Klasse klasse : klassen)
    {
      if(klasse.getId() == id)
      {
        return klasse;
      }
    }
    return null;
  }

  /**
   * Damit ein Raum nur mit seiner ID herausgefunden werden kann.
   * Man bekommt von der API nur die ID und über eine Liste kann man die dazugehörige Werte bekommen.
   * @param id
   * @return
   */
  public static Room getRoomById(int id)
  {
    for (Room room : rooms)
    {
      if(room.getId() == id)
      {
        return room;
      }
    }
    return null;
  }

  /**
   * Methode zum Anlegen eines neuen Tasks (Aufgabe).
   * @param name
   * @param subject
   * @param date
   * @param type
   * @param done
   * @param note
   * @param time
   * @return
   */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/newtask")
    public String newTask(@RequestParam(value = "name", defaultValue = "null") String name,
                          @RequestParam(value = "subject", defaultValue = "null") String subject,
                          @RequestParam(value = "date", defaultValue = "null") String date,
                          @RequestParam(value = "type", defaultValue = "null") String type,
                          @RequestParam(value = "done", defaultValue = "false") String done,
                          @RequestParam(value = "note", defaultValue = "0") int note,
                          @RequestParam(value = "time", defaultValue = "0") int time)
    {
        try
        {
          int id = 1;
          if(tasks.size()!=0)
          {
            id = tasks.get(tasks.size()-1).getId()+1;
          }

          if(date.contains("."))
          {
            String tokens[] = date.split(".");
            date = tokens[2]+tokens[1]+tokens[0];
          }

          done=(done.toLowerCase().equals("ja")?"true":"false");

          tasks.add(new Task(id,name,subject,date,type,Boolean.parseBoolean(done),note,time));
          CSV_Access.writeTasks(tasks,username);

          return "Success - New Task added!";
        }
        catch(Exception e)
        {
          System.out.println(e);
        }
      return error();
    }

  /**
   * Gibtl alle TAsks für jeweiligen User zurück. Es kann auch gefiltert werden.
   * @param name
   * @param subject
   * @param date
   * @param type
   * @param done
   * @param note
   * @param time
   * @return
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/gettasks")
  public String getTasks(@RequestParam(value = "name", defaultValue = "all") String name,
                        @RequestParam(value = "subject", defaultValue = "all") String subject,
                        @RequestParam(value = "date", defaultValue = "all") String date,
                        @RequestParam(value = "type", defaultValue = "all") String type,
                        @RequestParam(value = "done", defaultValue = "all") String done,
                        @RequestParam(value = "note", defaultValue = "all") String note,
                        @RequestParam(value = "time", defaultValue = "all") String time)
  {
    try
    {
      List<Task> output = new LinkedList<>(tasks);

      if(!name.equals("all"))
      {
        output.removeIf(task -> !task.getName().contains(name));
      }

      if(!subject.equals("all"))
      {
        output.removeIf(task -> !task.getSubject().contains(subject));
      }

      if(!date.equals("all"))
      {
        output.removeIf(task -> !task.getDate().equals(date));
      }

      if(!type.equals("all"))
      {
        output.removeIf(task -> !task.getType().equals(type));
      }

      if(!done.equals("all"))
      {
        output.removeIf(task -> task.isDone()!=Boolean.parseBoolean(done));
      }

      if(!note.equals("all"))
      {
        output.removeIf(task -> task.getNote()!=Integer.parseInt(note));
      }

      if(!time.equals("all"))
      {
        output.removeIf(task -> (task.getTime()<Integer.parseInt(time)-30 || task.getTime()>Integer.parseInt(time)+30));
      }

      String str="[";
      //Collections.sort(tasks);
      for (int i = 0; i < tasks.size(); i++)
      {
        str+=tasks.get(i).toFrontend();
        if(i!=tasks.size()-1)
        {
          str+=",";
        }
      }
      return str+"]";
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    return error();
  }



  /**
   * Service-Mapping für die API-Methode und Klasse "Logout".
   * @return
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/logout")
  public String logout()
  {
    try
    {
      String output = Logout.exec(counter.incrementAndGet(), school, sessionId);

      return output;
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    return error();
  }

  /**
   * Methode zum Löschen eines bereits existierenden Tasks
   * @param id
   * @return
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/removetask")
  public String removeTask(@RequestParam(value = "id", defaultValue = "-1") int id)
  {
    try
    {
      boolean check = false;

      for (int i = 0; i < tasks.size(); i++)
      {
        if(tasks.get(i).getId() == id)
        {
          tasks.remove(i);
          check = true;
          break;
        }
      }
      if(!check)
      {
        return "Error - Task with the id " + id + " does not exist";
      }
      CSV_Access.writeTasks(tasks,username);

      return "Success - Task with the id " + id + " has been removed!";
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    return error();
  }

  /**
   * Methode zum Updaten eines bereits existierenden Tasks
   * @param id
   * @param name
   * @param subject
   * @param date
   * @param type
   * @param done
   * @param note
   * @param time
   * @return
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/updatetask")
  public String updateTask(@RequestParam(value = "id", defaultValue = "-1") int id,
                         @RequestParam(value = "name", defaultValue = "null") String name,
                         @RequestParam(value = "subject", defaultValue = "null") String subject,
                         @RequestParam(value = "date", defaultValue = "null") String date,
                         @RequestParam(value = "type", defaultValue = "null") String type,
                         @RequestParam(value = "done", defaultValue = "null") String done,
                         @RequestParam(value = "note", defaultValue = "null") String note,
                         @RequestParam(value = "time", defaultValue = "null") String time)
  {
    try
    {
      int index = -1;
      for (int i = 0; i < tasks.size(); i++)
      {
        if(tasks.get(i).getId()==id)
        {
          index = i;
          break;
        }
      }

      if(index==-1)
      {
        return "Error - Task with the id " + id + " does not exist";
      }
      else
      {
        Task t =  tasks.get(index);
        if(!name.equals("null"))
        {
          t.setName(name);
        }

        if(!subject.equals("null"))
        {
          t.setSubject(subject);
        }

        if(!date.equals("null"))
        {
          t.setDate(date);
        }

        if(!type.equals("null"))
        {
          t.setType(type);
        }

        if(!done.equals("null"))
        {
          t.setDone(Boolean.parseBoolean(done));
        }

        if(!note.equals("null"))
        {
          t.setNote(Integer.parseInt(note));
        }

        if(!time.equals("null"))
        {
          t.setTime(Integer.parseInt(time));
        }

        tasks.set(index,t);
        CSV_Access.writeTasks(tasks,username);
        return "Success - Task with the id " + id + " has been updated!";
      }
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    return error();
  }

  /**
   * Service-Mapping für die API-Methode und Klasse "Auth".
   * Ebenfalls werden andere Funktionen wie "getRooms", "getSubjects", "getTeachers" und "getKlassen" aufgerufen, um die Listen
   * welche für dieses Programm benötigt werden, zu initialisieren.
   * Ebenfalls holt man sich über den Response der API auch die SessionID, welche mfür die weiteren Zugriffe braucht.
   * @return
   */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/auth")
    public String auth(@RequestParam(value = "school", defaultValue = SCHOOL_DEFAULT) String school, 
                       @RequestParam(value = "username", defaultValue = "null") String username,
                       @RequestParam(value = "password", defaultValue = "null") String password)
    {
        if(!school.equals("null") && !username.equals("null") && !password.equals("null"))
        {
          this.school=school;
          this.username=username;
          this.password=password;
          
          try
          {
            String output = Auth.exec(counter.incrementAndGet(), school, username, password);

            int help = output.indexOf("sessionId")+12;
            String sessionID = output.substring(help,help+32);
            this.sessionId=sessionID;

            help = output.indexOf("klasseId")+10;
            String klasse = output.substring(help,help+3);
            this.klasse=klasse;

            help = output.indexOf("personId")+10;
            String person = output.substring(help,help+4).replace("\"","").trim();
            this.person=person;

            //init
            getRooms();
            getSubjects();
            getTeachers();
            getKlassen();
            SchoolPlusController.setTasks(CSV_Access.getTasks(username));
            System.out.println("Initializied!");

            return output;
          }
          catch(Exception e)
          {
            System.out.println(e);
          }
        }
        return error(); 
    }

  /**
   * Service-Mapping für die API-Methode und Klasse "Subjects".
   * @return
   */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getsubjects")
    public String getSubjects()
    {
      if(!school.equals("null"))
      {
        try
        {
          SchoolPlusController.setSubjects(new LinkedList<Subject>());
          Subjects.exec(counter.incrementAndGet(), school, sessionId);

          return "Got Subjects";
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
      }
      return error(); 
    }

  /**
   * Service-Mapping für die API-Methode und Klasse "Teacher".
   * @return
   */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getteachers")
    public String getTeachers()
    {
      if(!school.equals("null"))
      {
        try
        {
          SchoolPlusController.setTeachers(new LinkedList<Person>());
          for (String teacherName : CSV_Access.getTeacherNames())
          {
            String[] help = teacherName.split(";");
            teachers.add(GetPerson.exec(counter.incrementAndGet(), school, sessionId, help[1], help[0], help[2], Integer.parseInt(help[3])));
          }
          return "Got Teachers!";
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
      }
      return error(); 
    }

  /**
   * Service-Mapping für die API-Methode und Klasse "Klasse".
   * @return
   */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getklassen")
    public String getKlassen()
    {
        if(!school.equals("null"))
        {
          try
          {
            SchoolPlusController.setKlassen(new LinkedList<Klasse>());
            Klassen.exec(counter.incrementAndGet(), school, sessionId);

            return "Got Klassen!";
          }
          catch(Exception e)
          {
              System.out.println(e);
          }
        }
        return error(); 
    }

  /**
   * Service-Mapping für die API-Methode und Klasse "Room".
   * @return
   */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getrooms")
    public String getRooms()
    {
      if(!school.equals("null"))
      {
        try
        {
          Rooms.exec(counter.incrementAndGet(), school, sessionId);

          return "Got Rooms";
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
      }
      return error(); 
    }

  /**
   * Service-Mapping für die API-Methode und Klasse "Logout".
   * @return
   */
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/gettimetable")
    public String timetable(@RequestParam(value = "date", defaultValue = "today") String date)
    {
      if(!school.equals("null") && !klasse.equals("null"))
      {
        try
        {
          if(date.contains("."))
          {
            String tokens[] = date.split(".");
            date = tokens[2]+tokens[1]+tokens[0];
          }
          else if(date.equals("today") || date.length()<8)
          {
            date = LocalDate.now().getYear()+"";

            if(LocalDate.now().getMonthValue()<10)
            {
              date+="0";
            }
            date+=LocalDate.now().getMonthValue()+"";

            if(LocalDate.now().getDayOfMonth()<10)
            {
              date+="0";
            }
            date+=LocalDate.now().getDayOfMonth()+"";
          }

          String output = Day.exec(counter.incrementAndGet(), Integer.parseInt(klasse), Integer.parseInt("1"), date, school, sessionId);

          String str="[";
          Collections.sort(day);

          for (int i = 0; i < day.size(); i++)
          {
            str+=day.get(i);
            if(i!=day.size()-1)
            {
              str+=",";
            }
          }
          return str+"]";
        }
        catch(Exception e)
        {
          System.out.println(e);
        }
      }
      return error(); 
    }

  /**
   * Wurde für anfängliche Tests verwendet.
   * @return
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/test")
  public String test()
  {
    return "[{\n" +
            "  \"time\":\"15:35 - 16:20\",\n" +
            "  \"subject\":\"POS;Programmieren und Software Engineering\",\n" +
            "  \"room\":\"4BHIF\",\n" +
            "  \"teacher\":\"Schiffermüller Heinz, Paulus Dietmar\",\n" +
            "  \"activityType\":\"Unterricht\"\n" +
            "},\n"+
            "{\n" +
            "  \"time\":\"15:35 - 16:20\",\n" +
            "  \"subject\":\"POS;Programmieren und Software Engineering\",\n" +
            "  \"room\":\"4BHIF\",\n" +
            "  \"teacher\":\"Schiffermüller Heinz, Paulus Dietmar\",\n" +
            "  \"activityType\":\"Unterricht\"\n" +
            "},\n"+
            "{\n" +
            "  \"time\":\"15:35 - 16:20\",\n" +
            "  \"subject\":\"POS;Programmieren und Software Engineering\",\n" +
            "  \"room\":\"4BHIF\",\n" +
            "  \"teacher\":\"Schiffermüller Heinz, Paulus Dietmar\",\n" +
            "  \"activityType\":\"Unterricht\"\n" +
            "}]";
  }

  /**
   * Wurde für anfängliche Tests verwendet.
   * @return
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/test2")
  public String test2()
  {
    return "{\"id\":1029887,\"date\":20210616,\"startTime\":800,\"endTime\":850,\"kl\":[{\"id\":418}],\"te\":[{\"id\":100}],\"su\":[{\"id\":5}],\"ro\":[{\"id\":178}],\"activityType\":\"Unterricht\"}";
  }

  /**
   * Sollte irgendwo ein Fehler auftreten, wird diese FUnktion angesprochen.
   * @return
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/error")
    public String error()
    {
      return "an error occured";
    }
}
