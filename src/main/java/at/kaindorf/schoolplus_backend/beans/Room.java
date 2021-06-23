/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.beans;

import at.kaindorf.schoolplus_backend.SchoolPlusController;

/**
 * Beansklasse für das Objekt "Room".
 * Ebenfalls wird hier das Mapping gehandelt, um über eine ID den ganzen Raum zu bekommen, mit allen Variablen.
 * @author Luca Kern BHIF17
 */
public class Room
{
  private int id;
  private String name;
  private String longName;
  private int orgid=-1;

  public Room()
  {
  }

  public Room(int id, String name, String longName, int orgid)
  {
    this.id = id;
    this.name = name;
    this.longName = longName;
    this.orgid = orgid;
  }

  public Room(int id, String name, String longName)
  {
    this.id = id;
    this.name = name;
    this.longName = longName;
  }
  
  public Room(int id)
  {
    Room r = SchoolPlusController.getRoomById(id);
    this.id = id;
    if(r!=null)
    {
      this.longName = r.getLongName();
      this.name = r.getName();
      this.orgid = r.getOrgid();
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
    return "Room{" + "id=" + id + ", name=" + name + ", longName=" + longName + '}';
  }
  
  
}
