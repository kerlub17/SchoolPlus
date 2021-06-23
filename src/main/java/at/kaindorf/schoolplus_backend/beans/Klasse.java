/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.beans;

import at.kaindorf.schoolplus_backend.SchoolPlusController;

/**
 * Beansklasse für das Objekt "Klasse".
 * Ebenfalls wird hier das Mapping gehandelt, um über eine ID die ganze Klasse zu bekommen, mit allen Variablen.
 * @author Luca Kern BHIF17
 */
public class Klasse
{
  private int id;
  private String name;
  private String longName;
  private Person teacher1;
  private Person teacher2;
  private boolean active;
  private int did;
  private int orgid=-1;

  public Klasse()
  {
  }

  public Klasse(int id, String name, String longName, Person teacher1, Person teacher2, boolean active, int did)
  {
    this.id = id;
    this.name = name;
    this.longName = longName;
    this.teacher1 = teacher1;
    this.teacher2 = teacher2;
    this.active = active;
    this.did = did;
  }

  public Klasse(int id, String name, String longName, Person teacher1, Person teacher2, boolean active, int did, int orgid)
  {
    this.id = id;
    this.name = name;
    this.longName = longName;
    this.teacher1 = teacher1;
    this.teacher2 = teacher2;
    this.active = active;
    this.did = did;
    this.orgid = orgid;
  }

  public Klasse(int id, String name, String longName, Person teacher1, boolean active, int did)
  {
    this.id = id;
    this.name = name;
    this.longName = longName;
    this.teacher1 = teacher1;
    this.active = active;
    this.did = did;
  }

  public Klasse(int id, String name, String longName, boolean active, int did)
  {
    this.id = id;
    this.name = name;
    this.longName = longName;
    this.active = active;
    this.did = did;
  }

  public Klasse(int id)
  {
    Klasse k = SchoolPlusController.getKlasseById(id);
    this.id = id;
    if(k!=null)
    {
      this.name = k.getName();
      this.longName = k.getLongName();
      this.teacher1=k.getTeacher1();
      this.teacher2=k.getTeacher2();
      this.active = k.isActive();
      this.did = k.getDid();
      this.did = k.getOrgid();
    }
    else
    {
      this.name = null;
      this.longName = null;
      this.teacher1=null;
      this.teacher2=null;
      this.active = false;
      this.did = 0;
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

  public Person getTeacher1()
  {
    return teacher1;
  }

  public Person getTeacher2()
  {
    return teacher2;
  }

  public void setTeacher1(Person teacher1)
  {
    this.teacher1 = teacher1;
  }

  public void setTeacher2(Person teacher2)
  {
    this.teacher2 = teacher2;
  }

  public boolean isActive()
  {
    return active;
  }

  public int getDid()
  {
    return did;
  }

  public void setActive(boolean active)
  {
    this.active = active;
  }

  public void setDid(int did)
  {
    this.did = did;
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
    return "Klasse{" + "id=" + id + ", name=" + name + ", longName=" + longName + ", teacher1=" + teacher1 + ", teacher2=" + teacher2 + ", active=" + active + ", did=" + did + '}';
  }
}
