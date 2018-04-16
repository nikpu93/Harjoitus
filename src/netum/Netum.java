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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niklas
 */
public class Netum {

    Connection con = null;
    int PlaneettojaLisatty = 0;
    int LajejaLisatty = 0;
    int IhmisiaLisatty = 0;
    ResultSet rst1 = null;
    
    public Netum () {
        connect();
        //paivitaID();
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
    
    /*
    Funktion tarkoituksena oli päivittää Planeetta, Lajit ja Ihmiset
    tietokantojen ID-muuttujia vastaamaan tietokantaa, jotta se mahdollistaisi
    planeettojen, lajien ja ihmisten lisäämisen tietokantaan. Jostain syystä
    en saanut kyselyistä arvoja (tietokannassa tekemällä sain), joten useiden
    tietokanta lisäysten tekeminen ei tästä syystä toimi. 
    public void paivitaID() {
        try {
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            ResultSet rsetPlaneetta = stmt.executeQuery("SELECT MAX(id) FROM Planeetta");
            ResultSet rsetLaji = stmt1.executeQuery("SELECT MAX(id) FROM Lajit");
            ResultSet rsetIhmiset = stmt1.executeQuery("SELECT MAX(id) FROM Ihmiset");
            
            System.out.println(rsetPlaneetta.getInt(1));
            if (rsetPlaneetta.getObject(1).toString() != null) {
                PlaneettojaLisatty = (rsetPlaneetta.getInt("isoin")) + 1;
            }
            else {
                PlaneettojaLisatty = 0;
            }
            if (rsetLaji.getObject(1).toString() != null) {
                LajejaLisatty = Integer.parseInt(rsetLaji.toString()) + 1;
            }
            else {
                LajejaLisatty = 0;
            }
            if (rsetIhmiset.getObject(1).toString() != null) {
                IhmisiaLisatty = Integer.parseInt(rsetIhmiset.toString());
            }
            else {
                IhmisiaLisatty = 0;
            }
            
        } catch (SQLException ex) {
            System.out.println("Ei päivitä muuttujia...");
        } 
    }
    */
    
    /*
    NetumUI-luokka kutsuu tätä funktioa, jotta se saa käyttöliittymään
    poimittua planeettojen nimet. Kysely poimii kaikkien tietokannassa olevien
    planeettojen nimet ja laittaa ne taulukkoon, jonka funktio palauttaa
    NetumUI-luokalle.
    */
    public String[] getPlanetNames() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rset1 = stmt.executeQuery("SELECT * FROM planeetta");
            String [] nimet = new String [20];
            int i = 0;
            while (rset1.next()) {
                String nimi = rset1.getString("nimi");
                nimet[i] = nimi;
                i= i+1;
            }
            return nimet;
        }
        catch (SQLException ex) {
            return null;   
        }
    }
    
    /*
    NetumUI-luokka kutsuu tätä funktioa, jotta se saa käyttöliittymään
    poimittua lisätietoja planeetasta. Kysely poimii tiedot planeetan
    nimen avulla ja kerää tiedot taulukkoon, jonka funktio palauttaa
    NetumUI-luokalle.
    */
    public String[] getPlanetInfo(String nimi) {
        try {
            Statement stmt = con.createStatement();
            System.out.println(nimi);
            ResultSet rset1 = stmt.executeQuery("SELECT * FROM planeetta WHERE nimi ='" +nimi + "'");
            String [] tiedot = new String [4];
            int i = 0;
            while (rset1.next()) {
                tiedot[0] = rset1.getString("id");
                tiedot[1] = rset1.getString("nimi");
                tiedot[2] = rset1.getString("vakiluku");
                tiedot[3] = rset1.getString("url");
            }
            return tiedot;
        }
        catch (SQLException ex) {
            return null;   
        }
    }
    
    /*
    Funktioa kutsutaan NetumUI-luokasta. Parametrina se saa id:n, nimen, asukasluvun
    ja urlin. Id:n avulla kysely muuttaa nimen, asukasluvun ja urlin tietoja
    parametreina saaduilla arvoilla. 
    */
    public void updatePlanetInfo (String id, String nimi, String asukasluku, String url) {
        try {
            PreparedStatement prstmt1 = con.prepareStatement("UPDATE planeetta SET nimi = ?, vakiluku = ?, url = ?"
                    + " WHERE id ="+Integer.parseInt(id));
            prstmt1.setString(1, nimi);
            prstmt1.setString(2, asukasluku);
            prstmt1.setString(3, url);
            prstmt1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    Funktioa kutsutaan NetumUI-luokasta. Parametrina se saa id:n, jonka avulla
    se poistaa halutun planeetan tietokannan planeetta-taulusta. 
    */
    public void deletePlanetInfo (String id) {
        try {
            PreparedStatement prstmt1 = con.prepareStatement("DELETE FROM planeetta WHERE id ="+Integer.parseInt(id));
            prstmt1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    NetumUI-luokka kutsuu tätä funktioa, jotta se saa käyttöliittymään
    poimittua lajien nimet. Kysely poimii kaikkien tietokannassa olevien
    lajien nimet ja laittaa ne taulukkoon, jonka funktio palauttaa
    NetumUI-luokalle.
    */
    public String[] getSpeciesNames() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rset1 = stmt.executeQuery("SELECT * FROM lajit");
            String [] nimet = new String [20];
            int i = 0;
            while (rset1.next()) {
                String nimi = rset1.getString("nimi");
                nimet[i] = nimi;
                i= i+1;
            }
            return nimet;
        }
        catch (SQLException ex) {
            return null;   
        }
    }
    
    /*
    NetumUI-luokka kutsuu tätä funktioa, jotta se saa käyttöliittymään
    poimittua lisätietoja lajista. Kysely poimii tiedot lajin
    nimen avulla ja kerää tiedot taulukkoon, jonka funktio palauttaa
    NetumUI-luokalle.
    */
    public String[] getSpeciesInfo(String nimi) {
        try {
            Statement stmt = con.createStatement();
            System.out.println(nimi);
            ResultSet rset1 = stmt.executeQuery("SELECT * FROM lajit WHERE nimi ='" +nimi + "'");
            String [] tiedot = new String [4];
            while (rset1.next()) {
                tiedot[0] = rset1.getString("id");
                tiedot[1] = rset1.getString("nimi");
                tiedot[2] = rset1.getString("kotiplaneetta");
                tiedot[3] = rset1.getString("url");
            }
            return tiedot;
        }
        catch (SQLException ex) {
            return null;   
        }
    }
    
    /*
    Funktioa kutsutaan NetumUI-luokasta. Parametrina se saa id:n, nimen, 
    kotiplaneetan ja urlin. Id:n avulla kysely muuttaa nimen, kotiplaneetan 
    ja urlin tietoja parametreina saaduilla arvoilla. 
    */
    public void updateSpeciesInfo (String id, String nimi, String kotiplaneetta, String url) {
        try {
            PreparedStatement prstmt1 = con.prepareStatement("UPDATE lajit SET nimi = ?, kotiplaneetta = ?, url = ?"
                    + " WHERE id ="+Integer.parseInt(id));
            prstmt1.setString(1, nimi);
            prstmt1.setString(2, kotiplaneetta);
            prstmt1.setString(3, url);
            prstmt1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    Funktioa kutsutaan NetumUI-luokasta. Parametrina se saa id:n, jonka avulla
    se poistaa halutun lajin tietokannan lajit-taulusta. 
    */
    public void deleteSpeciesInfo (String id) {
        try {
            PreparedStatement prstmt1 = con.prepareStatement("DELETE FROM lajit WHERE id ="+Integer.parseInt(id));
            prstmt1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* 
    Funktioa kutsutaan NetumUI-luokasta, jonka jälkeen se lataa planeetat,
    ihmiset ja lajit json-tiedostoista sekä laittaa näistä halutut tiedot 
    tietokantaan. Tämä tapahtuu kutsumalla readPlanets, readPeople ja 
    readSpecies funktioita.
    */
    public void lataaJsonit () {
        readPlanets();
        readPeople();
        readSpecies();
        //readMovies();
        //readVehicles();
        //readStarships();
        //kasitteleJson();
    }
    
    /*
    Funktioa kutsutaan NetumUI-luokasta. Parametrina funktio saa nimi, 
    asukasluku(population) ja url muuttujat, jotka se lisää tietokantaan 
    planeetta tauluun. 
    Valitettavasti en saanut id:tä laskettua "paivitaID"-funktiossa, joten 
    tiedon lisääminen ei tapahdu kuin kerran ID:llä 100. 
    */
    public void insertPlanet (String name, String population, String url) {
        try {
            try {
                Statement stmt = con.createStatement();
            }catch (SQLException ex) {
                Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
            }
            //paivitaID();  
            String tiedot = "INSERT INTO Planeetta(id,nimi,vakiluku,url)" +
                "VALUES(?, ?, ?, ?)";
            PreparedStatement prstmt = con.prepareStatement(tiedot);
            prstmt.setInt(1, 100);
            prstmt.setString(2, name);
            prstmt.setString(3, population);
            prstmt.setString(4, url);
            prstmt.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println("Virhe tietokantayhteydessä");
                System.out.println(e);
            }
    }
    
    /*
    Funktioa kutsutaan NetumUI-luokasta. Parametrina funktio saa nimi, 
    synytyny ja url muuttujat, jotka se lisää tietokantaan ihmiset tauluun. 
    Valitettavasti en saanut id:tä laskettua "paivitaID"-funktiossa, joten 
    tiedon lisääminen ei tapahdu kuin kerran ID:llä 100. 
    */
    public void insertPeople (String name, String syntynyt, String url) {
        try {
            try {
                Statement stmt = con.createStatement();
            }catch (SQLException ex) {
                Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
            }
            String tiedot = "INSERT INTO Ihmiset(id,nimi,syntynyt,url)" +
                "VALUES(?, ?, ?, ?)";
            PreparedStatement prstmt = con.prepareStatement(tiedot);
            prstmt.setInt(1, 100);
            prstmt.setString(2, name);
            prstmt.setString(3, syntynyt);
            prstmt.setString(4, url);
            prstmt.executeUpdate();
            
        }
        catch (SQLException e) {
            System.out.println("Virhe tietokantayhteydessä");
        }
    }
    
    /*
    Funktioa kutsutaan NetumUI-luokasta. Parametrina funktio saa nimi, 
    kotiplaneetta ja url muuttujat, jotka se lisää tietokantaan lajit tauluun. 
    Valitettavasti en saanut id:tä laskettua "paivitaID"-funktiossa, joten 
    tiedon lisääminen ei tapahdu kuin kerran ID:llä 100. 
    */
    public void insertSpecies (String name, String kotiplaneetta, String url) {
        try {

            try {
                    Statement stmt = con.createStatement();
            }catch (SQLException ex) {
                Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String tiedot = "INSERT INTO Lajit(id,nimi,kotiplaneetta,url)" +
                    "VALUES(?, ?, ?, ?)";
            PreparedStatement prstmt = con.prepareStatement(tiedot);
            prstmt.setInt(1, 100);
            prstmt.setString(2, name);
            prstmt.setString(3, kotiplaneetta);
            prstmt.setString(4, url);

            prstmt.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println("Virhe tietokantayhteydessä");
                System.out.println(e);
            }
    }
    
    /*
    Luetaan planeetat.json tiedosto ja muodostetaan JSON-objekteista taulukko.
    Taulukossa käsitellään JSON-objektit (eli planeetat) yksi kerrallaan
    ja poimitaan niistä halutut tiedot. Lisäksi ladataan tiedot tietokantaan.
    Seassa tehtävänannon kannalta ylimääräisiä muuttujia
    */
    public void readPlanets () {
        String content = readJson("planeetat.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
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
            try {
                try {
                    Statement stmt = con.createStatement();
                }catch (SQLException ex) {
                    Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
                }
                String tiedot = "INSERT INTO Planeetta(id,nimi,vakiluku,url)" +
                        "VALUES(?, ?, ?, ?)";
                PreparedStatement prstmt = con.prepareStatement(tiedot);
                prstmt.setInt(1, PlaneettojaLisatty);
                prstmt.setString(2, name);
                prstmt.setString(3, population);
                prstmt.setString(4, url);

                prstmt.executeUpdate();
                PlaneettojaLisatty = PlaneettojaLisatty + 1;
            }
            catch (SQLException e) {
                System.out.println("Virhe tietokantayhteydessä");
            }
        }
    }
    /*
    Luetaan ihmiset.json tiedosto ja muodostetaan JSON-objekteista taulukko.
    Taulukossa käsitellään JSON-objektit (eli ihmiset) yksi kerrallaan
    ja poimitaan niistä halutut tiedot. Lisäksi ladataan tiedot tietokantaan.
    Seassa tehtävänannon kannalta ylimääräisiä muuttujia
    */
    public void readPeople() {
        String content = readJson("ihmiset.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
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
            
            try {
                try {
                    Statement stmt = con.createStatement();
                }catch (SQLException ex) {
                    Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
                }
                String tiedot = "INSERT INTO Ihmiset(id,nimi,syntynyt,url)" +
                        "VALUES(?, ?, ?, ?)";
                PreparedStatement prstmt = con.prepareStatement(tiedot);
                prstmt.setInt(1, IhmisiaLisatty);
                prstmt.setString(2, name);
                prstmt.setString(3, birth_year);
                prstmt.setString(4, url);

                prstmt.executeUpdate();
                IhmisiaLisatty = IhmisiaLisatty + 1;
            }
            catch (SQLException e) {
                System.out.println("Virhe tietokantayhteydessä");
            }
        }
    }
    
    /*
    Luetaan lajit.json tiedosto ja muodostetaan JSON-objekteista taulukko.
    Taulukossa käsitellään JSON-objektit (eli lajit) yksi kerrallaan
    ja poimitaan niistä halutut tiedot. Lisäksi ladataan tiedot tietokantaan.
    Seassa tehtävänannon kannalta ylimääräisiä muuttujia
    */
    public void readSpecies () {
        String content = readJson("lajit.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
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
            
            try {
                try {
                    Statement stmt = con.createStatement();
                }catch (SQLException ex) {
                    Logger.getLogger(Netum.class.getName()).log(Level.SEVERE, null, ex);
                }
                String tiedot = "INSERT INTO Lajit(id,nimi,kotiplaneetta,url)" +
                        "VALUES(?, ?, ?, ?)";
                PreparedStatement prstmt = con.prepareStatement(tiedot);
                prstmt.setInt(1, LajejaLisatty);
                prstmt.setString(2, name);
                prstmt.setString(3, homeworld);
                prstmt.setString(4, url);

                prstmt.executeUpdate();
                LajejaLisatty = LajejaLisatty + 1;
            }
            catch (SQLException e) {
                System.out.println("Virhe tietokantayhteydessä");
            }
        }
    }
    /*
    Luetaan elokuvat.json tiedosto ja muodostetaan JSON-objekteista taulukko.
    Taulukossa käsitellään JSON-objektit (elokuvat) yksi kerrallaan
    ja poimitaan niistä halutut tiedot. 
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
    
    */
    /*
    Luetaan ajoneuvot.json tiedosto ja muodostetaan JSON-objekteista taulukko.
    Taulukossa käsitellään JSON-objektit (ajoneuvot) yksi kerrallaan
    ja poimitaan niistä halutut tiedot. 
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
    */
    /*
    Luetaan avaruusalukset.json tiedosto ja muodostetaan JSON-objekteista taulukko.
    Taulukossa käsitellään JSON-objektit (avaruusalukset) yksi kerrallaan
    ja poimitaan niistä halutut tiedot. 
    public void readStarships () {
        String content = readJson("avaruusalukset.json");
        JSONObject a = new JSONObject(content);
        JSONArray results = a.getJSONArray("results");
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
    */
    
    /*
    Lukee Json-filen ja tekee siitä tekstitiedoston, jonka palauttaa muille 
    funktioille käsiteltäväksi. Saa parametrina tiedoston nimen tai polun. 
    */
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
    
    // Ohjelman käynnistyessä asetetaan NetumUI näkyväksi.
    public static void main(String args[]) {
        new NetumUI().setVisible(true);
    }
    
}
