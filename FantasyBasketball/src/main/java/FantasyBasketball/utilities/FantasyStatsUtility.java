package FantasyBasketball.utilities;

import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.Client;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.repositories.clientRepository;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.repositories.fantasyStatsRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;



// TODO: current_date and use start_date and end_date of the game, add new data to the already added stats

public class FantasyStatsUtility {

    // Try doing this only once as then copy the data from th first copy

    // Function to start API calls to get player information from BallDontLie API
    public void API_player_stats_importation(fantasyPlayerRepository playerRepo, fantasyGameRepository gameRepo,
                                             fantasyStatsRepository statsRepo, clientRepository clientRepo,
                                             Date end_date) throws IOException {
        // Format for the Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convert Date type to LocalDate
        LocalDate end_date1 = convertToLocalDate(end_date);
        
        // Finding List of Games that happened during the given date
        List<FantasyGame> games_within_dates = gameRepo.findGamesGivenDate(end_date1);
        // if the list has game, then we get the API data
        if (games_within_dates.size()>0) {
            
            // Converting dates to string for API calls
            String start_date_game=(games_within_dates.get(0).getGameStartDate()).format(formatter);
            String end_date_game=(games_within_dates.get(0).getGameEndDate()).format(formatter);
            
            // Getting JSONObject from API calls
            JSONObject json = FantasyLeagueUtility.readJsonFromUrl("https://www.balldontlie.io/api/v1/stats?per_page=100&start_date=" +
                    start_date_game + "&end_date=" + end_date_game);
            
            // Getting Metadata to find no. of pages
            JSONObject API_data = (JSONObject) json.get("meta");
            
            // List of Players who played within this week
            List<FantasyPlayer> players_play_this_week = players_from_games(playerRepo, games_within_dates);
            
            // HashMap of clients to get desired weights
            HashMap<Integer, Client> client_map = clientHashMap(clientRepo, games_within_dates);

            // Looping through all pages of the API
            for (int i = (int) API_data.get("current_page"); i < (int) API_data.get("total_pages"); i++) {
                String url = "https://www.balldontlie.io/api/v1/stats?per_page=100&start_date=" + start_date_game
                        + "&end_date=" + end_date_game + "&page=" + i;
                
                // Putting the url in this function to add stats 
                API_player_to_stats(url, statsRepo, players_play_this_week, games_within_dates, client_map);
            }
        }
    }

    // Function for each page url call to add stats for all players
    public void API_player_to_stats(String url, fantasyStatsRepository statsRepo,
                                    List<FantasyPlayer> players_play_this_week,
                                    List<FantasyGame> games_within_dates,
                                    HashMap<Integer, Client> client_map) throws IOException {
        // Getting JSONObject from API calls
        JSONObject json = FantasyLeagueUtility.readJsonFromUrl(url);
        // Getting player data from json
        JSONArray json_data = (JSONArray) json.get("data");
        // For each player in the page
        for (int i = 0; i < json_data.length(); i++) {
            // Getting stats and ID
            JSONObject players_all_data = (JSONObject) json_data.get(i);
            JSONObject players_info = (JSONObject) players_all_data.get("player");
            Integer ball_api_id = (Integer) players_info.get("id");
            // Confirming that Ball_API_ID is within the players playing this week 
            Integer cur_player_id = getPlayerId(players_play_this_week, ball_api_id);
            // If ball_api_id match confirmed we get a player_id, or -1
            if (cur_player_id != -1) {
                //System.out.println((String) players_info.get("first_name")+" "+(String) players_info.get("last_name"));
                // Creating new Fantasystats for the player and putting information for each stats
                FantasyStats stats = new FantasyStats();
                stats.setPlayer_id(cur_player_id);
                stats.setThree_points((Integer) players_all_data.get("fg3m"));
                stats.setTwo_points((Integer) players_all_data.get("fgm") - (Integer) players_all_data.get("fg3m"));
                stats.setFree_throws((Integer) players_all_data.get("ftm"));
                stats.setRebounds((Integer) players_all_data.get("reb"));
                stats.setAssists((Integer) players_all_data.get("ast"));
                stats.setBlocks((Integer) players_all_data.get("blk"));
                stats.setSteals((Integer) players_all_data.get("stl"));
                stats.setTurnovers((Integer) players_all_data.get("turnover"));
                
                // Finding all the client_id, leagues_id, schedule_id for the same player and updating them to stats
                ArrayList<ArrayList<Integer>> client_league_schedule_info = all_client_league_schedule_info(cur_player_id, games_within_dates);
                for (int j = 0; j < client_league_schedule_info.size(); j++) {
                    ArrayList<Integer> each_list = client_league_schedule_info.get(j);
                    Integer client_id = each_list.get(0);
                    Integer league_id = each_list.get(1);
                    Integer schedule_id = each_list.get(2);
                    // Confirming that the stats already exist, so we can update it
                    Integer stats_id = getStatID(statsRepo, client_id, league_id, schedule_id, cur_player_id);
                    if (stats_id > -1) {
                        // Saving the stats information
                        stats.setStats_id(stats_id);
                        stats.setClient_id(client_id);
                        stats.setLeague_id(league_id);
                        stats.setSchedule_id(schedule_id);
                        
                        // Finding total points through client weights
                        Integer total_points = total_points(client_map.get(client_id), stats);
                        stats.setTot_points(total_points);
                        
                        // Updating the stats
                        statsRepo.save(stats);
                    }
                    //statsRepo.save(stats);
                }
            }
        }
    }

