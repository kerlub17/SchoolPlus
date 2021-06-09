/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.beans;

import at.kaindorf.schoolplus_backend.SchoolPlusController;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

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

    for (int i = 0; i < kl.length; i++)
    {
      klasse[i] = SchoolPlusController.getKlasseById(kl[i].getId());
    }

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

  @Override
  public String toString() {
    return "Lesson{" +
            "id=" + id +
            ", date='" + date + '\'' +
            ", startTime='" + startTime + '\'' +
            ", endTime='" + endTime + '\'' +
            ", klasse=" + Arrays.toString(klasse) +
            ", teacher=" + Arrays.toString(teacher) +
            ", subject=" + Arrays.toString(subject) +
            ", room=" + Arrays.toString(room) +
            ", activityType='" + activityType + '\'' +
            '}';
  }
}
