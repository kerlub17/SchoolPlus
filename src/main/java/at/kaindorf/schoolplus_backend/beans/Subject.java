/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.beans;

import at.kaindorf.schoolplus_backend.SchoolPlusController;

/**
 *
 * @author Luca Kern BHIF17
 */
public class Subject
{
  private int id;
  private String name;
  private String longName;

  public Subject()
  {
  }

  public Subject(int id, String name, String longName)
  {
    this.id = id;
    this.name = name;
    this.longName = longName;
  }
  
  public Subject(int id)
  {
    Subject s = SchoolPlusController.getSubjectById(id);
    this.id = id;
    if(s!=null)
    {
      this.longName = s.getLongName();
      this.name = s.getName();
    }
    else
    {
      this.longName = null;
      this.name = null;
    }
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getLongName()
  {
    return longName;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setLongName(String longName)
  {
    this.longName = longName;
  }

  @Override
  public String toString()
  {
    return "Subject{" + "id=" + id + ", name=" + name + ", longName=" + longName + '}';
  }
  
  
}
