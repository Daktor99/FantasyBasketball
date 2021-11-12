package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import FantasyBasketball.services.fantasyLeagueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

@Controller
public class fantasyLeagueController {

    @Autowired
    fantasyLeagueService fantasyLeagueService;

    private static final Logger log = LoggerFactory.getLogger(fantasyLeagueController.class);

    private final HttpServletRequest request;

    // default constructor for fantasyLeagueController class
    @Autowired
    public fantasyLeagueController(HttpServletRequest request) { this.request = request; }

    @RequestMapping(value = "/leagues", method = RequestMethod.GET)
    public ResponseEntity<?> getLeaguesByTemplate(@RequestParam(value = "league_id", required = false) Integer league_id,
                                                @RequestParam(value = "league_name", required = false) String league_name,
                                                @RequestParam(value = "admin_id", required = false) Integer admin_id,
                                                @RequestParam(value = "league_size", required = false) Integer league_size,
                                                @RequestParam(value = "league_start_date", required = false) Date league_start_date,
                                                @RequestParam(value = "league_end_date", required = false) Date league_end_date) {
        try {

            // log GET request
            if (request.getQueryString() != null) {
                log.info("GET: " + request.getRequestURL() + "?" + request.getQueryString());
            } else {
                log.info("GET: " + request.getRequestURL());
            }

            List<FantasyLeague> result = fantasyLeagueService.getLeaguesByTemplate(league_id,
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

    @RequestMapping(value = "/leagues", method = RequestMethod.POST)
    public ResponseEntity<?> postFantasyLeague(@RequestBody FantasyLeague newLeague) {

        try {

            log.info("POST: " + request.getRequestURL());
            log.info(newLeague.toString());

            // Checks to make sure the input is valid to insert in DB
            fantasyLeagueService.checkPostInputs(newLeague);

            // Regular post
            List<FantasyLeague> result = fantasyLeagueService.postFantasyLeague(newLeague);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch(resourceException e) {
            // exception thrown if League instance is not formatted correctly
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/leagues", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLeague(@RequestBody FantasyLeague fantasyLeague) {
        try {

            log.info("PUT: " + request.getRequestURL());
            log.info(fantasyLeague.toString());

            // Checks to make sure the input is valid to insert in DB
            fantasyLeagueService.checkPutInputs(fantasyLeague);

            // Regular put
            List<FantasyLeague> result = fantasyLeagueService.updateLeague(fantasyLeague);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (resourceException e) {
            // exception thrown if League instance is not formatted correctly
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (resourceNotFoundException e) {
            // If league not found in the database, throw exception not found
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