    // Date to LocalDate convertor 
    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // Getting a list of all players from list of games
    public List<FantasyPlayer> players_from_games(fantasyPlayerRepository playerRepo,
                                                  List<FantasyGame> games_within_dates) {
        List<FantasyPlayer> players = new ArrayList<>();
        for (FantasyGame each_game : games_within_dates) {
            Integer client_id = each_game.getClientID();
            Integer league_id = each_game.getLeagueID();
            //Integer schedule_id=each_game.getScheduleID();
            Integer home_id = each_game.getHomeTeamID();

            players.add(getPlayer(playerRepo, each_game.getStartHomePG(), client_id, league_id, home_id));
            players.add(getPlayer(playerRepo, each_game.getStartHomeSG(), client_id, league_id, home_id));
            players.add(getPlayer(playerRepo, each_game.getStartHomeSF(), client_id, league_id, home_id));
            players.add(getPlayer(playerRepo, each_game.getStartHomePF(), client_id, league_id, home_id));
            players.add(getPlayer(playerRepo, each_game.getStartHomeC(), client_id, league_id, home_id));
            players.add(getPlayer(playerRepo, each_game.getHomeBench1(), client_id, league_id, home_id));
            players.add(getPlayer(playerRepo, each_game.getHomeBench2(), client_id, league_id, home_id));

            Integer away_id = each_game.getAwayTeamID();
            players.add(getPlayer(playerRepo, each_game.getStartAwayPG(), client_id, league_id, away_id));
            players.add(getPlayer(playerRepo, each_game.getStartAwaySG(), client_id, league_id, away_id));
            players.add(getPlayer(playerRepo, each_game.getStartAwaySF(), client_id, league_id, away_id));
            players.add(getPlayer(playerRepo, each_game.getStartAwayPF(), client_id, league_id, away_id));
            players.add(getPlayer(playerRepo, each_game.getStartAwayC(), client_id, league_id, away_id));
            players.add(getPlayer(playerRepo, each_game.getAwayBench1(), client_id, league_id, away_id));
            players.add(getPlayer(playerRepo, each_game.getAwayBench2(), client_id, league_id, away_id));

        }
        // Removing Null values form the list
        while (players.remove(null)) {
        }
        return players;

    }

    // From the list of players playing this week and ball_api_id, find the player_id
    Integer getPlayerId(List<FantasyPlayer> players_play_this_week, Integer ball_api_id) {
        // Checking each player with ball_api_id
        for (FantasyPlayer each_player : players_play_this_week) {
            Integer test_id = each_player.getBallapiID();
            if (Objects.equals(test_id, ball_api_id)) {
                return each_player.getPlayerID();
            }
        }
        // Or return -1
        return -1;
    }

