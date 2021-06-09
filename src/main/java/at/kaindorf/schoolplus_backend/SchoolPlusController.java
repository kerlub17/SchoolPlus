package at.kaindorf.schoolplus_backend;

import at.kaindorf.schoolplus_backend.api_methods.Auth;
import at.kaindorf.schoolplus_backend.api_methods.GetPerson;
import at.kaindorf.schoolplus_backend.api_methods.Klassen;
import at.kaindorf.schoolplus_backend.api_methods.Rooms;
import at.kaindorf.schoolplus_backend.api_methods.Subjects;
import at.kaindorf.schoolplus_backend.api_methods.Day;
import at.kaindorf.schoolplus_backend.beans.Klasse;
import at.kaindorf.schoolplus_backend.beans.Lesson;
import at.kaindorf.schoolplus_backend.beans.Subject;
import at.kaindorf.schoolplus_backend.beans.Task;
import at.kaindorf.schoolplus_backend.beans.Person;
import at.kaindorf.schoolplus_backend.beans.Room;
import at.kaindorf.schoolplus_backend.teacher.CSV_Access;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolPlusController
{
  private final AtomicLong counter = new AtomicLong();
  private static String sessionId;

  private static final String SCHOOL_DEFAULT="htbla_kaindorf";
  private static final String USERNAME_DEFAULT="kerlub17";
  private static final String PASSWORD_DEFAULT="oxpcasxu";
  
  private static String school = "null";
  private static String username = "null";
  private static String password = "null";
    
  private static List<Subject> subjects = new LinkedList<>();
  private static List<Person> teachers = new LinkedList<>();
  private static List<Klasse> klassen = new LinkedList<>();
  private static List<Room> rooms = new LinkedList<>();
  
  private static List<Lesson> day = new LinkedList<>();
  
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
  
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/task")
    public Task aufgabe(@RequestParam(value = "date", defaultValue = "today") String date)
    {
        if(date.equals("today"))
        {
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString();
        }
        return new Task(counter.incrementAndGet(), date);
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/auth")
    public String auth(@RequestParam(value = "school", defaultValue = SCHOOL_DEFAULT) String school, 
                       @RequestParam(value = "username", defaultValue = USERNAME_DEFAULT) String username, 
                       @RequestParam(value = "password", defaultValue = PASSWORD_DEFAULT) String password)
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
            System.out.println(sessionID);
            this.sessionId=sessionID;

            //init
            getRooms();
            getSubjects();
            getTeachers();
            getKlassen();
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

//          for (Subject subject : subjects)
//          {
//            System.out.println(subject);
//          }

          return "Got Subjects";
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
      }
      return error(); 
    }
    
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
//            System.out.println(teachers.get(teachers.size()-1));
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

//            for (Klasse klasse : klassen)
//            {
//              System.out.println(klasse);
//            }

            return "Got Klassen!";
          }
          catch(Exception e)
          {
              System.out.println(e);
          }
        }
        return error(); 
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getrooms")
    public String getRooms()
    {
      if(!school.equals("null"))
      {
        try
        {
          Rooms.exec(counter.incrementAndGet(), school, sessionId);

//          for (Room room : rooms)
//          {
//            System.out.println(room);
//          }

          return "Got Rooms";
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
      }
      return error(); 
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/xyz")
    public String timetable(@RequestParam(value = "klasse", defaultValue = "418") String klasse, 
                            @RequestParam(value = "type", defaultValue = "1") String type, 
                            @RequestParam(value = "date", defaultValue = "today") String date)
    {
      System.out.println("1");
      
      if(!school.equals("null"))
      {
        try
        {
          System.out.println("2");
          if(date.equals("today") || date.length()<8)
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

          System.out.println("3");

          System.out.println(date);
          String output = Day.exec(counter.incrementAndGet(), Integer.parseInt(klasse), Integer.parseInt(type), date, school, sessionId);

          return output;
        }
        catch(Exception e)
        {
          System.out.println(e);
        }
      }
      return error(); 
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/test")
    public String test(@RequestParam(value = "type", defaultValue = "1") String type)
    {
      if(type.equals("1"))
      {
        return "{\n" +
            "  \"time\":\"15:35 - 16:20\",\n" +
            "  \"subject\":\n" +
            "  {\n" +
            "    \"name\":\"POS\",\n" +
            "    \"longName\":\"Programmieren und Software Engineering\"\n" +
            "  },\n" +
            "  \"room\":\"4BHIF\",\n" +
            "  \"teacher\":\"Schiffermüller Heinz, Paulus Dietmar\",\n" +
            "  \"activityType\":\"Unterricht\"\n" +
            "}";
      }
      else if(type.equals("2"))
      {
        return "{\n" +
              "  \"time\":\"15:35 - 16:20\",\n" +
              "  \"subject\":\"POS;Programmieren und Software Engineering\",\n" +
              "  \"room\":\"4BHIF\",\n" +
              "  \"teacher\":\"Schiffermüller Heinz, Paulus Dietmar\",\n" +
              "  \"activityType\":\"Unterricht\"\n" +
              "}";
      }
      return error();
    }
    
    @GetMapping("/error")
    public String error()
    {
      return "upsi daisy";
    }
}