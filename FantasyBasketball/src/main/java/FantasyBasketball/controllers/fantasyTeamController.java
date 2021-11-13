package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.models.User;
import FantasyBasketball.services.fantasyTeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class fantasyTeamController {

    @Autowired
    fantasyTeamService fantasyTeamService;

    private static final Logger log = LoggerFactory.getLogger(userController.class);

    private final HttpServletRequest request;

    // default constructor for userController class
    @Autowired
    public fantasyTeamController(HttpServletRequest request) { this.request = request; }

    @RequestMapping(value = "/fantasyTeam", method = RequestMethod.GET)
    public ResponseEntity<?> getTeamsByTemplate(@RequestParam(value = "team_id", required = false) Integer team_id,
                                                @RequestParam(value = "team_name", required = false) String team_name,
                                                @RequestParam(value = "owner_id", required = false) Integer owner_id,
                                                @RequestParam(value = "league_id", required = false) Integer league_id) {

        try {

            // log GET request
            if (request.getQueryString() != null) {
                log.info("GET: " + request.getRequestURL() + "?" + request.getQueryString());
            } else {
                log.info("GET: " + request.getRequestURL());
            }

            List<FantasyTeam> result = fantasyTeamService.getTeamsByTemplate(team_id, team_name, owner_id, league_id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyTeam", method = RequestMethod.POST)
    public ResponseEntity<?> postTeam(@RequestBody FantasyTeam newTeam) {

        try {

            log.info("POST: " + request.getRequestURL());
            log.info(newTeam.toString());

            // Checks to make sure the input is valid to insert in DB
            fantasyTeamService.checkPostInputs(newTeam);

            // Regular post
            List<FantasyTeam> result = fantasyTeamService.postTeam(newTeam);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch(resourceException e) {
            // exception thrown if User instance is not formatted correctly
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyTeam", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTeam(@RequestBody FantasyTeam team) {
        try {

            log.info("PUT: " + request.getRequestURL());
            log.info(team.toString());

            // Checks to make sure the input is valid to insert in DB
            fantasyTeamService.checkPutInputs(team);

            // Regular put
            List<FantasyTeam> result = fantasyTeamService.updateTeam(team);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (resourceException e) {
            // exception thrown if User instance is not formatted correctly
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (resourceNotFoundException e) {
            // If user not found in the database, throw exception not found
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyTeam", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTeam(@RequestParam(value = "team_id", required = true) Integer team_id) {
        try {

            log.info("DELETE: " + request.getRequestURL() + "?" + request.getQueryString());

            fantasyTeamService.deleteTeamById(team_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (resourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
