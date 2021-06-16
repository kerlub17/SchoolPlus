package at.kaindorf.schoolplus_backend.api_methods;


import at.kaindorf.schoolplus_backend.SchoolPlusController;
import at.kaindorf.schoolplus_backend.beans.Klasse;
import at.kaindorf.schoolplus_backend.beans.Subject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.URL;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//URL webuntis = new URL("https://mese.webuntis.com/WebUntis/jsonrpc.do?school=" + school);
public class Klassen
{
    private static HttpURLConnection con;
  
    public static String exec(long id, String school, String sessionId) throws IOException, InterruptedException
    {
        String url = "https://mese.webuntis.com/WebUntis/jsonrpc.do?school="+school;
        ObjectMapper om = new ObjectMapper();

        URL myurl = new URL(url);
        con = (HttpURLConnection) myurl.openConnection();

        con.setDoOutput(true);
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Cookie", "JSESSIONID="+sessionId);
        
        Map values = new HashMap<String, String>() {{
            put("id", id+"");
            put("method", "getKlassen");
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

          List<Klasse> klassen = new LinkedList<>();
          for (int i = 0; i < helper.length; i++)
          {
            klassen.add(om.readValue(helper[i], Klasse.class));
          }
          SchoolPlusController.setKlassen(klassen);
          
          con.disconnect();
          return response.toString();
        }
    }
}