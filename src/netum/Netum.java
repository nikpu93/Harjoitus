/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import org.json.*;
import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author Niklas
 */
public class Netum {

    Connection con = null;
    
    public Netum () {
        connect();
        //kasitteleJson();
        JSONObject obj = getJSONObjectFromFile("planeetat.json");
        String[] names = JSONObject.getNames(obj);
        for (String string : names) {
            System.out.println(string);
        }
    }
    
    // Funktio tietokanta-yhteyden luomiseksi
    public void connect () {
        // Tietokannan osoite, kayttajanimi ja salasana
        String url = "jdbc:postgresql://localhost/postgres";
        String username = "postgres"; 
        String password = "salasana";  
        
        // Muodostetaan yhteys tietokantaan
        try {
           con = DriverManager.getConnection(url, username, password); 
           System.out.println("Yhteys luotu!");
        } catch (SQLException e) {
        System.out.println("Tapahtui virhe: " + e.getMessage());
        }
    }
    
    public static String getJSONStringFromFile(String path) {
        Scanner scanner;
        InputStream in = inputStreamFromFile(path);
        scanner = new Scanner(in);
        String json = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return json;
    }
    public JSONObject getJSONObjectFromFile(String path) {
        return new JSONObject(readJson());
    }
    public static InputStream inputStreamFromFile(String path) {
        try {
            InputStream inputStream = Netum.class.getResourceAsStream(path);
            return inputStream;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void kasitteleJson() throws IOException {
        /*String jsonData = readJson();
        System.out.println(jsonData);
        JSONObject jobj = new JSONObject(jsonData);
        JSONObject jarr = new JSONObject(jobj.getJSONArray("results"));
        System.out.println("Name: " + JSONObject.getNames("jarr"));
        for (int i = 0; i < jarr.length(); i++){
            System.out.println("Planet: " + jarr.getString(i));
        }
        */
        /*
        URL url = new URL("https://swapi.co/api/planets/");
        try (InputStream is = url.openStream();
            JSONReader rdr = Json.createReader(is)) {
            
            JSONObject obj = rdr.readOnject();
            JSONArray results = obj.getJSONArray("results");
            for (JSONObject result : results.getValueAs(JSONObject.class)) {
                System.out.println(result.getJSONObject("name"));
            }
            
        }
        */
        
    }
    public String readJson () {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("planeetat.json"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public static void main(String args[]) {
        new Netum();
    }
    
}
