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
import java.util.Set;

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
        //current_date = LocalDate.of(2021, 12, 25);
        List<FantasyGame> gameList = gameService.getGamesForWeek(current_date);

        // TODO: put this in its own function
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

        // get games from last week
        List<FantasyGame> lastWeekGameList = gameService.getGamesForWeek(current_date.minusWeeks(1));
        HashMap<Integer, Integer> lastWeekStats = statService.generatePlayerStat(generateScheduleSet(lastWeekGameList));


        // calculate the scores from each players from fantasy stats in the game
        for (FantasyGame game: lastWeekGameList){
            Integer homePg=lastWeekStats.get(game.getStartHomePG());
            Integer homeSg=lastWeekStats.get(game.getStartHomeSG());
            Integer homeSf=lastWeekStats.get(game.getStartHomeSF());
            Integer homePf=lastWeekStats.get(game.getStartHomePF());
            Integer homeC=lastWeekStats.get(game.getStartHomeC());
            Integer homeB1=lastWeekStats.get(game.getHomeBench1());
            Integer homeB2=lastWeekStats.get(game.getHomeBench2());
            Integer homePoints= (int) Math.round(homePg+homeSg+homeSf+homePf+homeC+
                    (0.5*homeB1)+(0.5*homeB2));

            Integer awayPg=lastWeekStats.get(game.getStartAwayPG());
            Integer awaySg=lastWeekStats.get(game.getStartAwaySG());
            Integer awaySf=lastWeekStats.get(game.getStartAwaySF());
            Integer awayPf=lastWeekStats.get(game.getStartAwayPF());
            Integer awayC=lastWeekStats.get(game.getStartAwayC());
            Integer awayB1=lastWeekStats.get(game.getAwayBench1());
            Integer awayB2=lastWeekStats.get(game.getAwayBench2());
            Integer awayPoints= (int) Math.round(awayPg+awaySg+awaySf+awayPf+awayC+
                    (0.5*awayB1)+(0.5*awayB2));

            game.setHomePoints(homePoints);
            game.setAwayPoints(awayPoints);

            if (homePoints>awayPoints){
                game.setWinner(game.getHomeTeamID());
            } else if(homePoints<awayPoints){
                game.setWinner(game.getAwayTeamID());
            } else {
                game.setWinner(null);
            }
        }
        // update home points and away points and winner


        // update fantasy team teamWins, teamLosses, pointsScored, pointsAgainst accordingly
    }

    public String generateScheduleSet(List<FantasyGame> lastWeekGameList) {

        StringBuilder result = new StringBuilder("(");
        for (FantasyGame game : lastWeekGameList) {
            result.append(game.getScheduleID()).append(",");
        }
        result.replace(result.length() - 1, result.length(), ")");
        return result.toString();
    }

//    public List<FantasyTeam>teamPlayingThisWeek(List<FantasyGame> lastWeekGameList){
//
//        for (FantasyGame game:lastWeekGameList){
//            teamService.getByID(game.getHomeTeamID());
//
//        }
//    }

}
