package at.kaindorf.schoolplus_backend.api_methods;


import at.kaindorf.schoolplus_backend.beans.Person;
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

//URL webuntis = new URL("https://mese.webuntis.com/WebUntis/jsonrpc.do?school=" + school);
public class GetPerson
{
    private static HttpURLConnection con;

    /**
     * Sendet einen HTTP-Post Request an die WebUntis-Api, und gibt das Ergebnis, in diesem Fall die ID einer Person, zurück.
     * Diese Id holt man sich über den Name, das Geburtsdatum und den Type der Person (2=Teacher, 5=Student)
     * @param id
     * @param school
     * @param sessionId
     * @param fn
     * @param sn
     * @param dob
     * @param type
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static Person exec(long id, String school, String sessionId, String fn, String sn, String dob, int type) throws IOException, InterruptedException
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

        Map params = new HashMap<String, String>() {{
            put("sn", sn);
            put("fn", fn);
            put("dob", dob);
            put("type", type+"");
        }};
        
        Map values = new HashMap<String, String>() {{
            put("id", id+"");
            put("method", "getPersonId");
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

          Person p = new Person(sn,fn,dob,type);
          String help = response.toString().split("result\":")[1].split("}")[0];
          p.setId(Integer.parseInt(help));
          
          con.disconnect();
          return p;
        }
    }
}