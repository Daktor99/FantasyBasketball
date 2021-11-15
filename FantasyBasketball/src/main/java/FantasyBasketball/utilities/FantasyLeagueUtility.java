package FantasyBasketball.utilities;

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
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class FantasyLeagueUtility {

    // Try doing this only once as then copy the data from th first copy


    public void API_player_importation(fantasyPlayerRepository playerRepo) throws IOException {
        JSONObject json = readJsonFromUrl("https://www.balldontlie.io/api/v1/players?per_page=100");
        JSONObject API_data = (JSONObject) json.get("meta");

        for (int i = (int) API_data.get("current_page"); i < (int) API_data.get("total_pages"); i++){
            String url = "https://www.balldontlie.io/api/v1/players/?per_page=100&page=" + i;
            API_each_page_to_player(url, playerRepo);
        }
    }

    public void API_each_page_to_player(String url,fantasyPlayerRepository playerRepo) throws IOException {
        JSONObject json = readJsonFromUrl(url);
        JSONArray json_data = (JSONArray) json.get("data");

        for (int i=0;i<json_data.length();i++){
            JSONObject players_info = (JSONObject) json_data.get(i);
            JSONObject players_team_info = (JSONObject) players_info.get("team");

            String pos=(String) players_info.get("position");

            if (!Objects.equals(pos, "") && (players_info.get("height_feet")!= JSONObject.NULL)) {
                FantasyPlayer player = new FantasyPlayer();
                FantasyPlayer new_player = player.setNewPlayer(0, 1, 1,
                        (String) players_info.get("first_name"),
                        (String) players_info.get("last_name"),
                        (String) players_team_info.get("full_name"),
                        (String) players_info.get("position"), 1,
                        (Integer) players_info.get("id"));
                playerRepo.save(new_player);
            }
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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
}
