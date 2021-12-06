package FantasyBasketball.utilities;

import FantasyBasketball.repositories.playerDataRepository;
import FantasyBasketball.services.fantasyLeagueService;
import FantasyBasketball.services.fantasyPlayerService;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.models.FantasyPlayer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class FantasyLeagueUtility {

    // Try doing this only once as then copy the data from th first copy

    // Function to start API calls to get player information from BallDontLie API
    public void API_player_importation(playerDataRepository dataRepo) throws IOException {
        JSONObject json = readJsonFromUrl("https://www.balldontlie.io/api/v1/players?per_page=100");
        JSONObject API_data = (JSONObject) json.get("meta");
        // Grabbing data page by page
        for (int i = (int) API_data.get("current_page"); i < (int) API_data.get("total_pages"); i++){
            String url = "https://www.balldontlie.io/api/v1/players/?per_page=100&page=" + i;
            API_each_page_to_player(url, dataRepo);
        }
    }

    // Function for each page url call to upload player in database
    public void API_each_page_to_player(String url, playerDataRepository dataRepo) throws IOException {
        JSONObject json = readJsonFromUrl(url);
        JSONArray json_data = (JSONArray) json.get("data");
        List<FantasyPlayer> player_page_list=new ArrayList<>();
        // For each player in the page
        for (int i=0;i<json_data.length();i++){
            JSONObject players_info = (JSONObject) json_data.get(i);
            JSONObject players_team_info = (JSONObject) players_info.get("team");

            String pos=(String) players_info.get("position");
            // Current players
            if (!Objects.equals(pos, "") && (players_info.get("height_feet")!= JSONObject.NULL)) {
                FantasyPlayer player = new FantasyPlayer();
                // Construct player
                // TODO: make sure teamID, clientID and leagueID are assigned correctly, using default values for now
                FantasyPlayer new_player = player.setNewPlayer(0, 1, 1,
                        (String) players_info.get("first_name"),
                        (String) players_info.get("last_name"),
                        (String) players_team_info.get("full_name"),
                        (String) players_info.get("position"), 1,
                        (Integer) players_info.get("id"));

                dataRepo.insertDbPlayer(new_player.getPlayerID(),
                        new_player.getPosition(),
                        new_player.getFirstName(),
                        new_player.getLastName(),
                        new_player.getNbaTeam(),
                        new_player.getBallapiID(),
                        0);



                // Save player in list
//                player_page_list.add(new_player);
            }
        }
        // Save player in database
//        playerRepo.saveAll(player_page_list);
    }

    // Convert API response to a String
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    // Read APi response
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
