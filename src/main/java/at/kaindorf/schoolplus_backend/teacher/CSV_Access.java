/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.teacher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Luca Kern BHIF17
 */
public class CSV_Access
{
  
  private static String filename = System.getProperty("user.dir")
          + "\\src"
          + "\\main"
          + "\\java"
          + "\\at"
          + "\\kaindorf"
          + "\\schoolplus_backend"
          + "\\teacher"
          + "\\KaindorfLehrer.csv";
  public static List<String> getTeacherNames() throws FileNotFoundException
  {
    BufferedReader br = new BufferedReader(new FileReader(filename));

    ArrayList<String> help = (ArrayList<String>) br.lines().skip(1).collect(Collectors.toList());
    
    for (int i = 0; i < help.size(); i++)
    {
      byte[] utf = help.get(i).getBytes();
      help.set(i, new String(utf, StandardCharsets.UTF_8));
    }
    
    return help;
  }
  
  public static void main(String[] args)
  {
    System.out.println(filename);
    try
    {
      System.out.println(getTeacherNames().get(0));
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }
}