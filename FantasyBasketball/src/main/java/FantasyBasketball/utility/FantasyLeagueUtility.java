package FantasyBasketball.utility;

import FantasyBasketball.services.fantasyLeagueService;
import FantasyBasketball.services.fantasyPlayerService;
import FantasyBasketball.models.FantasyPlayer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class FantasyLeagueUtility {
    @Autowired
    private fantasyLeagueService leagueService;

    @Autowired
<<<<<<< Updated upstream
    private fantasyPlayerService playerService;
=======
    private static fantasyPlayerService playerService;

>>>>>>> Stashed changes

    // Try doing this only once as then copy the data from th first copy


<<<<<<< Updated upstream
    public void jsontest() throws IOException {
=======
    public static void jsontest() throws IOException {
>>>>>>> Stashed changes
        JSONObject json = readJsonFromUrl("https://www.balldontlie.io/api/v1/players?per_page=100");
        //System.out.println(json.toString());
        JSONObject API_data = (JSONObject) json.get("meta");
        for (int i = (int) API_data.get("current_page"); i < (int) API_data.get("total_pages"); i++){
            String url = "https://www.balldontlie.io/api/v1/players/?per_page=100&page=" + i;
            System.out.println(url);
            json_page(url);
        }
    }

<<<<<<< Updated upstream
    public void json_page(String url) throws IOException {
=======
    public static void json_page(String url) throws IOException {
>>>>>>> Stashed changes
        JSONObject json = readJsonFromUrl(url);
        //System.out.println(json.toString());
        JSONArray json_data = (JSONArray) json.get("data");
        for (int i=0;i<json_data.length();i++){
            JSONObject players_info = (JSONObject) json_data.get(i);
            JSONObject players_team_info = (JSONObject) players_info.get("team");
            FantasyPlayer player = new FantasyPlayer();

            player.setFirstName((String) players_info.get("first_name"));
            player.setLastName((String) players_info.get("last_name"));
            player.setNbaTeam((String) players_team_info.get("full_name"));
            player.setPosition((String) players_info.get("position"));
            playerService.postFantasyPlayer(player);

            System.out.println(players_team_info.get("full_name"));
            System.out.println(players_info.get("last_name"));
            System.out.println(players_info.get("first_name"));
            System.out.println(players_info.get("position"));
        }
    }

<<<<<<< Updated upstream
    private String readAll(Reader rd) throws IOException {
=======
    private static String readAll(Reader rd) throws IOException {
>>>>>>> Stashed changes
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

<<<<<<< Updated upstream
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
=======
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
>>>>>>> Stashed changes
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
