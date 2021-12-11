package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyTeam;
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

import static FantasyBasketball.controllers.controllerUtils.*;

@Controller
public class fantasyTeamController {

    @Autowired
    fantasyTeamService fantasyTeamService;

    private static final Logger log = LoggerFactory.getLogger(userController.class);

    private final HttpServletRequest request;

    // default constructor for userController class
    @Autowired
    public fantasyTeamController(HttpServletRequest request) {
        this.request = request;
    }

    @RequestMapping(value = "/fantasyTeams", method = RequestMethod.GET)
    public ResponseEntity<?> getTeamsByTemplate(@RequestParam(value = "team_id", required = false) Integer team_id,
                                                @RequestParam(value = "team_name", required = false) String team_name,
                                                @RequestParam(value = "owner_id", required = false) Integer owner_id,
                                                @RequestParam(value = "league_id", required = false) Integer league_id) {

        try {

            // log GET request and get client it
            logGetRequest(request, log);
            Integer client_id = getClientId(request);

            // get teams by template
            List<FantasyTeam> result = fantasyTeamService.getTeamsByTemplate(team_id, client_id, team_name, owner_id, league_id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*
    Minimum fields required to post (ex):
        {
            "team_name": "Marion's Team",
            "owner_id": 6,
            "league_id": 1
        }
     */
    @RequestMapping(value = "/fantasyTeams", method = RequestMethod.POST)
    public ResponseEntity<?> postTeam(@RequestBody FantasyTeam newTeam) {

        try {

            // log post request and get client id
            logPostRequest(request, log, newTeam.toString());
            Integer client_id = getClientId(request);

            // Checks to make sure the input is valid to insert in DB
            newTeam.setClientID(client_id);
            fantasyTeamService.checkPostInputs(newTeam);

            // Regular post
            List<FantasyTeam> result = fantasyTeamService.postTeam(newTeam);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (resourceException e) {
            // exception thrown if User instance is not formatted correctly
            log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // other exceptions
            log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyTeams", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTeam(@RequestBody FantasyTeam team) {
        try {

            // log PUT request and get client id
            logPutRequest(request, log, team.toString());
            Integer client_id = getClientId(request);

            // Regular put
            team.setClientID(client_id);
            List<FantasyTeam> result = fantasyTeamService.updateTeam(team);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (resourceException e) {
            // exception thrown if User instance is not formatted correctly
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (resourceNotFoundException e) {
            // If user not found in the database, throw exception not found
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // other exceptions
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyTeams", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTeam(@RequestParam(value = "team_id") Integer team_id) {
        try {

            // log delete request
            logDeleteRequest(request, log);

            // regular delete
            fantasyTeamService.deleteTeamById(team_id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (resourceNotFoundException e) {
            log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
