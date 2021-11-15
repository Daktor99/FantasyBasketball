package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.repositories.fantasyStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class fantasyStatsService {

    @Autowired
    fantasyStatsRepository statsRepo;

    // find by playerID
    public List<FantasyStats> getByID(Integer fantasyStatsID) throws resourceNotFoundException {
        Optional<FantasyStats> result = statsRepo.findById(fantasyStatsID);
        if (result.isPresent()) {
            FantasyStats statsResult = result.get();
            return List.of(statsResult);
        } else {
            throw new resourceNotFoundException("Fantasy Stats couldn't be found by ID in DB");
        }
    }

    // get operation
    public List<FantasyStats> getStatsByTemplate(Integer client_id,
                                                 Integer player_id,
                                                 Integer schedule_id,
                                                 Integer threeP,
                                                 Integer twoP,
                                                 Integer freeThrows,
                                                 Integer rebounds,
                                                 Integer assists,
                                                 Integer blocks,
                                                 Integer steals,
                                                 Integer turnovers,
                                                 Integer totPoints) {
        return statsRepo.findByTemplate(client_id,
                player_id,
                schedule_id,
                threeP,
                twoP,
                freeThrows,
                rebounds,
                assists,
                blocks,
                steals,
                turnovers,
                totPoints);
    }

    // delete operations
    public void deleteStatsByScheduleID(Integer schedule_id) throws resourceNotFoundException {
        List<FantasyStats> result = statsRepo.deleteByScheduleID(schedule_id);
        if (result.size() == 0) {
            throw new resourceNotFoundException("Fantasy Stats by schedule_id not found in DB");
        }
    }

    public void deleteStatsByPlayerID(Integer player_id) throws resourceNotFoundException {
        List<FantasyStats> result = statsRepo.deleteByPlayerId(player_id);
        if (result.size() == 0) {
            throw new resourceNotFoundException("Fantasy Stats by player_id not found in DB");
        }
    }

    public void deleteStats(Integer player_id, Integer schedule_id) throws resourceNotFoundException {
        if (player_id == null) {
            deleteStatsByScheduleID(schedule_id);
        }
        else if (schedule_id == null) {
            deleteStatsByPlayerID(player_id);
        } else{
            List<FantasyStats> result = statsRepo.findByPlayerIDAndScheduleID(player_id, schedule_id);
            if (result.size() > 0) {
                statsRepo.deleteByPlayerIDAndScheduleID(player_id, schedule_id);
            } else {
                throw new resourceNotFoundException("Fantasy Stats not found in DB. Cannot delete");
            }
        }
    }

    // Post operation
    public List<FantasyStats> postStats(FantasyStats stats) {
        List<FantasyStats> result = statsRepo.findByPlayerIDAndScheduleID(stats.getPlayerID(), stats.getScheduleID());
        if (result.size() == 0) {
            FantasyStats newStats = statsRepo.save(stats);
            return List.of(newStats);
        } else {
            throw new DataIntegrityViolationException("This Fantasy Stats is already on the table");
        }

    }

    // Put Operation
    public List<FantasyStats> updateStats(FantasyStats stats) throws resourceNotFoundException {
        List<FantasyStats> result = statsRepo.findByPlayerIDAndScheduleID(
                stats.getPlayerID()
                , stats.getScheduleID());

        if (result.size() > 0) {
            FantasyStats newStats = statsRepo.save(stats);
            return List.of(newStats);
        } else {
            throw new resourceNotFoundException("Fantasy Stats not found in DB. Cannot update");
        }
    }

}
