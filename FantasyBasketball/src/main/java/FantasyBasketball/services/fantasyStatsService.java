package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.repositories.fantasyStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class fantasyStatsService {

    @Autowired
    fantasyStatsRepository statsRepo;

    // find by stats_id
    public List<FantasyStats> getByID(Integer statsID) throws resourceNotFoundException {
        Optional<FantasyStats> result = statsRepo.findById(statsID);
        if (result.isPresent()) {
            FantasyStats statsResult = result.get();
            return List.of(statsResult);
        } else {
            throw new resourceNotFoundException("Fantasy Stats couldn't be found by ID in DB");
        }
    }

    // get operation
    public List<FantasyStats> getStatsByTemplate(
            Integer stats_id,
            Integer client_id,
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
        return statsRepo.findByTemplate(
                stats_id,
                client_id,
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
    public List<FantasyStats> deleteStats(Integer player_id, Integer schedule_id) throws resourceNotFoundException {
        List<FantasyStats> stats_to_delete = statsRepo.findByPlayerIDAndScheduleID(player_id, schedule_id);

        for (FantasyStats stat_to_delete:stats_to_delete) {
            deleteStatsByID(stat_to_delete.getStats_id());
        }

        return stats_to_delete;
    }

    public void deleteStatsByID(Integer stats_id) throws resourceNotFoundException {
        if(statsRepo.existsById(stats_id)) {
            statsRepo.deleteById(stats_id);
        } else {
            throw new resourceNotFoundException("Fantasy Stats not found in DB, cannot delete");
        }
    }

    // Post operation
    public List<FantasyStats> postStats(FantasyStats stats) {
        stats.setStats_id(0);
        FantasyStats result = statsRepo.save(stats);

        return List.of(result);
    }

    // Put Operation
    public List<FantasyStats> updateStats(FantasyStats stats) throws resourceNotFoundException {
        if(statsRepo.existsById(stats.getStats_id())) {
            FantasyStats result = statsRepo.save(stats);
            return List.of(result);
        } else {
            throw new resourceNotFoundException("Fantasy Stats not found in DB. Cannot update");
        }
    }

    public void checkPostInputs(FantasyStats stats) throws resourceException {
        if (stats.getStats_id() != null) {
            throw new resourceException("Do not provide stats_id.");
        }
    }

}
