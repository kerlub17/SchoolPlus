/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.beans;

import at.kaindorf.schoolplus_backend.SchoolPlusController;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Locale;

/**
 * Beans-Klasse für das Objekt "Lesson". Das Ergbenis der API wird hier gemappt und dann werden aus den IDs für Klassen die ganzen Klassen etc. geholt.
 * @author Luca Kern BHIF17
 */
public class Lesson implements Comparable<Lesson>
{
  private int id;
  private String date;
  private String startTime;
  private String endTime;
  @JsonAlias("kl")
  private Klasse[] klasse;
  @JsonAlias("te")
  private Person[] teacher;
  @JsonAlias("su")
  private Subject[] subject;
  @JsonAlias("ro")
  private Room[] room;
  private String activityType;
  private String code="null";
  
 
  public Lesson()
  {
  }

  public Lesson(int id, String date, String startTime, String endTime, Klasse[] kl, Person[] te, Subject[] su, Room[] ro, String activityType, String code)
  {
    System.out.println(123);
    this.id = id;
    this.date = date;

    if(startTime.length()==3)
    {
      this.startTime = "0"+startTime;
    }
    else
    {
      this.startTime = startTime;
    }

    if(endTime.length()==3)
    {
      this.endTime = "0"+endTime;
    }
    else
    {
      this.endTime = endTime;
    }

    klasse=kl;

    for (int i = 0; i < te.length; i++)
    {
      teacher[i] = SchoolPlusController.getTeacherById(te[i].getId());
    }

    for (int i = 0; i < su.length; i++)
    {
      subject[i] = SchoolPlusController.getSubjectById(su[i].getId());
    }

    for (int i = 0; i < ro.length; i++)
    {
      room[i] = SchoolPlusController.getRoomById(ro[i].getId());
    }

    this.activityType = activityType;

    this.code = code;
  }

  //Ohne Code
  public Lesson(int id, String date, String startTime, String endTime, Klasse[] kl, Person[] te, Subject[] su, Room[] ro, String activityType)
  {
    System.out.println(123);
    this.id = id;
    this.date = date;

    if(startTime.length()==3)
    {
      this.startTime = "0"+startTime;
    }
    else
    {
      this.startTime = startTime;
    }

    if(endTime.length()==3)
    {
      this.endTime = "0"+endTime;
    }
    else
    {
      this.endTime = endTime;
    }

    klasse=kl;

    for (int i = 0; i < te.length; i++)
    {
      teacher[i] = SchoolPlusController.getTeacherById(te[i].getId());
    }

    for (int i = 0; i < su.length; i++)
    {
      subject[i] = SchoolPlusController.getSubjectById(su[i].getId());
    }

    for (int i = 0; i < ro.length; i++)
    {
      room[i] = SchoolPlusController.getRoomById(ro[i].getId());
    }

    this.activityType = activityType;
  }

  public int getId() {
    return id;
  }

  public String getDate() {
    return date;
  }

  public String getStartTime() {
    return startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public Klasse[] getKlasse() {
    return klasse;
  }

  public Person[] getTeacher() {
    return teacher;
  }

  public Subject[] getSubject() {
    return subject;
  }

  public Room[] getRoom() {
    return room;
  }

  public String getActivityType() {
    return activityType;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setStartTime(String startTime)
  {
    if(startTime.length()==3)
    {
      this.startTime = "0"+startTime;
    }
    else
    {
      this.startTime = startTime;
    }
  }

  public void setEndTime(String endTime)
  {
    if(endTime.length()==3)
    {
      this.endTime = "0"+endTime;
    }
    else
    {
      this.endTime = endTime;
    }
  }

  public void setKlasse(Klasse[] klasse)
  {
    for (int i = 0; i < klasse.length; i++)
    {
      klasse[i] = new Klasse(klasse[i].getId());
    }
    this.klasse = klasse;
  }

  public void setTeacher(Person[] teacher)
  {
    for (int i = 0; i < teacher.length; i++)
    {
      teacher[i] = new Person(teacher[i].getId());
    }
    this.teacher = teacher;
  }

  public void setSubject(Subject[] subject)
  {
    for (int i = 0; i < subject.length; i++)
    {
      subject[i] = new Subject(subject[i].getId());
    }
    this.subject = subject;
  }

  public void setRoom(Room[] room)
  {
    for (int i = 0; i < room.length; i++)
    {
      room[i] = new Room(room[i].getId());
    }
    this.room = room;
  }

  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Vereint das start und end Time einer Lesson in einen schönen String, welcher an das Frontend geliefert wird.
   * @return
   */
  public String fancyDate()
  {
    String str="";

    str+=startTime.substring(0,2);
    str+=":";
    str+=startTime.substring(2);

    str+=" - ";

    str+=endTime.substring(0,2);
    str+=":";
    str+=endTime.substring(2);

    return str;
  }

  /**
   * Fügt alle Klassennamen einer Lesson in einen String zusammen, welcher dan ans Frotnend geliefert wird.
   * @return
   */
  public String getKlasseNames()
  {
    String str = "";

    for (int i = 0; i < klasse.length; i++) {
      str += klasse[i].getName();
      if (i != (klasse.length - 1)) {
        str += ", ";
      }
    }

    return str;
  }

  /**
   * Fügt alle Lehrernamen einer Lesson in einen String zusammen, welcher dan ans Frotnend geliefert wird.
   * @return
   */
  public String getTeacherNames()
  {
    String str="";

    for (int i = 0; i < teacher.length; i++)
    {
      str+=teacher[i].getSn()+" "+teacher[i].getFn();
      if(i!=(teacher.length-1))
      {
        str+=", ";
      }
    }

    return str;
  }

  /**
   * Fügt alle Fächernamen einer Lesson in einen String zusammen, welcher dan ans Frotnend geliefert wird.
   * @return
   */
  public String getSubjectNames()
  {
    String str = "";

    for (int i = 0; i < subject.length; i++) {
      str += subject[i].getName();
      if (i != (subject.length - 1)) {
        str += ", ";
      }
    }

    return str;
  }


  /**
   * Erstellt ein schönes Jso-Objekt, mit welchem das Arbeiten im Frontend erleichtert wird und
   * alle wichtigen DAten fürs Frontend enthalten sind
   * @return
   */
  @Override
  public String toString() {
    return "{\n" +
            "  \"time\":\"" + fancyDate() + "\",\n" +
            "  \"subject\":\"" + getSubjectNames() + "\",\n" +
            "  \"room\":\"4BHIF\",\n" +
            "  \"teacher\":\"" + getTeacherNames() + "\",\n" +
            "  \"activityType\":\"" + activityType + (!code.equals("null")?(", " + code.toUpperCase()):"") + "\"\n" +
            "}\n";
  }

  /**
   * Wird benötigt, da die Stunden von der API nicht in der richtigen Reihenfolge sind.
   * @param o
   * @return
   */
  @Override
  public int compareTo(Lesson o) {
    return this.startTime.compareTo(o.startTime);
  }
}
