package FantasyBasketball.services;

import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.repositories.playerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import FantasyBasketball.exceptions.resourceNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class fantasyPlayerService {

    @Autowired
    private fantasyPlayerRepository playerRepo;

    @Autowired
    private playerDataRepository dataRepo;

    // find by ID
    public List<FantasyPlayer> getByID(Integer playerID) throws resourceNotFoundException {
        Optional<FantasyPlayer> result = playerRepo.findById(playerID);
        if (result.isPresent()) {
            FantasyPlayer fantasyPlayerResult = result.get();
            return List.of(fantasyPlayerResult);
        } else {
            throw new resourceNotFoundException("Player not found by ID in DB.");
        }
    }

    // get operation
    public List<FantasyPlayer> getPlayerByTemplate(Integer player_id,
                                                   Integer client_id,
                                                   Integer team_id,
                                                   String position,
                                                   String first_name,
                                                   String last_name,
                                                   String nba_team,
                                                   Integer league_id,
                                                   Integer ball_api_id) {
        return playerRepo.findByTemplate(player_id,
                client_id,
                team_id,
                league_id,
                ball_api_id,
                first_name,
                last_name,
                nba_team,
                position);
    }

    // post operation
    public List<FantasyPlayer> postFantasyPlayer(FantasyPlayer player) {
        // player.setPlayerID(0);


        // TODO: Update this when client functionality is ready
        //This client_id will be updated later
        //player.setClientID(1);


        playerRepo.insertFantasyPlayer(
                player.getPlayerID(),
                player.getClientID(),
                player.getTeamID(),
                player.getLeagueID(),
                player.getBallapiID());

        List<FantasyPlayer> result = playerRepo.findByTemplate(player.getPlayerID(),
                player.getClientID(),
                player.getTeamID(),
                player.getLeagueID(),
                player.getBallapiID(),
                null,
                null,
                null,
                null);

        return result;
    }

    // put operation
    public List<FantasyPlayer> updateFantasyPlayer(FantasyPlayer player) throws resourceNotFoundException {

        List<FantasyPlayer> playerCheck = playerRepo.findByTemplate(player.getPlayerID(),
                player.getClientID(),
                null,
                player.getLeagueID(),
                null,
                null,
                null,
                null,
                null);

        if(playerCheck.size() == 1) {

            // TODO: update this with the correct client ID
            // TODO: update error message where teamID doesn't exist
            //This client_id will be updated later
            player.setClientID(1);

            playerRepo.updateFantasyPlayer(player.getPlayerID(),
                    player.getClientID(),
                    player.getLeagueID(),
                    player.getTeamID());

            List<FantasyPlayer> result = playerRepo.findByTemplate(player.getPlayerID(),
                    player.getClientID(),
                    player.getTeamID(),
                    player.getLeagueID(),
                    null,
                    null,
                    null,
                    null,
                    null);
            result.get(0).setTeamID(player.getTeamID());
            return result;
        } else {
            throw new resourceNotFoundException("Single player not found by ID in DB, cannot update.");
        }
    }
    // delete operation
    public void deleteFantasyPlayer(Integer player_id, Integer client_id, Integer league_id) throws resourceNotFoundException {

        List<FantasyPlayer> playerCheck = playerRepo.findByTemplate(player_id,
                client_id,
                null,
                league_id,
                null,
                null,
                null,
                null,
                null);

        if(playerCheck.size() == 1) {
            playerRepo.deleteFantasyPlayer(player_id,client_id,league_id);
        } else {
            throw new resourceNotFoundException("Single player not found in DB, cannot delete");
        }
    }
    // Get available players
    public List<FantasyPlayer> getAvailablePlayers(Integer league_id, Integer client_id, String first_name,
                                                          String last_name, String nba_team, String position) {

        // TODO: Enforce that league_id and client_id are present, or else raise exception

        return playerRepo.getAvailablePlayers(league_id, client_id, first_name, last_name, nba_team, position);
    }

    public List<Integer> getUndraftedPlayers(Integer league_id, Integer client_id) throws resourceNotFoundException {

        // TODO: Enforce that league_id and client_id are present, or else raise exception

        List<Integer> player_ids = playerRepo.getUndraftedPlayers(league_id, client_id);
        return player_ids;
    }

    public List<FantasyPlayer> draftFantasyPlayer(FantasyPlayer player) throws resourceNotFoundException {
        return updateFantasyPlayer(player);
    }

    public Integer generateNumber(Integer min, Integer max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
