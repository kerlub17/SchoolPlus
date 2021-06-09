/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.kaindorf.schoolplus_backend.api_methods;

import at.kaindorf.schoolplus_backend.SchoolPlusController;
import at.kaindorf.schoolplus_backend.beans.Subject;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luca Kern BHIF17
 */
public class Subjects
{
  private static HttpURLConnection con;
  
    public static String exec(long id, String school, String sessionId) throws IOException, InterruptedException
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

        Map values = new HashMap<String, String>() {{
            put("id", id+"");
            put("method", "getSubjects");
            put("params", "");
            put("jsonrpc", "2.0");
        }};

        String jsonInputString = om.writeValueAsString(values);

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
          
          String[] helper = response.toString().substring(response.toString().indexOf("[")+1,response.toString().indexOf("]")).replace("},{", "}#;#{").split("#;#");
          
          List<Subject> subjects = new LinkedList<>();
          for (int i = 0; i < helper.length; i++)
          {
            helper[i]=helper[i].split(",\"alternateName")[0]+"}";
            subjects.add(om.readValue(helper[i], Subject.class));
          }
          SchoolPlusController.setSubjects(subjects);
          
          con.disconnect();
          return response.toString();
        }
    }
}
