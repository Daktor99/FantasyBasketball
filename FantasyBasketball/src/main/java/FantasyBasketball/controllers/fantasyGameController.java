package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.services.fantasyGameService;
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
import java.time.LocalDate;
import java.util.List;

@Controller
public class fantasyGameController {

    @Autowired
    fantasyGameService gameService;

    private static final Logger log = LoggerFactory.getLogger(fantasyGameController.class);

    private final HttpServletRequest request;

    // default constructor for fantasyGameController
    @Autowired
    public fantasyGameController(HttpServletRequest request) { this.request = request; }

    @RequestMapping(value = "/fantasyGames", method = RequestMethod.GET)
    public ResponseEntity<?> getGamesByTemplate(@RequestParam(value = "schedule_id", required = false)      Integer scheduleId,
                                                @RequestParam(value = "league_id", required = false)        Integer leagueId,
                                                @RequestParam(value = "home_team_id", required = false)     Integer homeTeamId,
                                                @RequestParam(value = "away_team_id", required = false)     Integer awayTeamId,
                                                @RequestParam(value = "game_start_date", required = false)  LocalDate gameStartDate,
                                                @RequestParam(value = "game_end_date", required = false)    LocalDate gameEndDate,
                                                @RequestParam(value = "winner_id", required = false)        Integer winnerId) {

        try {
            // log GET request
            if (request.getQueryString() != null) {
                log.info("GET: " + request.getRequestURL() + "?" + request.getQueryString());
            } else {
                log.info("GET: " + request.getRequestURL());
            }

            // This client_id will be updated later
            Integer client_id = 1;
            List<FantasyGame> result = gameService.getGamesByTemplate(scheduleId, leagueId, client_id, homeTeamId, awayTeamId,
                                                                        gameStartDate, gameEndDate, winnerId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception on GET: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyGames", method = RequestMethod.POST)
    public ResponseEntity<?> postGame(@RequestBody FantasyGame newGame) {

        try {

            log.info("POST: " + request.getRequestURL());
            log.info(newGame.toString());
            newGame.setClientID(1);

            // Check inputs to make sure no errors
            gameService.checkPostInputs(newGame);

            List<FantasyGame> result = gameService.postGame(newGame);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (resourceException e) {
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyGames", method = RequestMethod.PUT)
    public ResponseEntity<?> updateGame(@RequestBody FantasyGame game) {

        try {

            log.info("PUT: " + request.getRequestURL());
            log.info(game.toString());
            game.setClientID(1);

            // Check input to make sure no errors
            gameService.checkPutInputs(game);

            // Update the FantasyGame normally if no exceptions raised
            List<FantasyGame> result = gameService.updateGame(game);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (resourceException e) {
            log.info("Exception on PUT:", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on PUT: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.info("Exception on PUT:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyGames", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGame(@RequestParam(value = "schedule_id", required = true) Integer schedule_id) {

        try {

            log.info("DELETE: " + request.getRequestURL() + "?" + request.getQueryString());

            gameService.deleteGameById(schedule_id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (resourceNotFoundException e) {
            log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("Exception on DELETE: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
