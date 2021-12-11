package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.repositories.fantasyStatsRepository;
import FantasyBasketball.services.fantasyStatsService;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class fantasyStatsTest {

    private final Integer PLAYER_ID_TEST = 1;
    private final Integer SCHEDULE_ID_TEST = 1;
    private final Integer STATS_ID_TEST = 1;
    private final Integer LEAGUE_ID_TEST = 1;
    private final Integer CLIENT_ID_TEST = 1;
    private final Integer TOTAL_POINTS_TEST = 50;

    @Autowired
    fantasyStatsService fantasyStatsService;

    @MockBean
    fantasyStatsRepository statsRepo;

    @BeforeEach
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    FantasyStats genericStats = new FantasyStats(
            1,
            1,
            1,
            1,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            1
    );

    @Test()
    public void testGetByID() throws resourceNotFoundException {
        Integer fantasyStatsID = 1;
        Optional<FantasyStats> result = Optional.of(genericStats);
        Mockito.when(statsRepo.findById(fantasyStatsID)).thenReturn(result);
        assertEquals(List.of(result.get()).get(0).getStats_id(), fantasyStatsService.getByID(fantasyStatsID).get(0).getStats_id());
//        assertEquals(List.of(result.get()), fantasyStatsService.getByID(fantasyStatsID));
    }

    @Test()
    public void testFindByID() throws resourceNotFoundException {
        FantasyStats savedStats = new FantasyStats(
                PLAYER_ID_TEST,
                SCHEDULE_ID_TEST,
                LEAGUE_ID_TEST,
                CLIENT_ID_TEST
        );
        savedStats.setStats_id(STATS_ID_TEST);

        Optional<FantasyStats> db = Optional.of(savedStats);

        when(statsRepo.findById(
                STATS_ID_TEST)).thenReturn(db);

        List<FantasyStats> result = fantasyStatsService.getByID(STATS_ID_TEST);
        assertEquals(result.get(0).getStats_id(), STATS_ID_TEST);
    }

//    @Test (expected = resourceNotFoundException.class)
//    public void testFindByIdWithNonExistent() throws resourceNotFoundException {
//        Optional<FantasyStats> result = Optional.empty() ;
//
//        when(statsRepository.findById(
//                STATS_ID_TEST)).thenReturn(result);
//
//        fantasyStatsService.getByID(STATS_ID_TEST);
//    }
//
//    @Test
//    public void testFindByTemplate() {
//
//        FantasyStats savedStats = new FantasyStats(
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                LEAGUE_ID_TEST,
//                CLIENT_ID_TEST
//        );
//        savedStats.setStats_id(STATS_ID_TEST);
//
//        List<FantasyStats> db =  new LinkedList<>();
//        db.add(savedStats);
//        Mockito.when(statsRepository.findByTemplate(
//                STATS_ID_TEST,
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                CLIENT_ID_TEST,
//                LEAGUE_ID_TEST,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null)).thenReturn(db);
//
//        List<FantasyStats> result = fantasyStatsService.getStatsByTemplate(STATS_ID_TEST,
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                CLIENT_ID_TEST,
//                LEAGUE_ID_TEST,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null);
//
//        assertTrue(result.size() > 0);
//    }
//
//    @Test
//    public void testPostStats(){
//        FantasyStats statsToSave = new FantasyStats(PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                LEAGUE_ID_TEST,
//                CLIENT_ID_TEST
//        );
//
//        FantasyStats savedStats = new FantasyStats(
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                LEAGUE_ID_TEST,
//                CLIENT_ID_TEST
//        );
//        savedStats.setStats_id(STATS_ID_TEST);
//
//        when(statsRepository.save(statsToSave)).thenReturn(savedStats);
//
//        assertEquals(fantasyStatsService.postStats(statsToSave).get(0).getStats_id(), STATS_ID_TEST);
//    }
//
//    @Test()
//    public void testUpdateStats() throws resourceNotFoundException {
//        FantasyStats statsToSave = new FantasyStats(PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                LEAGUE_ID_TEST,
//                CLIENT_ID_TEST
//        );
//
//        FantasyStats savedStats = new FantasyStats(
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                LEAGUE_ID_TEST,
//                CLIENT_ID_TEST
//        );
//        savedStats.setStats_id(STATS_ID_TEST);
//
//        when(statsRepository.save(statsToSave)).thenReturn(savedStats);
//
//        FantasyStats statsToUpdate = new FantasyStats(STATS_ID_TEST,
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                CLIENT_ID_TEST,
//                LEAGUE_ID_TEST,
//                10,
//                10,
//                0,
//                0,
//                5,
//                5,
//                2,
//                5,
//                TOTAL_POINTS_TEST);
//
//        when(statsRepository.save(statsToUpdate)).thenReturn(statsToUpdate);
//        when(statsRepository.existsById(STATS_ID_TEST)).thenReturn(true);
//        fantasyStatsService.postStats(statsToSave);
//
//        assertEquals(fantasyStatsService.updateStats(statsToUpdate).get(0).getTot_points(), TOTAL_POINTS_TEST);
//    }
//
//    @Test (expected = resourceNotFoundException.class)
//    public void testUpdateNonExistingStats() throws resourceNotFoundException {
//        FantasyStats statsToUpdate = new FantasyStats(STATS_ID_TEST,
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                CLIENT_ID_TEST,
//                LEAGUE_ID_TEST,
//                10,
//                10,
//                0,
//                0,
//                5,
//                5,
//                2,
//                5,
//                TOTAL_POINTS_TEST);
//
//        when(statsRepository.save(statsToUpdate)).thenReturn(statsToUpdate);
//        when(statsRepository.existsById(STATS_ID_TEST)).thenReturn(false);
//
//        fantasyStatsService.updateStats(statsToUpdate);
//    }
//
//    @Test()
//    public void testDelete() throws resourceException, resourceNotFoundException {
//        FantasyStats savedStats = new FantasyStats(
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                LEAGUE_ID_TEST,
//                CLIENT_ID_TEST
//        );
//        savedStats.setStats_id(STATS_ID_TEST);
//
//        List<FantasyStats> savedStatsList = new LinkedList<>();
//        savedStatsList.add(savedStats);
//
//        when(statsRepository.findByIDs(PLAYER_ID_TEST,SCHEDULE_ID_TEST,LEAGUE_ID_TEST,CLIENT_ID_TEST)).
//                thenReturn(savedStatsList);
//        when(statsRepository.existsById(STATS_ID_TEST)).thenReturn(true);
//
//        List<FantasyStats> result = fantasyStatsService.deleteStats(
//                PLAYER_ID_TEST,SCHEDULE_ID_TEST,LEAGUE_ID_TEST,CLIENT_ID_TEST);
//
//        assertTrue(result.size() > 0);
//    }
//
//    @Test (expected = resourceNotFoundException.class)
//    public void testDeleteNonExistingStats() throws resourceException, resourceNotFoundException {
//        List<FantasyStats> emptyList = new LinkedList<>();
//        when(statsRepository.findByIDs(PLAYER_ID_TEST,SCHEDULE_ID_TEST,LEAGUE_ID_TEST,CLIENT_ID_TEST)).
//                thenReturn(emptyList);
//
//        List<FantasyStats> result = fantasyStatsService.deleteStats(
//                PLAYER_ID_TEST,SCHEDULE_ID_TEST,LEAGUE_ID_TEST,CLIENT_ID_TEST);
//
//        assertEquals(0, result.size());
//    }
//
//    @Test (expected = resourceException.class)
//    public void testDeleteWithInsufficientParameters() throws resourceException, resourceNotFoundException {
//        FantasyStats savedStats = new FantasyStats(
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                LEAGUE_ID_TEST,
//                CLIENT_ID_TEST
//        );
//        savedStats.setStats_id(STATS_ID_TEST);
//
//        List<FantasyStats> savedStatsList = new LinkedList<>();
//        savedStatsList.add(savedStats);
//
//        when(statsRepository.findByIDs(PLAYER_ID_TEST,SCHEDULE_ID_TEST,LEAGUE_ID_TEST,CLIENT_ID_TEST)).
//                thenReturn(savedStatsList);
//
//        fantasyStatsService.deleteStats(null,null,null,null);
//    }
//
//    @Test()
//    public void testDeleteByStatsID() throws resourceNotFoundException {
//
//        when(statsRepository.existsById(STATS_ID_TEST)).thenReturn(true);
//        fantasyStatsService.deleteStatsByID(STATS_ID_TEST);
//    }
//
//    @Test (expected = resourceNotFoundException.class)
//    public void testDeleteByStatsIDWithNonExistingID() throws resourceNotFoundException {
//        when(statsRepository.existsById(STATS_ID_TEST)).thenReturn(false);
//        fantasyStatsService.deleteStatsByID(STATS_ID_TEST);
//    }
//
//    @Test (expected = resourceException.class)
//    public void testCheckPostInputs() throws resourceException {
//        FantasyStats stats_to_save = new FantasyStats(
//                PLAYER_ID_TEST,
//                SCHEDULE_ID_TEST,
//                LEAGUE_ID_TEST,
//                CLIENT_ID_TEST
//        );
//        stats_to_save.setStats_id(STATS_ID_TEST);
//        fantasyStatsService.checkPostInputs(stats_to_save);
//    }
}
