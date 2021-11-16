package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import FantasyBasketball.services.fantasyLeagueService;
import FantasyBasketball.services.fantasyPlayerService;
import FantasyBasketball.services.fantasyTeamService;
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
                                                  @RequestParam(value = "league_start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate league_start_date,
                                                  @RequestParam(value = "league_end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate league_end_date) {
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
                    league_start_date,
                    league_end_date);
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
    public ResponseEntity<?> scheduleGames(@RequestBody FantasyLeague fantasyLeague) {

        try {
            // log schedule generation
            log.info("GENERATE SCHEDULE: " + request.getRequestURL()
                    + " for fantasyLeague with league_id: " + fantasyLeague.getLeagueID());

            // get the teams registered with the leagueID
            Integer league_id = fantasyLeague.getLeagueID();
            Integer client_id = 1;
            List<Integer> teamIDList = fantasyLeagueService.getTeamIDs(league_id, client_id);

            // get proposed schedule, instantiate scheduler service & create schedule
            robinRoundScheduler scheduler = new robinRoundScheduler(teamIDList, fantasyLeague.getLeagueStartDate(), fantasyLeague.getLeagueEndDate());
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

    @RequestMapping(value = "/fantasyLeague/draft/draftPlayer", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDraftedPlayer(@RequestBody FantasyPlayer fantasyPlayer) {
        try {

            log.info("PUT: " + request.getRequestURL());
            log.info(fantasyPlayer.toString());

            // Regular put
            List<FantasyPlayer> result = fantasyPlayerService.draftFantasyPlayer(fantasyPlayer);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (resourceNotFoundException e) {
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
    public ResponseEntity<?> getAvailablePlayers( @RequestParam(value = "team_id", required = false) Integer team_id,
                                                  @RequestParam(value = "league_id", required = false) Integer league_id,
                                                  @RequestParam(value = "client_id", required = false) Integer client_id) {
        try {

            // log GET request
            if (request.getQueryString() != null) {
                log.info("GET: " + request.getRequestURL() + "?" + request.getQueryString());
            } else {
                log.info("GET: " + request.getRequestURL());
            }

            List<FantasyPlayer> result = fantasyPlayerService.getAvailablePlayers(team_id,
                    league_id,
                    client_id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
