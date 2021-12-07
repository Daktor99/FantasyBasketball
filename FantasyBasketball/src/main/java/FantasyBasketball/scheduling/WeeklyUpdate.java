package FantasyBasketball.scheduling;

import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.services.fantasyGameService;
import FantasyBasketball.services.fantasyStatsService;
import FantasyBasketball.services.fantasyTeamService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeeklyUpdate {

    @Autowired
    fantasyTeamService teamService;

    @Autowired
    fantasyGameService gameService;

    @Autowired
    fantasyStatsService statService;

    public void runWeekly(fantasyTeamService teamService, fantasyGameService gameService, fantasyStatsService statService) throws resourceNotFoundException {

        List<FantasyTeam> teamList = teamService.getTeamsByTemplate(null, null, null, null, null);
        HashMap<Integer, List<Integer>> teamMap = new HashMap<>();

        for (FantasyTeam team : teamList) {
            Integer team_id = team.getTeamID();
            List<Integer> starters = new ArrayList<>();

            // TODO: check to see if NULL cases break code!!!
            starters.add(team.getStartPG());
            starters.add(team.getStartSG());
            starters.add(team.getStartSF());
            starters.add(team.getStartPF());
            starters.add(team.getStartC());
            starters.add(team.getBench1());
            starters.add(team.getBench2());

            teamMap.put(team_id, starters);
        }

        LocalDate current_date = LocalDate.now();
        // TODO: remove current date overwriting below that is being used for testing
        current_date = LocalDate.of(2021, 12, 25);
        List<FantasyGame> gameList = gameService.getGamesForWeek(current_date);

        for (FantasyGame game : gameList) {
            Integer home_team_id = game.getHomeTeamID();
            Integer away_team_id = game.getAwayTeamID();

            List<Integer> home_starters = teamMap.get(home_team_id);
            List<Integer> away_starters = teamMap.get(away_team_id);

            // setting home starters
            game.setStartHomePG(home_starters.get(0));
            game.setStartHomeSG(home_starters.get(1));
            game.setStartHomeSF(home_starters.get(2));
            game.setStartHomePF(home_starters.get(3));
            game.setStartHomeC(home_starters.get(4));
            game.setHomeBench1(home_starters.get(5));
            game.setHomeBench2(home_starters.get(6));

            // setting away starters
            game.setStartAwayPG(away_starters.get(0));
            game.setStartAwaySG(away_starters.get(1));
            game.setStartAwaySF(away_starters.get(2));
            game.setStartAwayPF(away_starters.get(3));
            game.setStartAwayC(away_starters.get(4));
            game.setAwayBench1(away_starters.get(5));
            game.setAwayBench2(away_starters.get(6));

            // TODO: check exception being thrown!
            gameService.updateGame(game);

            List<FantasyStats> statSheet = gameService.generateStatsSheet(game);
            statService.postStatSheet(statSheet);

        }
    }
}
