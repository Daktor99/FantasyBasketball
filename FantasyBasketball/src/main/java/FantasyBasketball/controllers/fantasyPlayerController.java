package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.services.fantasyPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.List;
@Controller
public class fantasyPlayerController {

    @Autowired
    fantasyPlayerService fantasyPlayerService;

    private static final Logger log = LoggerFactory.getLogger(fantasyPlayerController.class);

    private final HttpServletRequest request;

    @Autowired
    public fantasyPlayerController(HttpServletRequest request) { this.request = request; }


    @RequestMapping(value = "/fantasyPlayers", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayersByTemplate(@RequestParam(value = "player_id", required = false) Integer player_id,
                                                  @RequestParam(value = "team_id", required = false) Integer team_id,
                                                  @RequestParam(value = "position", required = false) String position,
                                                  @RequestParam(value = "first_name", required = false) String first_name,
                                                  @RequestParam(value = "last_name", required = false) String last_name,
                                                  @RequestParam(value = "nba_team", required = false) String nba_team,
                                                  @RequestParam(value = "league_id", required = false) Integer league_id) {

        try {

            // log GET request
            if (request.getQueryString() != null) {
                log.info("GET: " + request.getRequestURL() + "?" + request.getQueryString());
            } else {
                log.info("GET: " + request.getRequestURL());
            }

            List<FantasyPlayer> result = fantasyPlayerService.getPlayerByTemplate(player_id, team_id, position, first_name, last_name,nba_team,league_id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //    Added Update Players
    //    Not sure if this function is needed for implementation
    //


    @RequestMapping(value = "/fantasyPlayers", method = RequestMethod.POST)
    public ResponseEntity<?> createPlayers(@RequestBody FantasyPlayer player) {
        try {

            log.info("POST: " + request.getRequestURL());
            log.info(player.toString());

            // Regular put
            List<FantasyPlayer> result = fantasyPlayerService.postFantasyPlayer(player);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyPlayers", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePlayers(@RequestBody FantasyPlayer player) {
        try {

            log.info("POST: " + request.getRequestURL());
            log.info(player.toString());

            // Regular put
            List<FantasyPlayer> result = fantasyPlayerService.updateFantasyPlayer(player);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
