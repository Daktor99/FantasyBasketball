package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.repositories.fantasyStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class fantasyGameService {

    @Autowired
    fantasyGameRepository gameRepo;

    @Autowired
    fantasyStatsRepository statsRepo;

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
    public List<FantasyGame> getGamesByTemplate(Integer schedule_id,
                                                Integer league_id,
                                                Integer client_id,
                                                Integer home_team_id,
                                                Integer away_team_id,
                                                LocalDate game_start_date,
                                                LocalDate game_end_date,
                                                Integer winner_id) {
        return gameRepo.findByTemplate(schedule_id, league_id, client_id, home_team_id, away_team_id,
                game_start_date, game_end_date, winner_id);
    }

    // post operation
    public List<FantasyGame> postGame(FantasyGame game) {
        game.setScheduleID(0);
        FantasyGame result = gameRepo.save(game);
        return List.of(result);
    }

    public List<FantasyGame> postMultipleGames(List<FantasyGame> games) {
        return (List<FantasyGame>) gameRepo.saveAll(games);
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

    // Checking if game's schedule is null before posting
    public void checkPostInputs(FantasyGame game) throws resourceException {
        if (game.getScheduleID() != null) {
            throw new resourceException("Do not provide schedule_id.");
        }
        checkInputs(game);
    }
    // Checking if game's schedule is null before putting
    public void checkPutInputs(FantasyGame game) throws resourceException {
            if (game.getScheduleID() == null) {
                throw new resourceException("Must provide schedule_id to update Game.");
            }
            checkInputs(game);

    }
    // Checking if game's start date and end date are correct
    private void checkInputs(FantasyGame game) throws resourceException {
        try {
            LocalDate startDate = game.getGameStartDate();
            LocalDate endDate   = game.getGameEndDate();
            int daysBetween     = Period.between(startDate, endDate).getDays();
            if (endDate.isBefore(startDate)) {
                throw new resourceException("Start Date must be before End Date");
            }
        } catch (NullPointerException e) {
            throw new resourceException("Fantasy game formatted incorrectly, please provide at least the following:\n schedule_id, leagueID, home_team_id, away_team_id, game_start_date, game_end_date.");
        }
    }
    // Getting the games for the current_date
    public List<FantasyGame> getGamesForWeek(LocalDate current_date) {
        return gameRepo.findGamesGivenDate(current_date);
    }

    // Generating FantasyStats from FantasyGame info
    /*
    * Takes a game and its corresponding players (starting home players and starting away players) and returns:
    *
    * List of Fantasy Stats in the order seen in FantasyGame
    *
    * */
    public List<FantasyStats> generateStatsSheet(FantasyGame game) {

        Integer schedule_id = game.getScheduleID();
        Integer client_id   = game.getClientID();
        Integer league_id   = game.getLeagueID();
        List<FantasyStats> statsList = new ArrayList<>();

        // adding stat sheets for home players
        statsList.add(new FantasyStats(game.getStartHomePG(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getStartHomeSG(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getStartHomeSF(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getStartHomePF(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getStartHomeC(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getHomeBench1(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getHomeBench2(), schedule_id, client_id, league_id));

        // adding stat sheets for away players
        statsList.add(new FantasyStats(game.getStartAwayPG(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getStartAwaySG(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getStartAwaySF(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getStartAwayPF(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getStartAwayC(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getAwayBench1(), schedule_id, client_id, league_id));
        statsList.add(new FantasyStats(game.getAwayBench2(), schedule_id, client_id, league_id));

        // save stats into db
        return statsList;

    }
}
