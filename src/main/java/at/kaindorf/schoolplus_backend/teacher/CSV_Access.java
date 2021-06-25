/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.teacher;

import at.kaindorf.schoolplus_backend.beans.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Macht den Zugriff auf die benötigten CSV-Dateien
 * @author Luca Kern BHIF17
 */
public class CSV_Access
{
  
  private static String filepath = System.getProperty("user.dir")
          + "\\src"
          + "\\main"
          + "\\java"
          + "\\at"
          + "\\kaindorf"
          + "\\schoolplus_backend"
          + "\\teacher"
          + "\\";

  /**
   * Methode, um die Namen aller Lehrer der HTBLA Kaindorf zu bekommen.
   * Ist ein WOrk-Around, da man als Schüler kein Zugriff auf die Lehrer der Schule hat.
   * @return
   * @throws FileNotFoundException
   */
  public static List<String> getTeacherNames() throws FileNotFoundException
  {
    BufferedReader br = new BufferedReader(new FileReader(filepath+"KaindorfLehrer.csv"));

    ArrayList<String> help = (ArrayList<String>) br.lines().skip(1).collect(Collectors.toList());
    
    for (int i = 0; i < help.size(); i++)
    {
      byte[] utf = help.get(i).getBytes();
      help.set(i, new String(utf, StandardCharsets.UTF_8));
    }
    
    return help;
  }

  public static List<Task> getTasks(String user)
  {
    BufferedReader br;
    try
    {
      br = new BufferedReader(new FileReader(filepath+user+".csv"));
    }
    catch(FileNotFoundException e)
    {
      System.out.println(e);
      return new LinkedList<>();
    }

    ArrayList<String> help = (ArrayList<String>) br.lines().skip(1).collect(Collectors.toList());
    LinkedList<Task> tasks = new LinkedList<>();

    for (int i = 0; i < help.size(); i++)
    {
      byte[] utf = help.get(i).getBytes();
      help.set(i, new String(utf, StandardCharsets.UTF_8));

      tasks.add(new Task(help.get(i)));
    }

    return tasks;
  }

  public static void writeTasks(List<Task> tasks, String user) throws FileNotFoundException
  {
    PrintWriter pw = new PrintWriter(new File(filepath+user+".csv"));
    StringBuilder sb = new StringBuilder();

    sb.append("id;name;subject;date;type;done;note;time\n");
    for (Task task:tasks)
    {
      sb.append(task.toString()+"\n");
    }

    pw.write(sb.toString());
    pw.close();
  }
}
