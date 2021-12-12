package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.*;
import FantasyBasketball.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class fantasyLeagueService {

    @Autowired
    clientService clientService;

    @Autowired
    fantasyPlayerService fantasyPlayerService;

    @Autowired
    userRepository userRepo;

    @Autowired
    fantasyLeagueRepository leagueRepo;

    @Autowired
    fantasyTeamRepository teamRepo;

    @Autowired
    fantasyGameRepository gameRepo;

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
                                                    Boolean draft_finished,
                                                    LocalDate league_start_date,
                                                    Integer num_weeks) {
        return leagueRepo.findByTemplate(league_id,
                client_id,
                league_name,
                admin_id,
                league_size,
                draft_finished,
                league_start_date,
                num_weeks);
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

    // helper function: check if drafting is finished
    public Boolean checkDraftFinished(Integer leagueID) {
        Optional<FantasyLeague> fantasyLeagueOptional = leagueRepo.findById(leagueID);
        FantasyLeague fantasyLeague = fantasyLeagueOptional.get();
        if (fantasyLeague.getDraftFinished().equals(Boolean.TRUE)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // helper function: check if given start date for league is valid
    public Boolean checkDates(LocalDate league_start_date) throws resourceException {
        LocalDate today = LocalDate.now();
        if (league_start_date.compareTo(today) < 0) {
            return false;
        }
        return true;
    }

    // post operation
    public List<FantasyLeague> postLeagues(FantasyLeague fantasyLeague) throws resourceException {

        fantasyLeague.setLeagueID(0);
        fantasyLeague.setDraftFinished(false);

        if (checkAdmin(fantasyLeague.getAdminID())) {
            if (checkDates(fantasyLeague.getLeagueStartDate())) {

                // Player Importation is done when league is posted
                // FantasyLeagueUtility leagueUtility = new FantasyLeagueUtility();
                // leagueUtility.API_player_importation(playerRepo);

                FantasyLeague result = leagueRepo.save(fantasyLeague);

                leagueRepo.prepLeaguePlayers(result.getLeagueID(), result.getClientID());

                return List.of(result);
            } else {
                throw new resourceException("LeagueStartDate has to be in the future.");
            }
        } else {
            throw new resourceException("The adminID provided is not a valid userID.");
        }
    }

    // helper function: check to make sure only league name is being changed
    public Boolean checkOnlyChangingName(FantasyLeague league) {
        return (league.getAdminID() == null && league.getLeagueSize() == null &&
                league.getDraftFinished() == null && league.getLeagueStartDate() == null &&
                league.getNumWeeks() == null);
    }

    // put operation
    public List<FantasyLeague> updateLeagues(FantasyLeague fantasyLeague)
            throws resourceNotFoundException, resourceException {

        // check to see that this league exists
        Optional<FantasyLeague> referenceLeagueOpt = leagueRepo.findById(fantasyLeague.getLeagueID());
        if (referenceLeagueOpt.isPresent()) {
            // check to make sure we are only changing league name
            if (checkOnlyChangingName(fantasyLeague)) {
                // update the referenced league in the DB to account for new name and save in db
                FantasyLeague referenceLeague = referenceLeagueOpt.get();
                referenceLeague.setLeagueName(fantasyLeague.getLeagueName());
                FantasyLeague result = leagueRepo.save(referenceLeague);
                return List.of(result);
            } else {
                throw new resourceException("Only name of Fantasy League may be changed after creation.");
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

    // helper function: checking valid string
    public boolean checkIfInvalid(String string) {
        return string.length() > 128 || string.isBlank();
    }

    // check and sanitize inputs
    public void checkInputs(FantasyLeague fantasyLeague) throws resourceException, resourceNotFoundException {
        // NOTE: max league size is hardcoded to 14 for now
        Integer newClientID = fantasyLeague.getClientID();
        List<Client> newClientList = clientService.getByID(newClientID);
        Client newClient = newClientList.get(0);
        Integer minLeagueSize = newClient.getMin_league_size();
        Integer minLeagueDur = newClient.getMin_league_dur();
        try {
            if (checkIfInvalid(fantasyLeague.getLeagueName())) {
                throw new resourceException("League name is invalid.");
            } else if (fantasyLeague.getLeagueSize() < minLeagueSize ||
                    fantasyLeague.getLeagueSize()%2 != 0) {
                throw new resourceException("League size is invalid. " +
                        "(League size must be an even number and greater than " + minLeagueSize + ").");
            } else if (fantasyLeague.getNumWeeks() < minLeagueDur) {
                throw new resourceException("Duration for the league's season is too short. " +
                        "(It has to be equal to or greater than " + minLeagueDur + ").");
            }
        } catch (NullPointerException e) {
            throw new resourceException("League formatted incorrectly please provide the following:\n" +
                    "league_name, admin_id, league_size, league_start_date, num_weeks.");
        }
        return;
    }

    // check post inputs
    public void checkPostInputs(FantasyLeague fantasyLeague) throws resourceException, resourceNotFoundException {
        if (fantasyLeague.getLeagueID() != null) {
            throw new resourceException("Do not provide league_id.");
        }
        checkInputs(fantasyLeague);
    }

    // check put inputs
    public void checkPutInputs(FantasyLeague fantasyLeague) throws resourceException, resourceNotFoundException {
        if (fantasyLeague.getLeagueID() == null) {
            throw new resourceException("Please provide league_id.");
        } else if (checkIfInvalid(fantasyLeague.getLeagueName())) {
            throw new resourceException("League name is invalid.");
        }
    }

    // generation and saving of games
    public List<Integer> getTeamIDs(Integer league_id, Integer client_id) throws resourceException {

        List<Integer> result = teamRepo.findTeamsInLeague(league_id, client_id);

        // check to make sure teams registered with league is even
        if (result.size() % 2 != 0) {
            throw new resourceException("Number of teams must be even.");
        } else {
            return result;
        }
    }

    public List<FantasyGame> postGames(Hashtable<LocalDate, List<List<Integer>>> schedule,
                                       Integer league_id, Integer client_id) {

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
                FantasyGame game = new FantasyGame(league_id,
                        client_id,
                        home_team_id,
                        away_team_id,
                        startDate,
                        endDate);
                game.setScheduleID(0);

                // add this in list of games to be entered into DB
                gameList.add(game);
            } // end matchup looping

        } // end week looping

        // save all of the games in gameList into DB and return the list of all saved games
        gameRepo.saveAll(gameList);
        return gameList;
    }

    public void checkValidSize(FantasyLeague league, Integer teamSize) throws resourceException {

        Integer league_size = league.getLeagueSize();
        if (league_size != teamSize) {
            throw new resourceException("Cannot generate schedule, make sure this league has at least "
                    + league_size
                    + " teams registered.");
        }
    }

    public void checkDraftInputs(Integer league_id, Integer team_id, Integer client_id)
            throws resourceException, resourceNotFoundException {

        // Check if league is valid
        if (league_id == null) {
            throw new resourceException("league_id required");
        } else if (!leagueRepo.existsById(league_id)) {
            throw new resourceNotFoundException("This league does not exist.");
        }

        // List of all teams that are in the league
        List<Integer> team_ids = teamRepo.findTeamsInLeague(league_id, client_id);

        //check if team_id provided is in the list of team_ids that exist in the league
        boolean teamInLeague = team_ids.contains(team_id);

        // Check if team is valid
        if (team_id == null) {
            throw new resourceNotFoundException("team_id required");
        } else if (!teamRepo.existsById(team_id)) {
            throw new resourceNotFoundException("This team does not exist.");
        } else if (!teamInLeague) {
            throw new resourceNotFoundException("This team does not exist in this league.");
        }
    }

    public void pickPlayer(FantasyPlayer fantasyPlayer, Integer client_id)
            throws resourceException, resourceNotFoundException {
        // If player_id given, assign that player to the specified team.
        // Otherwise, assign a random one.
        if (fantasyPlayer.getPlayerID() == null) {

            // Get list of available players
            Integer league_id = fantasyPlayer.getLeagueID();
            List<FantasyPlayer> av_players = fantasyPlayerService.getAvailablePlayers(league_id,
                    client_id,
                    null,
                    null,
                    null,
                    null);

            if (av_players.size() == 0) {
                throw new resourceException("There are no more players to be drafted in this league.");
            }
            // Chose random player from list of available players
            Integer idx = fantasyPlayerService.generateNumber(0, av_players.size());
            FantasyPlayer chosenPlayer = av_players.get(idx);
            fantasyPlayer.setPlayerID(chosenPlayer.getPlayerID());

        } else {
            List<Integer> undraft_players =
                    fantasyPlayerService.getUndraftedPlayers(fantasyPlayer.getLeagueID(), client_id);
            if (!undraft_players.contains(fantasyPlayer.getPlayerID())) {
                throw new resourceException("Chosen player is not available for drafting.");
            }
        }
    }

    public List<Integer> randomOrder(Integer league_id, Integer client_id) throws resourceNotFoundException {
        List<Integer> team_ids = teamRepo.findTeamsInLeague(league_id, client_id);
        Collections.shuffle(team_ids);
        List<Integer> order = new ArrayList<>();
        List<Client> clientList = clientService.getByID(client_id);
        Client client = clientList.get(0);
        Integer team_size = client.getMax_team_size();
        for (int i = 0; i < team_size; i++) {
            order.addAll(team_ids);
        }
        return order;
    }

    public void checkIfValidLeague(Integer league_id) throws resourceException, resourceNotFoundException {
        // Check if league is valid
        if (league_id == null) {
            throw new resourceException("league_id required.");
        } else if (!leagueRepo.existsById(league_id)) {
            throw new resourceNotFoundException("This league does not exist.");
        }
    }

    public void checkIfScheduleGenerated(Integer league_id) throws resourceException{
        List<FantasyGame> games = gameRepo.findGamesByLeagueID(league_id);
        if (games.size() != 0 || games.isEmpty()) {
            throw new resourceException("Schedule already generated for this league.");
        }
    }
}