package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.ResourceException;
import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.services.FantasyGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
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

import static FantasyBasketball.controllers.ControllerUtils.*;

@Controller
public class FantasyGameController {

    @Autowired
    FantasyGameService gameService;

    private static final Logger Log = LoggerFactory.getLogger(FantasyGameController.class);

    private final HttpServletRequest request;

    // default constructor for FantasyGameController
    @Autowired
    public FantasyGameController(HttpServletRequest request) { this.request = request; }

    @RequestMapping(value = "/fantasyGames", method = RequestMethod.GET)
    public ResponseEntity<?> getGamesByTemplate(
                                @RequestParam(value = "schedule_id", required = false)      Integer scheduleId,
                                @RequestParam(value = "league_id", required = false)        Integer leagueId,
                                @RequestParam(value = "home_team_id", required = false)     Integer homeTeamId,
                                @RequestParam(value = "away_team_id", required = false)     Integer awayTeamId,
                                @RequestParam(value = "game_start_date", required = false)
                                                @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate gameStartDate,
                                @RequestParam(value = "game_end_date", required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate gameEndDate,
                                @RequestParam(value = "winner_id", required = false)        Integer winnerId) {

        try {
            // Log GET request and get client id
            logGetRequest(request, Log);
            Integer client_id = getClientId(request);

            // get results
            List<FantasyGame> result = gameService.getGamesByTemplate(scheduleId, leagueId, client_id, homeTeamId,
                    awayTeamId, gameStartDate, gameEndDate, winnerId);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            Log.error("Exception on GET: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyGames", method = RequestMethod.POST)
    public ResponseEntity<?> postGame(@RequestBody FantasyGame newGame) {

        try {

            // Log post request and get client id
            logPostRequest(request, Log, newGame.toString());
            Integer client_id = getClientId(request);

            // set client to current client id and check for errors in input
            newGame.setClientID(client_id);
            gameService.checkPostInputs(newGame);

            // normal post
            List<FantasyGame> result = gameService.postGame(newGame);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (ResourceException e) {
            Log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (DataIntegrityViolationException e) {
            Log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            Log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyGames", method = RequestMethod.PUT)
    public ResponseEntity<?> updateGame(@RequestBody FantasyGame game) {

        try {

            // Log put request and get client id
            logPutRequest(request, Log, game.toString());
            Integer client_id = getClientId(request);

            // Set client id and check input to make sure no errors
            game.setClientID(client_id);
            gameService.checkPutInputs(game);

            // Update the FantasyGame normally if no exceptions raised
            List<FantasyGame> result = gameService.updateGame(game);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (ResourceException e) {
            Log.info("Exception on PUT:" + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (DataIntegrityViolationException e) {
            Log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            Log.info("Exception on PUT:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyGames", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGame(@RequestParam(value = "schedule_id") Integer schedule_id) {

        try {

            // Log delete request
            logDeleteRequest(request, Log);

            // regular deletion
            gameService.deleteGameById(schedule_id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            Log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            Log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            Log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
