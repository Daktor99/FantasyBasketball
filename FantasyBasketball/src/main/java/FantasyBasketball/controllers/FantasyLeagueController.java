package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.ResourceException;
import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.repositories.playerDataRepository;
import FantasyBasketball.services.FantasyLeagueService;
import FantasyBasketball.services.FantasyPlayerService;
import FantasyBasketball.utilities.FantasyLeagueUtility;
import FantasyBasketball.utilities.RobinRoundScheduler;
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

import static FantasyBasketball.controllers.ControllerUtils.*;

@Controller
public class FantasyLeagueController {

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyPlayerService fantasyPlayerService;

    @Autowired
    playerDataRepository dataRepo;

    private static final Logger log = LoggerFactory.getLogger(FantasyLeagueController.class);

    private final HttpServletRequest Request;

    // default constructor for FantasyLeagueController class
    @Autowired
    public FantasyLeagueController(HttpServletRequest request) { this.Request = request; }

    @RequestMapping(value = "/fantasyLeagues", method = RequestMethod.GET)
    public ResponseEntity<?> getLeaguesByTemplate(@RequestParam(value = "league_id", required = false) Integer league_id,
                                                  @RequestParam(value = "league_name", required = false) String league_name,
                                                  @RequestParam(value = "admin_id", required = false) Integer admin_id,
                                                  @RequestParam(value = "league_size", required = false) Integer league_size,
                                                  @RequestParam(value = "draft_finished", required = false) Boolean draft_finished,
                                                  @RequestParam(value = "league_start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate league_start_date,
                                                  @RequestParam(value = "num_weeks", required = false) Integer num_weeks) {
        try {

            // log GET Request and get client id
            logGetRequest(Request, log);
            Integer client_id = getClientId(Request);

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

            // log post Request and get client id
            logPostRequest(Request, log, newLeague.toString());
            Integer client_id = getClientId(Request);


            // set client id and check inputs
            newLeague.setClientID(client_id);
            fantasyLeagueService.checkPostInputs(newLeague);

            // Regular post
            List<FantasyLeague> result = fantasyLeagueService.postLeagues(newLeague);

            // TODO: populate fantasy_player table with player_data table on creation

            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch(ResourceException e) {
            // exception thrown if League instance is not formatted correctly
            log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // other exceptions
            log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeagues", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLeagues(@RequestBody FantasyLeague fantasyLeague) {
        try {

            // log put Request and get client id
            logPutRequest(Request, log, fantasyLeague.toString());
            Integer client_id = getClientId(Request);

            // Checks to make sure the input is valid to insert in DB
            fantasyLeague.setClientID(client_id);
            fantasyLeagueService.checkPutInputs(fantasyLeague);

            // Regular put
            List<FantasyLeague> result = fantasyLeagueService.updateLeagues(fantasyLeague);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (ResourceException e) {
            // exception thrown if League instance is not formatted correctly
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ResourceNotFoundException e) {
            // If league not found in the database, throw exception not found
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // other exceptions
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeagues", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLeagues(@RequestParam(value = "league_id") Integer league_id) {
        try {

            // log delete Request
            logDeleteRequest(Request, log);

            // deletion
            fantasyLeagueService.deleteLeagues(league_id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeagues/scheduleGames", method = RequestMethod.POST)
    public ResponseEntity<?> scheduleGames(@RequestParam(value = "league_id") Integer league_id) {

        try {

            // log schedule generation and get client id
            logGameScheduling(Request, log, league_id);
            Integer client_id = getClientId(Request);

            // get teams within league and current league
            List<Integer> teamIDList = fantasyLeagueService.getTeamIDs(league_id, client_id);
            FantasyLeague fantasyLeague = fantasyLeagueService.getLeaguesByID(league_id).get(0);

            // make sure that the number of teams found in db are equal to league_size in league
            fantasyLeagueService.checkValidSize(fantasyLeague, teamIDList.size());

            // Check if the schedule for this league is already generated
            fantasyLeagueService.checkIfScheduleGenerated(fantasyLeague.getLeagueID());

            // get proposed schedule, instantiate scheduler service & create schedule
            RobinRoundScheduler scheduler = new RobinRoundScheduler(teamIDList, fantasyLeague.getLeagueStartDate(), fantasyLeague.getNumWeeks());
            Hashtable<LocalDate, List<List<Integer>>> schedule = scheduler.createSchedule();

            // save the games generated by the scheduler
            List<FantasyGame> result = fantasyLeagueService.postGames(schedule, league_id, client_id);

            // return result of schedules
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (ResourceException e) {
            log.error("Exception on schedule generation: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on schedule generation: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeague/draft/draftPlayer", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDraftedPlayer(@RequestBody FantasyPlayer fantasyPlayer) {
        try {

            // log drafting the player and get the client id
            logPlayerDrafting(Request, log, fantasyPlayer.toString());
            Integer client_id = getClientId(Request);

            // TODO: Filter and check inputs

            // Check that league and team exist and that their ids are provided in the body
            fantasyLeagueService.checkDraftInputs(fantasyPlayer.getLeagueID(), fantasyPlayer.getTeamID(), client_id);

            // set player's client id
            fantasyPlayer.setClientID(client_id);
            fantasyLeagueService.pickPlayer(fantasyPlayer, client_id);

            // save the draft pick into db
            List<FantasyPlayer> result = fantasyPlayerService.draftFantasyPlayer(fantasyPlayer);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (ResourceNotFoundException | ResourceException e) {
            // If league not found in the database, throw exception not found
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // other exceptions
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeague/draft/availablePlayers", method = RequestMethod.GET)
    public ResponseEntity<?> getAvailablePlayers(@RequestParam(value = "league_id", required = false) Integer league_id,
                                                 @RequestParam(value = "first_name", required = false) String first_name,
                                                 @RequestParam(value = "last_name", required = false) String last_name,
                                                 @RequestParam(value = "nba_team", required = false) String nba_team,
                                                 @RequestParam(value = "position", required = false) String position) {
        try {

            // log GET Request and get client id
            logGetRequest(Request, log);
            Integer client_id = getClientId(Request);

            // find available players
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

            // log GET Request and get client id
            logGetRequest(Request, log);
            Integer client_id = getClientId(Request);


            FantasyLeagueUtility newUtility= new FantasyLeagueUtility();
            newUtility.API_player_importation(dataRepo);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyLeague/draft/generateOrder", method = RequestMethod.GET)
    public ResponseEntity<?> randomizeDraftOrder(@RequestParam(value = "league_id", required = false) Integer league_id) {
        try {

            // log GET Request and get client id
            logGetRequest(Request, log);
            Integer client_id = getClientId(Request);

            // check if we are given a valid league id
            fantasyLeagueService.checkIfValidLeague(league_id);

            // generate draft order
            List<Integer> result = fantasyLeagueService.randomOrder(league_id, client_id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            // If league not found in the database, throw exception not found
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Exception on GET: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
