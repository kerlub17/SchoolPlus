/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.beans;

import at.kaindorf.schoolplus_backend.SchoolPlusController;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Luca Kern BHIF17
 */
public class Lesson
{
  private int id;
  private String date;
  private String startTime;
  private String endTime;
  private Klasse[] klasse;
  private Person[] teacher;
  private Subject[] subject;
  private Room[] room;
  private String activityType;
  
 
  public Lesson()
  {
  }

  public Lesson(int id, String date, String startTime, String endTime, Klasse[] kl, Person[] te, Subject[] su, Room[] ro, String activityType)
  {
    this.id = id;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.klasse = kl;
    this.teacher = te;
    for (int i = 0; i < teacher.length; i++)
    {
      teacher[0] = SchoolPlusController.getTeacherById(teacher[0].getId());
    }
    this.subject = su;
    this.room = ro;
    this.activityType = activityType;
  }
  
  
}
