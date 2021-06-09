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
public class Person
{
  private int id;
  private String sn;
  private String fn;
  private String dob;
  private int type;

  public Person()
  {
  }
  
  public Person(String sn, String fn, String dob, int type)
  {
    this.sn = sn;
    this.fn = fn;
    this.dob = dob;
    this.type = type;
  }

  public Person(int id, String sn, String fn, String dob, int type)
  {
    this.id = id;
    this.sn = sn;
    this.fn = fn;
    this.dob = dob;
    this.type = type;
  }
  
  public Person(int id)
  {
    Person p = SchoolPlusController.getTeacherById(id);
    this.id = id;
    if(p!=null)
    {
      this.sn = p.getSn();
      this.fn = p.getFn();
      this.dob = p.getDob();
      this.type = p.getType();
    }
    else
    {
      this.sn = null;
      this.fn = null;
      this.dob = null;
      this.type = 0;
    }
  }

  public int getId()
  {
    return id;
  }

  public String getSn()
  {
    return sn;
  }

  public String getFn()
  {
    return fn;
  }

  public String getDob()
  {
    return dob;
  }

  public int getType()
  {
    return type;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public void setSn(String sn)
  {
    this.sn = sn;
  }

  public void setFn(String fn)
  {
    this.fn = fn;
  }

  public void setDob(String dob)
  {
    this.dob = dob;
  }

  public void setType(int type)
  {
    this.type = type;
  }

  @Override
  public String toString()
  {
    return "Teacher{" + "id=" + id + ", sn=" + sn + ", fn=" + fn + ", dob=" + dob + ", type=" + type + '}';
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final Person other = (Person) obj;
    if (this.id != other.id)
    {
      return false;
    }
    return true;
  }
  
  
}
