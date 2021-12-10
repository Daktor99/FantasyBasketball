package FantasyBasketball.scheduling;

import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.services.fantasyGameService;
import FantasyBasketball.services.fantasyStatsService;
import FantasyBasketball.services.fantasyTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class WeeklyUpdate {

    @Autowired
    fantasyTeamService teamService;

    @Autowired
    fantasyGameService gameService;

    @Autowired
    fantasyStatsService statService;

    public void runWeekly(fantasyTeamService teamService, fantasyGameService gameService, fantasyStatsService statService) throws resourceNotFoundException {

        // get a list of all teams and create a hashmap to store starters
        List<FantasyTeam> teamList = teamService.getTeamsByTemplate(null, null, null, null, null);
        HashMap<Integer, List<Integer>> teamStarters = new HashMap<>();

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
            teamStarters.put(team_id, starters);
        }

        // TODO: remove current date overwriting below that is being used for testing
        LocalDate current_date = LocalDate.now();
        //current_date = LocalDate.of(2021, 12, 25);
        List<FantasyGame> gameList = gameService.getGamesForWeek(current_date);

        // TODO: put this in its own function
        for (FantasyGame game : gameList) {
            Integer home_team_id = game.getHomeTeamID();
            Integer away_team_id = game.getAwayTeamID();

            List<Integer> home_starters = teamStarters.get(home_team_id);
            List<Integer> away_starters = teamStarters.get(away_team_id);

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
        List<FantasyGame> lastWeekGamesList = gameService.getGamesForWeek(current_date.minusWeeks(1));
        HashMap<Integer, Integer> lastWeekStats = statService.generatePlayerStat(generateScheduleSet(lastWeekGamesList));

        // get a list of the updated games according to stats and update the games in the DB
        // make hashmap of all teams
        HashMap<Integer, FantasyTeam> teamMap = new HashMap<>();
        for (FantasyTeam team: teamList) {
            teamMap.put(team.getTeamID(), team);
        }

        updateGamesAndTeams(lastWeekGamesList, lastWeekStats, teamMap, gameService, teamService);
        //List<FantasyGame> gamesToUpdate = getGamesToUpdate(lastWeekGamesList, lastWeekStats);

        // update fantasy team teamWins, teamLosses, pointsScored, pointsAgainst accordingly


        // look through each game and update team accordingly

    }

    public Set<Integer> generateScheduleSet(List<FantasyGame> lastWeekGameList) {

        Set<Integer> result = new HashSet<>();
        for (FantasyGame game: lastWeekGameList) {
            result.add(game.getScheduleID());
        }
        return result;
    }

    public void updateGamesAndTeams(List<FantasyGame> lastWeekGamesList,
                                    HashMap<Integer, Integer> lastWeekStats,
                                    HashMap<Integer, FantasyTeam> teamMap,
                                    fantasyGameService gameService,
                                    fantasyTeamService teamService) {

        List<FantasyGame> gamesToUpdate = new ArrayList<>();
        List<FantasyTeam> teamsToUpdate = new ArrayList<>();

        // calculate the scores from each players from fantasy stats in the game
        for (FantasyGame game: lastWeekGamesList){
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

            // pull teams records to update their records
            FantasyTeam homeTeam = teamMap.get(game.getHomeTeamID());
            FantasyTeam awayTeam = teamMap.get(game.getAwayTeamID());

            // add to points scored and points_against for home team
            homeTeam.setPointsScored(homeTeam.getPointsScored() + homePoints);
            homeTeam.setPointsAgainst(homeTeam.getPointsAgainst() + awayPoints);

            // add to points scored and points_against for away team
            awayTeam.setPointsScored(awayTeam.getPointsScored() + awayPoints);
            awayTeam.setPointsAgainst(awayTeam.getPointsAgainst() + homePoints);

            // update home points and away points and winner
            if(homePoints < awayPoints) {
                // if home loses and away wins, set the game winner to the away team
                game.setWinner(game.getAwayTeamID());

                // add a loss to the home team and a win to the away team
                homeTeam.setTeamLosses(homeTeam.getTeamLosses() + 1);
                awayTeam.setTeamWins(awayTeam.getTeamWins() + 1);

            } else {
                // otherwise the home team wins, set the game winner to the home team
                game.setWinner(game.getHomeTeamID());

                // add a win to the home team and a loss to the away team
                homeTeam.setTeamWins(homeTeam.getTeamWins() + 1);
                awayTeam.setTeamLosses(awayTeam.getTeamLosses() + 1);
            }

            // done processing game, add it to the list to be updated
            gamesToUpdate.add(game);

            // done processing home team and away team, add both to teamsToUpdate
            teamsToUpdate.add(homeTeam);
            teamsToUpdate.add(awayTeam);

        }

        gameService.postMultipleGames(gamesToUpdate);
        teamService.postMultipleTeams(teamsToUpdate);
    }

//    public List<FantasyTeam>teamPlayingThisWeek(List<FantasyGame> lastWeekGameList){
//
//        for (FantasyGame game:lastWeekGameList){
//            teamService.getByID(game.getHomeTeamID());
//
//        }
//    }

}
