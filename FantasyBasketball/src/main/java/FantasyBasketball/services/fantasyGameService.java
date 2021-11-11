package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.repositories.fantasyGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class fantasyGameService {

    @Autowired
    fantasyGameRepository gameRepo;

    // find by ID
    public List<FantasyGame> getByID(Integer fantasyGameID) throws resourceNotFoundException {
        Optional<FantasyGame> result = gameRepo.findById(fantasyGameID);
        if (result.isPresent()) {
            FantasyGame gameResult = result.get();
            return List.of(gameResult);
        } else {
            throw new resourceNotFoundException("Fantasy Game not found by ID in DB.");
        }
    }

    // get operation
    public List<FantasyGame> getGamesByTemplate(Integer schedule_id,      Integer league_id,
                                                Integer home_team_id,     Integer away_team_id,
                                                Date game_start_date,     Date game_end_date,
                                                Integer winner_id,
                                                Integer home_points,      Integer away_points,
                                                Integer home_start_pg_id, Integer home_start_sg_id,
                                                Integer home_start_sf_id, Integer home_start_pf_id,
                                                Integer home_start_c_id,
                                                Integer home_bench_1_id, Integer home_bench_2_id,
                                                Integer away_start_pg_id, Integer away_start_sg_id,
                                                Integer away_start_sf_id, Integer away_start_pf_id,
                                                Integer away_start_c_id,
                                                Integer away_bench_1_id, Integer away_bench_2_id) {
        return gameRepo.findByTemplate(schedule_id, league_id, home_team_id, away_team_id,
                game_start_date, game_end_date, winner_id, home_points, away_points,
                home_start_pg_id, home_start_sg_id, home_start_sf_id, home_start_pf_id, home_start_c_id,
                home_bench_1_id, home_bench_2_id,
                away_start_pg_id, away_start_sg_id, away_start_sf_id, away_start_pf_id, away_start_c_id,
                away_bench_1_id, away_bench_2_id);
    }

    // post operation
    public List<FantasyGame> postGame(FantasyGame game) {
        game.setScheduleID(0);
        FantasyGame result = gameRepo.save(game);
        return List.of(result);
    }

    // put operation
    public List<FantasyGame> updateGame(FantasyGame game) throws resourceNotFoundException {
        if(gameRepo.existsById(game.getScheduleID())) {
            FantasyGame result = gameRepo.save(game);
            return List.of(result);
        } else {
            throw new resourceNotFoundException("Fantasy Game not found by ID in DB, cannot update.");
        }
    }

    // delete operation
    public void deleteGameById(Integer game_id) throws resourceNotFoundException {
        if(gameRepo.existsById(game_id)) {
            gameRepo.deleteById(game_id);
        } else {
            throw new resourceNotFoundException("Fantasy Game not found in DB, cannot delete.");
        }
    }

}
