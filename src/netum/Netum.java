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
        //readPlanets();
        //readPeople();
        //readSpecies();
        //readMovies();
        //readVehicles();
        readStarships();
        //kasitteleJson();
        /*JSONObject obj = getJSONObjectFromFile("planeetat.json");
        String[] names = JSONObject.getNames(obj);
        for (String string : names) {
            System.out.println(string);
        }
        */
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
    
    public void readPlanets () {
        String content = readJson("planeetat.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
        System.out.println(results);
        for(int i = 0; i < results.length(); i++){
            JSONObject obj = (JSONObject) results.get(i);
            String name = obj.getString("name");
            String rotation_period = obj.getString("rotation_period");
            String diameter = obj.getString("diameter");
            String climate = obj.getString("climate");
            String gravity = obj.getString("gravity");
            String terrain = obj.getString("terrain");
            String surface_water = obj.getString("surface_water");
            String population = obj.getString("population");
            JSONArray residents = obj.getJSONArray("residents");
            JSONArray films = obj.getJSONArray("films");
            String created = obj.getString("created");
            String edited = obj.getString("edited");
            String url = obj.getString("url");

            
            System.out.print(name + "/" + rotation_period + "/" + diameter);
            System.out.print(climate + "/" + gravity + "/" + terrain);
            System.out.println(surface_water + "/" + population);
            System.out.println(created + "/" + edited + "/" + url);
            System.out.println("Residents: " + residents);
            System.out.println("Films: " + films);
            System.out.println("");
            //System.out.println(obj.getString("name"));
        }
    }
    public void readPeople() {
        String content = readJson("ihmiset.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
        System.out.println(results);
        for(int i = 0; i < results.length(); i++){
            JSONObject obj = (JSONObject) results.get(i);
            String name = obj.getString("name");
            String height = obj.getString("height");
            String mass = obj.getString("mass");
            String hair_color = obj.getString("hair_color");
            String skin_color = obj.getString("skin_color");
            String eye_color = obj.getString("eye_color");
            String birth_year = obj.getString("birth_year");
            String gender = obj.getString("gender");
            String homeworld = obj.getString("homeworld");
   
            JSONArray films = obj.getJSONArray("films");
            JSONArray species = obj.getJSONArray("species");
            JSONArray vehicles = obj.getJSONArray("vehicles");
            JSONArray starships = obj.getJSONArray("starships");
            
            String created = obj.getString("created");
            String edited = obj.getString("edited");
            String url = obj.getString("url");

            
            System.out.println(name + "/" + height + "/" + mass);
            System.out.println(hair_color + "/" + skin_color + "/" + eye_color);
            System.out.println(birth_year + "/" + gender+ "/" + homeworld);
            System.out.println(created + "/" + edited + "/" + url);
            System.out.println("Films: " + films);
            System.out.println("Species: " + species);
            System.out.println("Vehicles: " + vehicles);
            System.out.println("Starships: " + starships);
            System.out.println("");
            //System.out.println(obj.getString("name"));
        }
    }
    public void readSpecies () {
        String content = readJson("lajit.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
        System.out.println(results);
        for(int i = 0; i < results.length(); i++){
            JSONObject obj = (JSONObject) results.get(i);
            String name = obj.getString("name");
            String classification = obj.getString("classification");
            String designation = obj.getString("designation");
            String average_height = obj.getString("average_height");
            String skin_colors = obj.getString("skin_colors");
            String hair_colors = obj.getString("hair_colors");
            String eye_colors = obj.getString("eye_colors");
            String average_lifespan = obj.getString("average_lifespan");
            String homeworld = obj.getString("homeworld");
            String language = obj.getString("language");
            JSONArray people = obj.getJSONArray("people");
            JSONArray films = obj.getJSONArray("films");
            String created = obj.getString("created");
            String edited = obj.getString("edited");
            String url = obj.getString("url");

            
            System.out.println(name + "/" + classification + "/" + designation);
            System.out.println(hair_colors + "/" + skin_colors + "/" + eye_colors);
            System.out.println(average_height + "/" + average_lifespan + "/" + homeworld + "/" + language);
            System.out.println(created + "/" + edited + "/" + url);
            System.out.println("People: " + people);
            System.out.println("Films: " + films);
            System.out.println("");
            //System.out.println(obj.getString("name"));
        }
    }
    
    public void readMovies () {
        String content = readJson("elokuvat.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
        System.out.println(results);
        for(int i = 0; i < results.length(); i++){
            JSONObject obj = (JSONObject) results.get(i);
            String title = obj.getString("title");
            int episode_id = obj.getInt("episode_id");
            String opening_crawl = obj.getString("opening_crawl");
            String director = obj.getString("director");
            String producer = obj.getString("producer");
            String release_date = obj.getString("release_date");

            JSONArray characters = obj.getJSONArray("characters");
            JSONArray planets = obj.getJSONArray("planets");
            JSONArray starships = obj.getJSONArray("starships");
            JSONArray vehicles = obj.getJSONArray("vehicles");
            JSONArray species = obj.getJSONArray("species");
            
            String created = obj.getString("created");
            String edited = obj.getString("edited");
            String url = obj.getString("url");

            
            System.out.println(title + "/" + episode_id + "/" + director + "/" + producer + "/" + release_date);
            System.out.println(created + "/" + edited + "/" + url);
            
            System.out.println("Characters: " + characters);
            System.out.println("Planets: " + planets);
            System.out.println("Starships: " + starships);
            System.out.println("Vehicles: " + vehicles);
            System.out.println("Species: " + species);
            System.out.println("");
            //System.out.println(obj.getString("name"));
        }
    }
    
    public void readVehicles () {
        String content = readJson("ajoneuvot.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
        System.out.println(results);
        for(int i = 0; i < results.length(); i++){
            JSONObject obj = (JSONObject) results.get(i);
            String name = obj.getString("name");
            String model = obj.getString("model");
            String manufacturer = obj.getString("manufacturer");
            String cost_in_credits = obj.getString("cost_in_credits");
            String length = obj.getString("length");
            String max_atmosphering_speed = obj.getString("max_atmosphering_speed");
            String crew = obj.getString("crew");
            String passengers = obj.getString("passengers");
            String cargo_capacity = obj.getString("cargo_capacity");
            String consumables = obj.getString("consumables");
            String vehicle_class = obj.getString("vehicle_class");

            JSONArray pilots = obj.getJSONArray("pilots");
            JSONArray films = obj.getJSONArray("films");
            
            String created = obj.getString("created");
            String edited = obj.getString("edited");
            String url = obj.getString("url");

            
            System.out.println(name + "/" + model + "/" + manufacturer);
            System.out.println(cost_in_credits + "/" + length + "/" + max_atmosphering_speed);
            System.out.println(crew + "/" + passengers + "/" + cargo_capacity + "/" + consumables + "/" + vehicle_class);
            System.out.println(created + "/" + edited + "/" + url);
            
            System.out.println("Pilots: " + pilots);
            System.out.println("Films: " + films);
            System.out.println("");
            //System.out.println(obj.getString("name"));
        }
    }
    
    public void readStarships () {
        String content = readJson("avaruusalukset.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
        System.out.println(results);
        for(int i = 0; i < results.length(); i++){
            JSONObject obj = (JSONObject) results.get(i);
            String name = obj.getString("name");
            String model = obj.getString("model");
            String manufacturer = obj.getString("manufacturer");
            String cost_in_credits = obj.getString("cost_in_credits");
            String length = obj.getString("length");
            String max_atmosphering_speed = obj.getString("max_atmosphering_speed");
            String crew = obj.getString("crew");
            String passengers = obj.getString("passengers");
            String cargo_capacity = obj.getString("cargo_capacity");
            String consumables = obj.getString("consumables");
            String hyperdrive_rating = obj.getString("hyperdrive_rating");
            String MGLT = obj.getString("MGLT");
            String starship_class = obj.getString("starship_class");

            JSONArray pilots = obj.getJSONArray("pilots");
            JSONArray films = obj.getJSONArray("films");
            
            String created = obj.getString("created");
            String edited = obj.getString("edited");
            String url = obj.getString("url");

            
            System.out.println(name + "/" + model + "/" + manufacturer);
            System.out.println(cost_in_credits + "/" + length + "/" + max_atmosphering_speed);
            System.out.println(crew + "/" + passengers + "/" + cargo_capacity + "/" + consumables);
            System.out.println(hyperdrive_rating + "/" + MGLT + "/" + starship_class);
            System.out.println(created + "/" + edited + "/" + url);
            
            System.out.println("Pilots: " + pilots);
            System.out.println("Films: " + films);
            System.out.println("");
            //System.out.println(obj.getString("name"));
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
    /*
    public JSONObject getJSONObjectFromFile(String path) {
        return new JSONObject(readJson());
    }
    */
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
    public String readJson (String file) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
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
