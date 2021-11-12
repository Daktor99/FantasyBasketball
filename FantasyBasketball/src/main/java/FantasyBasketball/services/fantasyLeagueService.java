package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class fantasyLeagueService {

    @Autowired
    fantasyLeagueRepository leagueRepo;

    // find by leagueID
    // TODO: also require clientID?
    public List<FantasyLeague> getByID(Integer leagueID) throws resourceNotFoundException {
        Optional<FantasyLeague> result = leagueRepo.findById(leagueID);
        if (result.isPresent()) {
            FantasyLeague fantasyLeagueResult = result.get();
            return List.of(fantasyLeagueResult);
        } else {
            throw new resourceNotFoundException("League not found by ID in DB.");
        }
    }

    // get operation
    // TODO: CHANGE TO... GET all leagueID's by clientID
    public List<FantasyLeague> getLeaguesByTemplate(Integer league_id,
                                             String league_name,
                                             Integer admin_id,
                                             Integer league_size,
                                             Date league_start_date,
                                             Date league_end_date) {
        return leagueRepo.findByTemplate(league_id,
                league_name,
                admin_id,
                league_size,
                league_start_date,
                league_end_date);
    }

    // post operation
    // TODO: create helper functions to make sure adminID is a real userID
    // TODO: create helper function to make sure start date is a real reasonable future date
    // TODO: create helper function to make sure end date is a real reasonable future date
    // TODO: create helper function to make sure end date is actually after start date (and we're happy with it)
    public List<FantasyLeague> postFantasyLeague(FantasyLeague fantasyLeague) {
        fantasyLeague.setLeagueID(0);
        FantasyLeague result = leagueRepo.save(fantasyLeague);
        return List.of(result);
    }

    // put operation
    // TODO: only allow league name to be changed. ever. nothing else can be changed.
    public List<FantasyLeague> updateLeague(FantasyLeague fantasyLeague) throws resourceNotFoundException {
        if(leagueRepo.existsById(fantasyLeague.getLeagueID())) {
            FantasyLeague result = leagueRepo.save(fantasyLeague);
            return List.of(result);
        } else {
            throw new resourceNotFoundException("User not found by ID in DB, cannot update.");
        }
    }

    // delete operation
    // TODO: only admin is allowed to delete

    // check and sanitize inputs
    // TODO ^

    // check post inputs
    // TODO ^
    // TODO: RIGHT NOW THIS IS JUST COPY PASTED FROM USER
    public void checkPostInputs(FantasyLeague fantasyLeague) throws resourceException {
        if (fantasyLeague.getLeagueID() != 0) {
            throw new resourceException("Do not provide user_id.");
        }
//        checkInputs(user);
    }


    // check put inputs
    // TODO ^
    // TODO: RIGHT NOW THIS IS JUST COPY PASTED FROM USER
    public void checkPutInputs(FantasyLeague fantasyLeague) throws resourceException {
        if (fantasyLeague.getLeagueID() != 0) {
            throw new resourceException("User formatted incorrectly please provide the following:\n" +
                    "user_id, username, email, first_name, last_name");
        }
//        checkInputs(user);
    }

}
