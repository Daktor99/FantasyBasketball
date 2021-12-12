package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.services.FantasyStatsService;
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

import static FantasyBasketball.controllers.ControllerUtils.*;

@Controller
public class FantasyStatsController {

    @Autowired
    FantasyStatsService fantasyStatsService;

    private static final Logger Log = LoggerFactory.getLogger(FantasyStatsController.class);

    private final HttpServletRequest request;

    // default constructor for FantasyStatsController
    @Autowired
    public FantasyStatsController(HttpServletRequest request) {this.request = request;}

    @RequestMapping(value = "/fantasyStats", method = RequestMethod.GET)
    public ResponseEntity<?> getFantasyStatsByTemplate(
            @RequestParam(value = "stats_id", required = false) Integer stats_id,
            @RequestParam(value = "player_id", required = false) Integer player_id,
            @RequestParam(value = "schedule_id", required = false) Integer schedule_id,
            @RequestParam(value = "league_id", required = false) Integer league_id,
            @RequestParam(value = "three_points", required = false) Integer threeP,
            @RequestParam(value = "two_points", required = false) Integer twoP,
            @RequestParam(value = "free_throws", required = false) Integer freeThrows,
            @RequestParam(value = "rebounds", required = false) Integer rebounds,
            @RequestParam(value = "assists", required = false) Integer assists,
            @RequestParam(value = "blocks", required = false) Integer blocks,
            @RequestParam(value = "steals", required = false) Integer steals,
            @RequestParam(value = "turnovers", required = false) Integer turnovers,
            @RequestParam(value = "tot_points", required = false) Integer totPoints) {

        try {

            // Log get request and get client id
            logGetRequest(request, Log);
            Integer client_id = getClientId(request);

            List<FantasyStats> result =  fantasyStatsService.getStatsByTemplate(
                    stats_id,
                    player_id,
                    schedule_id,
                    client_id,
                    league_id,
                    threeP,
                    twoP,
                    freeThrows,
                    rebounds,
                    assists,
                    blocks,
                    steals,
                    turnovers,
                    totPoints);

            Log.info("FantasyStats retrieved successfully");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            Log.error("Exception on GET: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyStats", method = RequestMethod.POST)
    public ResponseEntity<?> createStats(@RequestBody FantasyStats newStats) {

        try {

            // Log post request and get client id
            logPostRequest(request, Log, newStats.toString());
            Integer client_id = getClientId(request);
            newStats.setClient_id(client_id);

            // post to stats
            fantasyStatsService.checkPostInputs(newStats);
            List<FantasyStats> result = fantasyStatsService.postStats(newStats);
            Log.info("Fantasy Stats entry has been created successfully");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            Log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            Log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyStats", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStats(@RequestBody FantasyStats stats) {


        try{

            // Log put request and get client id
            logPutRequest(request, Log, stats.toString());
            Integer client_id = getClientId(request);

            // set client id and update stat
            stats.setClient_id(client_id);
            List<FantasyStats> result = fantasyStatsService.updateStats(stats);
            Log.info("Fantasy Stats entry has been updated successfully");
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            Log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fantasyStats", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStats(@RequestParam(value = "stats_id", required = false) Integer stats_id,
                                         @RequestParam(value = "player_id", required = false) Integer player_id,
                                         @RequestParam(value = "schedule_id", required = false) Integer schedule_id,
                                         @RequestParam(value = "league_id", required = false) Integer league_id) {

        try {

            // Log put request and get client id
            logDeleteRequest(request, Log);
            Integer client_id = getClientId(request);

            if (league_id == null) {
                Log.error("Cannot delete data without a league_id provided.");
                return new ResponseEntity<>("Cannot delete data without a league_id provided.",
                        HttpStatus.BAD_REQUEST);
            } else if (stats_id == null && player_id == null && schedule_id == null) {
                Log.error("Cannot delete data with no player_id, stats_id, schedule_id");
                return new ResponseEntity<>("Cannot delete data with no player_id, stats_id, schedule_id",
                        HttpStatus.BAD_REQUEST);
            }

            if (stats_id !=  null){
                fantasyStatsService.deleteStatsByID(stats_id);
                Log.info("Fantasy Stats by stats_id deleted successfully");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                List<FantasyStats> deleted_stats = fantasyStatsService.deleteStats(player_id, schedule_id, league_id, client_id);
                Log.info("Fantasy Stats entry has been deleted successfully");
                return new ResponseEntity<>(deleted_stats, HttpStatus.OK);
            }
        } catch (ResourceNotFoundException e) {
            Log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
