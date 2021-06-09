/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.api_methods;

import at.kaindorf.schoolplus_backend.SchoolPlusController;
import at.kaindorf.schoolplus_backend.beans.Lesson;
import at.kaindorf.schoolplus_backend.beans.Subject;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luca Kern BHIF17
 */
public class Day
{
  private static HttpURLConnection con;
  
  public static String exec(long id, int klasse, int type, String date, String school, String sessionId) throws IOException, InterruptedException
  {
      String url = "https://mese.webuntis.com/WebUntis/jsonrpc.do?school="+school;
      ObjectMapper om = new ObjectMapper();

      URL myurl = new URL(url);

      con = (HttpURLConnection) myurl.openConnection();

      con.setDoOutput(true);
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json; utf-8");
      con.setRequestProperty("Accept", "application/json");
      con.setRequestProperty("Cookie", "JSESSIONID="+sessionId);

      Map params = new HashMap<String, String>() {{
          put("id", klasse+"");
          put("type", type+"");
          put("startDate", date);
          put("endDate", date);
      }};

      Map values = new HashMap<String, String>() {{
          put("id", id+"");
          put("method", "getTimetable");
          put("params", om
              .writeValueAsString(params));
          put("jsonrpc", "2.0");
      }};

      String jsonInputString = om.writeValueAsString(values).replace("\\","")
                                                                  .replace("\"{\"", "{\"")
                                                                  .replace("\"}\"", "\"}");

      try(OutputStream os = con.getOutputStream()) 
      {
        byte[] input = jsonInputString.getBytes("utf-8");
        os.write(input, 0, input.length);			
      }

      try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) 
      {
        StringBuilder response = new StringBuilder();
        String responseLine = null;

        while((responseLine = br.readLine()) != null) 
        {
            response.append(responseLine.trim());
        }

        System.out.println("hello");
//        System.out.println(response.toString());
//        if(response.toString().split("\"result\": [")[1].length() != 1)
//        {
//          String[] helper = response.toString().substring(response.toString().indexOf("\"result\": [")+13,response.toString().length()-2).replace("},{ \"id\"","}#;#{\"id\"").split("#;#");
//          System.out.println(helper[0]);
//          System.out.println(helper[helper.length-1]);
//
//          List<Lesson> day = new LinkedList<>();
//          for (int i = 0; i < helper.length; i++)
//          {
//            day.add(om.readValue(helper[i], Lesson.class));
//            System.out.println(day.get(i));
//          }
//          SchoolPlusController.setDay(day);
//        }

        con.disconnect();
        return response.toString();
      }
  }
}
