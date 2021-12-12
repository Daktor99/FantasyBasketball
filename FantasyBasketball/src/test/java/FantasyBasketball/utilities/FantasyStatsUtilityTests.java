package FantasyBasketball.utilities;

import FantasyBasketball.models.*;
import FantasyBasketball.repositories.clientRepository;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.repositories.fantasyStatsRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.previous;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FantasyStatsUtilityTests {
    private final Integer CLIENT_ID_TEST = 1;
    private final Integer LEAGUE_ID_TEST = 1;
    private final Integer PLAYER_HOME_ID_TEST = 1;
    private final Integer PLAYER_AWAY_ID_TEST = 2;
    private final Integer TEAM_HOME_ID_TEST = 1;
    private final Integer TEAM_AWAY_ID_TEST = 2;
    private final Integer SCHEDULE_ID_TEST = 1;
    private final Integer STATS_HOME_ID_TEST = 1;
    private final FantasyStatsUtility fantasyStatsUtility = new FantasyStatsUtility();
    @MockBean
    private fantasyPlayerRepository fantasyPlayerRepo;
    @MockBean
    private fantasyStatsRepository fantasyStatsRepo;
    @MockBean
    private fantasyGameRepository fantasyGameRepo;
    @MockBean
    private clientRepository clientRepo;
    private Date START_DATE;
    private LocalDate LOCAL_DATE;
    private Client CLIENT_TEST;
    private FantasyGame GAME_TEST;
    private FantasyPlayer PLAYER_HOME_TEST;
    private FantasyPlayer PLAYER_AWAY_TEST;
    private FantasyStats STATS_HOME_PLAYER_TEST;
    private FantasyStats STATS_AWAY_PLAYER_TEST;

    @Before
    public void setUp() {

        START_DATE = new Date();
        LOCAL_DATE = fantasyStatsUtility.convertToLocalDate(START_DATE);

        CLIENT_TEST = new Client();
        CLIENT_TEST.setClientID(CLIENT_ID_TEST);

        User USER_HOME_TEST = new User();
        Integer USER_HOME_ID_TEST = 1;
        USER_HOME_TEST.setUserID(USER_HOME_ID_TEST);
        USER_HOME_TEST.setClientID(CLIENT_ID_TEST);

        User USER_AWAY_TEST = new User();
        Integer USER_AWAY_ID_TEST = 2;
        USER_AWAY_TEST.setUserID(USER_AWAY_ID_TEST);
        USER_AWAY_TEST.setClientID(CLIENT_ID_TEST);

        FantasyLeague LEAGUE_TEST = new FantasyLeague();
        LEAGUE_TEST.setClientID(CLIENT_ID_TEST);
        LEAGUE_TEST.setLeagueID(LEAGUE_ID_TEST);

        PLAYER_HOME_TEST = new FantasyPlayer();
        PLAYER_HOME_TEST.setPlayerID(PLAYER_HOME_ID_TEST);
        PLAYER_HOME_TEST.setPlayerID(PLAYER_HOME_ID_TEST);
        PLAYER_HOME_TEST.setTeamID(TEAM_HOME_ID_TEST);
        PLAYER_HOME_TEST.setLeagueID(LEAGUE_ID_TEST);
        PLAYER_HOME_TEST.setClientID(CLIENT_ID_TEST);
        Integer PLAYER_HOME_BALLAPI_ID_TEST = 192;
        PLAYER_HOME_TEST.setBallapiID(PLAYER_HOME_BALLAPI_ID_TEST);

        PLAYER_AWAY_TEST = new FantasyPlayer();
        PLAYER_AWAY_TEST.setPlayerID(PLAYER_AWAY_ID_TEST);
        PLAYER_AWAY_TEST.setTeamID(TEAM_AWAY_ID_TEST);
        PLAYER_AWAY_TEST.setLeagueID(LEAGUE_ID_TEST);
        PLAYER_AWAY_TEST.setClientID(CLIENT_ID_TEST);
        Integer PLAYER_AWAY_BALLAPI_ID_TEST = 360;
        PLAYER_AWAY_TEST.setBallapiID(PLAYER_AWAY_BALLAPI_ID_TEST);

        FantasyTeam TEAM_HOME_TEST = new FantasyTeam();
        TEAM_HOME_TEST.setClientID(CLIENT_ID_TEST);
        TEAM_HOME_TEST.setLeagueID(LEAGUE_ID_TEST);
        TEAM_HOME_TEST.setTeamID(TEAM_HOME_ID_TEST);
        TEAM_HOME_TEST.setOwnerID(USER_HOME_ID_TEST);

        FantasyTeam TEAM_AWAY_TEST = new FantasyTeam();
        TEAM_AWAY_TEST.setClientID(CLIENT_ID_TEST);
        TEAM_AWAY_TEST.setLeagueID(LEAGUE_ID_TEST);
        TEAM_AWAY_TEST.setTeamID(TEAM_AWAY_ID_TEST);
        TEAM_AWAY_TEST.setOwnerID(USER_AWAY_ID_TEST);

        GAME_TEST = new FantasyGame();
        GAME_TEST.setClientID(CLIENT_ID_TEST);
        GAME_TEST.setLeagueID(LEAGUE_ID_TEST);
        GAME_TEST.setScheduleID(SCHEDULE_ID_TEST);
        GAME_TEST.setHomeTeamID(TEAM_HOME_ID_TEST);
        GAME_TEST.setAwayTeamID(TEAM_AWAY_ID_TEST);
        GAME_TEST.setStartHomePG(PLAYER_HOME_ID_TEST);
        GAME_TEST.setStartAwayPG(PLAYER_AWAY_ID_TEST);
        GAME_TEST.setGameStartDate(LOCAL_DATE.with(previous(SUNDAY)));
        GAME_TEST.setGameEndDate(LOCAL_DATE.with(next(SUNDAY)));


        Integer STATS_TEST_VALUE = -1;
        STATS_HOME_PLAYER_TEST = new FantasyStats(
                STATS_HOME_ID_TEST,
                PLAYER_HOME_ID_TEST,
                SCHEDULE_ID_TEST,
                CLIENT_ID_TEST,
                LEAGUE_ID_TEST,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE
        );

        Integer STATS_AWAY_ID_TEST = 2;
        STATS_AWAY_PLAYER_TEST = new FantasyStats(
                STATS_AWAY_ID_TEST,
                PLAYER_AWAY_ID_TEST,
                SCHEDULE_ID_TEST,
                CLIENT_ID_TEST,
                LEAGUE_ID_TEST,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE,
                STATS_TEST_VALUE
        );

    }

    @After
    public void tearDown() {
        //tearDown function
    }

    @Test
    public void test_successful_API_import() throws IOException {

        List<FantasyGame> listOfGames = List.of(GAME_TEST);
        List<FantasyPlayer> listOfPlayerHome = List.of(PLAYER_HOME_TEST);
        List<FantasyPlayer> listOfAwayHome = List.of(PLAYER_AWAY_TEST);
        List<Client> listOfClients = List.of(CLIENT_TEST);
        List<FantasyStats> listOfPlayerHomeStats = List.of(STATS_HOME_PLAYER_TEST);
        List<FantasyStats> listOfPlayerAwayStats = List.of(STATS_AWAY_PLAYER_TEST);

        Mockito.when(fantasyGameRepo.findGamesGivenDate(LOCAL_DATE)).thenReturn(listOfGames);

        Mockito.when(fantasyPlayerRepo.findByTemplate(
                PLAYER_HOME_ID_TEST,
                CLIENT_ID_TEST,
                TEAM_HOME_ID_TEST,
                LEAGUE_ID_TEST,
                null,
                null,
                null,
                null,
                null)).thenReturn(listOfPlayerHome);
        Mockito.when(fantasyPlayerRepo.findByTemplate(
                PLAYER_AWAY_ID_TEST,
                CLIENT_ID_TEST,
                TEAM_AWAY_ID_TEST,
                LEAGUE_ID_TEST,
                null,
                null,
                null,
                null,
                null)).thenReturn(listOfAwayHome);

        Mockito.when(clientRepo.findByTemplate(
                CLIENT_ID_TEST,
                null,
                null,
                null)).thenReturn(listOfClients);

        Mockito.when(fantasyStatsRepo.findByTemplate(
                null,
                PLAYER_HOME_ID_TEST,
                SCHEDULE_ID_TEST,
                CLIENT_ID_TEST,
                LEAGUE_ID_TEST,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)).thenReturn(listOfPlayerHomeStats);

        Mockito.when(fantasyStatsRepo.findByTemplate(
                null,
                PLAYER_AWAY_ID_TEST,
                SCHEDULE_ID_TEST,
                CLIENT_ID_TEST,
                LEAGUE_ID_TEST,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)).thenReturn(listOfPlayerAwayStats);

        // Calls API gets updated stats
        Integer STATS_UPDATED_VALUES = 1;
        FantasyStats STATS_UPDATED_PLAYER = new FantasyStats(
                STATS_HOME_ID_TEST,
                PLAYER_HOME_ID_TEST,
                SCHEDULE_ID_TEST,
                CLIENT_ID_TEST,
                LEAGUE_ID_TEST,
                STATS_UPDATED_VALUES,
                STATS_UPDATED_VALUES,
                STATS_UPDATED_VALUES,
                STATS_UPDATED_VALUES,
                STATS_UPDATED_VALUES,
                STATS_UPDATED_VALUES,
                STATS_UPDATED_VALUES,
                STATS_UPDATED_VALUES,
                STATS_UPDATED_VALUES
        );

//        List<FantasyStats> listOfUpdatedStats = List.of(STATS_UPDATED_PLAYER);

        Mockito.when(fantasyStatsRepo.save(any(FantasyStats.class))).thenReturn(STATS_UPDATED_PLAYER);

        fantasyStatsUtility.API_player_stats_importation(
                fantasyPlayerRepo,
                fantasyGameRepo,
                fantasyStatsRepo,
                clientRepo,
                START_DATE);

        Assertions.assertNotEquals(STATS_UPDATED_PLAYER.getTot_points(), STATS_HOME_PLAYER_TEST.getTot_points());

        Assertions.assertNotEquals(STATS_UPDATED_PLAYER.getTot_points(), STATS_AWAY_PLAYER_TEST.getTot_points());
    }

}
