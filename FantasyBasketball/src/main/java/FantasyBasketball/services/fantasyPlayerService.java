package FantasyBasketball.services;

import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import FantasyBasketball.exceptions.resourceNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class fantasyPlayerService {

    @Autowired
    private fantasyPlayerRepository playerRepo;

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
                                                   Integer ballapiID) {
        return playerRepo.findByTemplate(player_id,
                client_id,
                team_id,
                position,
                first_name,
                last_name,
                nba_team,
                league_id,
                ballapiID);
    }

    // post operation
    public List<FantasyPlayer> postFantasyPlayer(FantasyPlayer player) {
        player.setPlayerID(0);

        //This client_id will be updated later
        player.setClientID(1);

        FantasyPlayer result = playerRepo.save(player);
        return List.of(result);
    }

    // put operation
    public List<FantasyPlayer> updateFantasyPlayer(FantasyPlayer player) throws resourceNotFoundException {
        if(playerRepo.existsById(player.getPlayerID())) {

            //This client_id will be updated later
            player.setClientID(1);

            FantasyPlayer result = playerRepo.save(player);
            return List.of(result);
        } else {
            throw new resourceNotFoundException("Player not found by ID in DB, cannot update.");
        }
    }
    // delete operation
    public void deleteFantasyPlayerById(Integer player_id) throws resourceNotFoundException {
        if(playerRepo.existsById(player_id)) {
            playerRepo.deleteById(player_id);
        } else {
            throw new resourceNotFoundException("Player not found in DB, cannot delete");
        }
    }

    public List<FantasyPlayer> getAvailablePlayers(Integer team_id, Integer league_id, Integer client_id) {
        return playerRepo.getAvailablePlayers(team_id,
                league_id,
                client_id);
    }


}
