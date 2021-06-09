/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luca Kern BHIF17
 */
public class Timetable
{
  private Map<String, String> timetable = new HashMap<>();

  public Timetable()
  {
  }
  
  public Timetable(List<Lesson> lessons)
  {
    for (Lesson lesson : lessons)
    {
      
    }
  }
}