    // Given list games and player_id; get client_ID, league_ID, schedule_ID for each player_id
    ArrayList<ArrayList<Integer>> all_client_league_schedule_info(Integer player_id, List<FantasyGame> games_within_dates) {
        ArrayList<ArrayList<Integer>> client_league_sch_info = new ArrayList<>();
        for (FantasyGame each_game : games_within_dates) {
            // Checking player through all positions
            if (Objects.equals(each_game.getStartHomePG(), player_id) ||
                    Objects.equals(each_game.getStartHomeSG(), player_id) ||
                    Objects.equals(each_game.getStartHomeSF(), player_id) ||
                    Objects.equals(each_game.getStartHomePF(), player_id) ||
                    Objects.equals(each_game.getStartHomeC(), player_id) ||
                    Objects.equals(each_game.getHomeBench1(), player_id) ||
                    Objects.equals(each_game.getHomeBench2(), player_id) ||
                    Objects.equals(each_game.getStartAwayPG(), player_id) ||
                    Objects.equals(each_game.getStartAwaySG(), player_id) ||
                    Objects.equals(each_game.getStartAwaySF(), player_id) ||
                    Objects.equals(each_game.getStartAwayPF(), player_id) ||
                    Objects.equals(each_game.getStartAwayC(), player_id) ||
                    Objects.equals(each_game.getAwayBench1(), player_id) ||
                    Objects.equals(each_game.getAwayBench2(), player_id)) {
                ArrayList<Integer> new_list = new ArrayList<>();
                new_list.add(each_game.getClientID());
                new_list.add(each_game.getLeagueID());
                new_list.add(each_game.getScheduleID());
                client_league_sch_info.add(new_list);
            }
        }
        return client_league_sch_info;
    }

    // Getting player from playerRepo using player_id,client_id,league_id,team_id
    FantasyPlayer getPlayer(fantasyPlayerRepository playerRepo, Integer player_id,
                            Integer client_id, Integer league_id, Integer team_id) {

        List<FantasyPlayer> player = playerRepo.findByTemplate(player_id, client_id, team_id, league_id,
                null, null, null, null, null);
        if (player.size()>0){
            return player.get(0);
        }
        return null;
    }

    // Getting stats_id from statsRepo using client_id, league_id, schedule_id, player_id
    Integer getStatID(fantasyStatsRepository statsRepo, Integer client_id,
                      Integer league_id, Integer schedule_id,
                      Integer player_id) {
        List<FantasyStats> stats = statsRepo.findByTemplate(null, player_id, schedule_id, client_id,
                league_id, null, null, null, null, null, null,
                null, null, null);
        if (stats.size() > 0) {
            return stats.get(0).getStats_id();
        }
        return -1;
    }

    // Getting hashmap of clients from list of games_within_dates, with client ID as the key
    HashMap<Integer, Client> clientHashMap(clientRepository clientRepo, List<FantasyGame> games_within_dates) {
        HashMap<Integer, Client> clientMap = new HashMap<>();
        for (FantasyGame each_game : games_within_dates) {
            Integer client_id = each_game.getClientID();
            Client cl1 = clientRepo.findByTemplate(client_id,null,
                    null,null).get(0);
            clientMap.put(client_id,cl1);
        }
        return clientMap;
    }

    // TODO: Test with changes in playerRepo courtesy of Emmanuel
    // Calculating total points through each client weights
    Integer total_points(Client client, FantasyStats stats) {
        double points = (client.getThree_p_weight() * stats.getThree_points())
                + (client.getTwo_p_weight() * stats.getTwo_points()) +
                (client.getFt_weight() * stats.getFree_throws()) +
                (client.getRebound_weight() * stats.getRebounds()) +
                (client.getAssist_weight() * stats.getAssists()) +
                (client.getBlock_weight() * stats.getBlocks()) +
                (client.getTurnover_weight() * stats.getTurnovers())+
                (client.getSteal_weight()*stats.getSteals());
        return (int) Math.round(points);
    }

}
