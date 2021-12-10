package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.repositories.playerDataRepository;
import FantasyBasketball.services.fantasyLeagueService;
import FantasyBasketball.services.fantasyPlayerService;
import FantasyBasketball.utilities.FantasyLeagueUtility;
import FantasyBasketball.utilities.robinRoundScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;

@Controller
public class fantasyLeagueController {

    @Autowired
    fantasyLeagueService fantasyLeagueService;

    @Autowired
    fantasyPlayerService fantasyPlayerService;

    @Autowired
    playerDataRepository dataRepo;

    private static final Logger log = LoggerFactory.getLogger(fantasyLeagueController.class);

    private final HttpServletRequest request;

    // default constructor for fantasyLeagueController class
    @Autowired
    public fantasyLeagueController(HttpServletRequest request) { this.request = request; }

    @RequestMapping(value = "/fantasyLeagues", method = RequestMethod.GET)
    public ResponseEntity<?> getLeaguesByTemplate(@RequestParam(value = "league_id", required = false) Integer league_id,
                                                  @RequestParam(value = "league_name", required = false) String league_name,
                                                  @RequestParam(value = "admin_id", required = false) Integer admin_id,
                                                  @RequestParam(value = "league_size", required = false) Integer league_size,
                                                  @RequestParam(value = "draft_finished", required = false) Boolean draft_finished,
                                                  @RequestParam(value = "league_start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate league_start_date,
                                                  @RequestParam(value = "num_weeks", required = false) Integer num_weeks) {
        try {

            // log GET request
            if (request.getQueryString() != null) {
                log.info("GET: " + request.getRequestURL() + "?" + request.getQueryString());
            } else {
                log.info("GET: " + request.getRequestURL());
            }

            //This client_id will be updated later
            Integer client_id = 1;
            List<FantasyLeague> result = fantasyLeagueService.getLeaguesByTemplate(league_id,
                    client_id,
                    league_name,
                    admin_id,
                    league_size,
                    draft_finished,
                    league_start_date,
                    num_weeks);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeagues", method = RequestMethod.POST)
    public ResponseEntity<?> postLeagues(@RequestBody FantasyLeague newLeague) {

        try {

            log.info("POST: " + request.getRequestURL());
            log.info(newLeague.toString());

            newLeague.setClientID(1);
            // Checks to make sure the input is valid to insert in DB
            fantasyLeagueService.checkPostInputs(newLeague);

            // Regular post
            List<FantasyLeague> result = fantasyLeagueService.postLeagues(newLeague);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch(resourceException e) {
            // exception thrown if League instance is not formatted correctly
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeagues", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLeagues(@RequestBody FantasyLeague fantasyLeague) {
        try {

            log.info("PUT: " + request.getRequestURL());
            log.info(fantasyLeague.toString());

            // Checks to make sure the input is valid to insert in DB
            fantasyLeagueService.checkPutInputs(fantasyLeague);

            // Regular put
            List<FantasyLeague> result = fantasyLeagueService.updateLeagues(fantasyLeague);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (resourceException e) {
            // exception thrown if League instance is not formatted correctly
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (resourceNotFoundException e) {
            // If league not found in the database, throw exception not found
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeagues", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLeagues(@RequestParam(value = "league_id", required = true) Integer league_id) {
        try {

            log.info("DELETE: " + request.getRequestURL() + "?" + request.getQueryString());

            fantasyLeagueService.deleteLeagues(league_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (resourceNotFoundException e) {
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeagues/scheduleGames", method = RequestMethod.POST)
    public ResponseEntity<?> scheduleGames(@RequestParam(value = "league_id", required = true) Integer league_id) {

        try {
            // log schedule generation
            log.info("GENERATE SCHEDULE: " + request.getRequestURL()
                    + " for fantasyLeague with league_id: " + league_id);

            // get the teams registered with the leagueID
            // TODO: Change client ID
            Integer client_id = 57;
            List<Integer> teamIDList = fantasyLeagueService.getTeamIDs(league_id, client_id);

            // get the league with league_id
            FantasyLeague fantasyLeague = fantasyLeagueService.getLeaguesByID(league_id).get(0);

            // make sure that the number of teams found in db are equal to league_size in league
            fantasyLeagueService.checkValidSize(fantasyLeague, teamIDList.size());

            // get proposed schedule, instantiate scheduler service & create schedule
            robinRoundScheduler scheduler = new robinRoundScheduler(teamIDList, fantasyLeague.getLeagueStartDate(), fantasyLeague.getNumWeeks());
            Hashtable<LocalDate, List<List<Integer>>> schedule = scheduler.createSchedule();

            // save the games generated by the scheduler
            List<FantasyGame> result = fantasyLeagueService.postGames(schedule, league_id, client_id);

            // return result of schedules
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (resourceException e) {
            log.error("Exception on schedule generation: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on schedule generation: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @RequestMapping(value = "/fantasyLeague/draft/draftPlayer", method = RequestMethod.PUT)
//    public ResponseEntity<?> updateDraftedPlayer(@RequestParam(value = "player_id", required = false) Integer player_id,
//                                                 @RequestParam(value = "team_id", required = true) Integer team_id,
//                                                 @RequestParam(value = "league_id", required = false) Integer league_id,
//                                                 @RequestParam(value = "client_id", required = false) Integer client_id,
//                                                 @RequestParam(value = "first_name", required = false) String first_name,
//                                                 @RequestParam(value = "last_name", required = false) String last_name,
//                                                 @RequestParam(value = "nba_team", required = false) String nba_team,
//                                                 @RequestParam(value = "position", required = false) String position) {
//        try {
//
//            log.info("Parameters: \n" + "player_id: " + player_id + "\n team_id: " + team_id);
//            log.info("PUT: " + request.getRequestURL());
//
//            // TODO: Filter and check inputs
//
//            FantasyPlayer chosenPlayer = new FantasyPlayer();
//
//            // If player_id given, draft that player to the specified team.
//            // Otherwise, assign a random one.
//            if (player_id == null) {
//
//                // Get list of available players
//                List<FantasyPlayer> av_players = fantasyPlayerService.getAvailablePlayers(league_id, client_id, first_name, last_name, nba_team, position);
//
//                // Chose random player from list of available players
//                Integer idx = fantasyPlayerService.generateNumber(0, av_players.size());
//                chosenPlayer = av_players.get(idx);
//
//            } else {
//
//                //get the player by player_id
//                chosenPlayer = fantasyPlayerService.getByID(player_id).get(0);
//            }
//
//            List<FantasyPlayer> result = fantasyPlayerService.draftFantasyPlayer(chosenPlayer);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//
//        } catch (resourceNotFoundException e) {
//            // If league not found in the database, throw exception not found
//            log.error("Exception on PUT: " + e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (DataIntegrityViolationException e) {
//            log.error("Exception on PUT: ", e);
//            return new ResponseEntity<>("This action is not allowed, please check values and try again.",
//                    HttpStatus.UNPROCESSABLE_ENTITY);
//        } catch (Exception e) {
//            // all other exceptions
//            log.error("Exception on PUT: ", e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @RequestMapping(value = "/fantasyLeague/draft/draftPlayer", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDraftedPlayer(@RequestBody FantasyPlayer fantasyPlayer) {
        try {

            log.info("PUT: " + request.getRequestURL());
            log.info(fantasyPlayer.toString());

            // TODO: Filter and check inputs

            // Check that league and team exist and that their ids are provided in the body
            // TODO: (Emanuel) change client_id later
            fantasyLeagueService.checkDraftInputs(fantasyPlayer.getLeagueID(), fantasyPlayer.getTeamID(), 57);

            // TODO: Update client ID
            fantasyLeagueService.pickPlayer(fantasyPlayer);
            // TODO: Update client ID
            fantasyPlayer.setClientID(57);
            List<FantasyPlayer> result = fantasyPlayerService.draftFantasyPlayer(fantasyPlayer);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (resourceNotFoundException | resourceException e) {
            // If league not found in the database, throw exception not found
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeague/draft/availablePlayers", method = RequestMethod.GET)
    public ResponseEntity<?> getAvailablePlayers(@RequestParam(value = "league_id", required = false) Integer league_id,
                                                 @RequestParam(value = "client_id", required = false) Integer client_id,
                                                 @RequestParam(value = "first_name", required = false) String first_name,
                                                 @RequestParam(value = "last_name", required = false) String last_name,
                                                 @RequestParam(value = "nba_team", required = false) String nba_team,
                                                 @RequestParam(value = "position", required = false) String position) {
        try {

            // log GET request
            if (request.getQueryString() != null) {
                log.info("GET: " + request.getRequestURL() + "?" + request.getQueryString());
            } else {
                log.info("GET: " + request.getRequestURL());
            }

            List<FantasyPlayer> result = fantasyPlayerService.getAvailablePlayers(league_id,
                    client_id, first_name, last_name, nba_team, position);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeague/playerCreation", method = RequestMethod.POST)
    public ResponseEntity<?> playerCreation() {
        try {

            // log GET request
            if (request.getQueryString() != null) {
                log.info("GET: " + request.getRequestURL() + "?" + request.getQueryString());
            } else {
                log.info("GET: " + request.getRequestURL());
            }

            FantasyLeagueUtility newUtility= new FantasyLeagueUtility();
            newUtility.API_player_importation(dataRepo);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeague/draft/generateOrder", method = RequestMethod.GET)
    public ResponseEntity<?> randomizeDraftOrder(@RequestParam(value = "league_id", required = false) Integer league_id,
                                                  @RequestParam(value = "client_id", required = true) Integer client_id) {
        try {
            fantasyLeagueService.checkIfValidLeague(league_id);
            List<Integer> result = fantasyLeagueService.randomOrder(league_id, client_id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
