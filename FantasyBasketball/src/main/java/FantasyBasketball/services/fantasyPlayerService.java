package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.repositories.playerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class fantasyPlayerService {

    @Autowired
    fantasyPlayerRepository playerRepo;

    @Autowired
    fantasyTeamService teamService;

    @Autowired
    playerDataRepository dataRepo;

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

    // finding players by team
    public List<Integer> getPlayerIDsByTeam(Integer teamID) {
        return playerRepo.getByTeamID(teamID);
    }

    // post operation
    public List<FantasyPlayer> postFantasyPlayer(FantasyPlayer player) {

        playerRepo.insertFantasyPlayer(
                player.getPlayerID(),
                player.getClientID(),
                player.getTeamID(),
                player.getLeagueID(),
                player.getBallapiID());

        List<FantasyPlayer> got_player = playerRepo.findByTemplate(player.getPlayerID(),
                player.getClientID(),
                player.getTeamID(),
                player.getLeagueID(),
                player.getBallapiID(),
                null,
                null,
                null,
                null);
        return got_player;
    }

    // put operation
    public List<FantasyPlayer> updateFantasyPlayer(FantasyPlayer player) throws resourceNotFoundException {

        List<FantasyPlayer> playerCheck = playerRepo.findByTemplate(
                player.getPlayerID(),
                player.getClientID(),
                null,
                player.getLeagueID(),
                null,
                null,
                null,
                null,
                null);

        if(playerCheck.size() == 1) {
            List<FantasyTeam> team = teamService.getByID(player.getTeamID());
            if(!team.isEmpty()) {

                playerRepo.setPlayerTeam(player.getPlayerID(),
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
                throw new resourceNotFoundException("Team not found by ID in DB, cannot update");
            }

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
            playerRepo.deleteFantasyPlayer(player_id, client_id, league_id);
        } else {
            throw new resourceNotFoundException("Single player not found in DB, cannot delete");
        }
    }
    // Get available players
    public List<FantasyPlayer> getAvailablePlayers(Integer league_id, Integer client_id, String first_name,
                                                          String last_name, String nba_team, String position) throws resourceException {

        if (league_id == null) {
            throw new resourceException("LeagueID not provided.");
        }

        else if (client_id == null) {
            throw new resourceException("clientID not provided.");
        }

        return playerRepo.getAvailablePlayers(league_id, client_id, first_name, last_name, nba_team, position);
    }

    public List<Integer> getUndraftedPlayers(Integer league_id, Integer client_id) throws resourceException {

        if (league_id == null) {
            throw new resourceException("LeagueID not provided.");
        }

        else if (client_id == null) {
            throw new resourceException("clientID not provided.");
        }
        return playerRepo.getUndraftedPlayers(league_id, client_id);
    }

    public List<FantasyPlayer> draftFantasyPlayer(FantasyPlayer player) throws resourceNotFoundException {
        return updateFantasyPlayer(player);
    }

    public Integer generateNumber(Integer min, Integer max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
