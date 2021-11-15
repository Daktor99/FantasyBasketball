package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.repositories.userRepository;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.utilities.FantasyLeagueUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class fantasyLeagueService {

    @Autowired
    userRepository userRepo;

    @Autowired
    fantasyLeagueRepository leagueRepo;

    @Autowired
    fantasyPlayerRepository playerRepo;

    @Autowired
    fantasyTeamRepository teamRepo;

    @Autowired
    fantasyGameRepository gameRepo;

    // helper function: get clientID
    // TODO: Get clientID from something like... session?
    public Integer getClientID() {
        return 0;
    }

    // helper function: find by leagueID
    public List<FantasyLeague> getLeaguesByID(Integer leagueID) throws resourceNotFoundException {
        Optional<FantasyLeague> result = leagueRepo.findById(leagueID);
        if (result.isPresent()) {
            FantasyLeague fantasyLeagueResult = result.get();
            return List.of(fantasyLeagueResult);
        } else {
            throw new resourceNotFoundException("League not found by ID in DB.");
        }
    }

    // get operation
    public List<FantasyLeague> getLeaguesByTemplate(Integer league_id,
                                                    Integer client_id,
                                                    String league_name,
                                                    Integer admin_id,
                                                    Integer league_size,
                                                    LocalDate league_start_date,
                                                    LocalDate league_end_date) {
        return leagueRepo.findByTemplate(league_id,
                client_id,
                league_name,
                admin_id,
                league_size,
                league_start_date,
                league_end_date);
    }

    // helper function: check if admin is valid user
    public Boolean checkAdmin(Integer adminID) {
        Optional<User> result = userRepo.findById(adminID);
        if (result.isPresent()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // helper function: check is given start and end dates for league are valid
    public Boolean checkDates(LocalDate league_start_date, LocalDate league_end_date) throws resourceException {
        LocalDate today = LocalDate.now();
        if (league_start_date.compareTo(today) >= 0) {
            if (league_start_date.compareTo(league_end_date) < 0) {
                if (DAYS.between(league_start_date, league_end_date) >= 14) {
                    return Boolean.TRUE;
                } else {
                    throw new resourceException("Attempted league duration is less than 2 weeks.");
                }
            } else {
                throw new resourceException("Attempted leagueStartDate is after leageEndDate.");
            }
        } else {
            throw new resourceException("Attempted leagueStartDate occurs in the past.");
        }
    }

    // post operation
    public List<FantasyLeague> postLeagues(FantasyLeague fantasyLeague) throws resourceException, IOException {
        fantasyLeague.setLeagueID(0);

        //This client_id will be updated later
        fantasyLeague.setClientID(1);

        if (checkAdmin(fantasyLeague.getAdminID())) {
            if (checkDates(fantasyLeague.getLeagueStartDate(), fantasyLeague.getLeagueEndDate())) {
                // Player Importation is done when league is posted
                FantasyLeagueUtility leagueUtility=new FantasyLeagueUtility();
                leagueUtility.API_player_importation(playerRepo);

                FantasyLeague result = leagueRepo.save(fantasyLeague);
                return List.of(result);
            } else {
                throw new resourceException("LeagueStartDate and/or LeagueEndDate are invalid.");
            }
        } else {
            throw new resourceException("The adminID provided is not a valid userID.");
        }
    }

    // helper function: check if two given fantasy leagues are equivalent except for league name
    public Boolean checkEqualWithoutLeagueName(FantasyLeague referenceLeague, FantasyLeague compareLeague) {
        if (referenceLeague.getLeagueID().equals(compareLeague.getLeagueID()) &&
                referenceLeague.getClientID().equals(compareLeague.getClientID()) &&
                referenceLeague.getAdminID().equals(compareLeague.getAdminID()) &&
                referenceLeague.getLeagueSize().equals(compareLeague.getLeagueSize()) &&
                referenceLeague.getLeagueStartDate().equals(compareLeague.getLeagueStartDate()) &&
                referenceLeague.getLeagueEndDate().equals(compareLeague.getLeagueEndDate())
        ) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // put operation
    public List<FantasyLeague> updateLeagues(FantasyLeague fantasyLeague) throws resourceNotFoundException, resourceException {

        //This client_id will be updated later
        fantasyLeague.setClientID(1);

        Optional<FantasyLeague> referenceLeagueOpt = leagueRepo.findById(fantasyLeague.getLeagueID());
        if (referenceLeagueOpt.isPresent()) {
            FantasyLeague referenceLeague = referenceLeagueOpt.get();
            if (checkEqualWithoutLeagueName(referenceLeague, fantasyLeague)) {
                FantasyLeague result = leagueRepo.save(fantasyLeague);
                return List.of(result);
            } else {
                throw new resourceException("Given FantasyLeague attempts to change field other than league_name.");
            }
        } else {
            throw new resourceNotFoundException("League not found by ID in DB, cannot update.");
        }
    }

    // delete operation
    public void deleteLeagues(Integer league_id) throws resourceNotFoundException {
        if(leagueRepo.existsById(league_id)) {
            leagueRepo.deleteById(league_id);
        } else {
            throw new resourceNotFoundException("League not found in DB, cannot delete");
        }
    }
    // TODO: Next iteration.
    // TODO: ONLY admin should be allowed to delete
    // TODO: How to know if user is currently admin?

    // helper function: checking valid string
    public boolean checkIfInvalid(String string) {
        return string.length() > 128 || string.isBlank();
    }

    // check and sanitize inputs
    private void checkInputs(FantasyLeague fantasyLeague) throws resourceException {
        // NOTE: max league size is hardcoded to 14 for now
        Integer max = 14;
        try {
            if (checkIfInvalid(fantasyLeague.getLeagueName())) {
                throw new resourceException("League name is invalid.");
            } else if (fantasyLeague.getLeagueSize() < 0 && fantasyLeague.getLeagueSize() > max
            && fantasyLeague.getLeagueSize()%2 == 0) {
                throw new resourceException("League size is invalid. (League size must be an even number.)");
            }
        } catch (NullPointerException e) {
            throw new resourceException("checkInputs: League formatted incorrectly please provide the following:\n" +
                    "league_id, client_id, league_name, admin_id, league_size, league_start_date, league_end_date.");
        }
    }

    // check post inputs
    public void checkPostInputs(FantasyLeague fantasyLeague) throws resourceException {
        if (fantasyLeague.getLeagueID() != null) {
            throw new resourceException("checkPostInputs: Do not provide league_id.");
        }
        checkInputs(fantasyLeague);
    }

    // check put inputs
    public void checkPutInputs(FantasyLeague fantasyLeague) throws resourceException {
        if (fantasyLeague.getLeagueID() == null) {
            throw new resourceException("checkPutInputs: Please provide league_id.");
        }
        checkInputs(fantasyLeague);

    // generation and saving of games
    public List<Integer> getTeamIDs(Integer league_id, Integer client_id) throws resourceException {

        List<Integer> result = teamRepo.findTeamsInLeague(league_id, client_id);

        // check to make sure teams registered with league is even
        if (result.size() % 2 != 0) {
            throw new resourceException("Number of teams must be even.");
        }
        return result;
    }

    public List<FantasyGame> postGames(Hashtable<LocalDate, List<List<Integer>>> schedule, Integer league_id, Integer client_id) {

        // making list of games to be saved
        List<FantasyGame> gameList = new ArrayList<>();

        // getting all key of schedule
        Set<LocalDate> startDates = schedule.keySet();

        // loop through each week of matchups
        for(LocalDate startDate: startDates) {

            // loop through each individual matchup
            for(List<Integer> matchup: schedule.get(startDate)) {
                // initialize variables for insertion
                Integer home_team_id = matchup.get(0);
                Integer away_team_id = matchup.get(1);
                LocalDate endDate    = startDate.plusWeeks(1);

                // initialize FantasyGame instance & make sure scheduleID is not null (0 by default)
                FantasyGame game = new FantasyGame(league_id, client_id, home_team_id, away_team_id, startDate, endDate);
                game.setScheduleID(0);

                // add this in list of games to be entered into DB
                gameList.add(game);
            } // end matchup looping

        } // end week looping

        // save all of the games in gameList into DB and return the list of all saved games
        return (List<FantasyGame>) gameRepo.saveAll(gameList);
    }

}
