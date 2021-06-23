/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.beans;

import at.kaindorf.schoolplus_backend.SchoolPlusController;

/**
 * Beansklasse für das Objekt "Subject".
 * Ebenfalls wird hier das Mapping gehandelt, um über eine ID das ganze Fach zu bekommen, mit allen Variablen.
 * @author Luca Kern BHIF17
 */
public class Subject
{
  private int id;
  private String name;
  private String longName;
  private int orgid;

  public Subject()
  {
  }

  public Subject(int id, String name, String longName)
  {
    this.id = id;
    this.name = name;
    this.longName = longName;
  }

  public Subject(int id, String name, String longName, int orgid)
  {
    this.id = id;
    this.name = name;
    this.longName = longName;
    this.orgid=orgid;
  }
  
  public Subject(int id)
  {
    Subject s = SchoolPlusController.getSubjectById(id);
    this.id = id;
    if(s!=null)
    {
      this.longName = s.getLongName();
      this.name = s.getName();
      this.orgid = s.getOrgid();
    }
    else
    {
      this.longName = null;
      this.name = null;
      this.orgid = -1;
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

  public int getOrgid() {
    return orgid;
  }

  public void setOrgid(int orgid) {
    this.orgid = orgid;
  }

  @Override
  public String toString()
  {
    return "Subject{" + "id=" + id + ", name=" + name + ", longName=" + longName + '}';
  }
  
  
}
