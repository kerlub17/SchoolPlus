package at.kaindorf.schoolplus_backend.api_methods;


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
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

//URL webuntis = new URL("https://mese.webuntis.com/WebUntis/jsonrpc.do?school=" + school);
/**
 *
 * @author Luca Kern BHIF17
 */
public class Auth
{
    private static HttpURLConnection con;

    /**
     * Sendet einen HTTP-Post Request an die WebUntis-Api, und gibt das Ergebnis, in diesem FAll einen Auth-Token, zurück.
     * Es wird ein Passwort, ein Username und eine Schule übergeben.
     * @param id
     * @param school
     * @param username
     * @param password
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String exec(long id, String school, String username, String password) throws IOException, InterruptedException
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
        
        Map params = new HashMap<String, String>() {{
            put("user", username);
            put("password", password);
            put("client", "CLIENT");
        }};
        
        Map values = new HashMap<String, String>() {{
            put("id", id+"");
            put("method", "authenticate");
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
          
          con.disconnect();
          return response.toString();
        }
    }
}