package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.services.fantasyGameService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class fantasyGameTests {

    @Autowired
    private fantasyGameService gameService;

    @MockBean
    private fantasyGameRepository gameRepo;

    private FantasyGame genericGame;

    @BeforeEach
    public void setUp() {
        //setUp function
//        LocalDate startDate = LocalDate.of(2022, 12, 10);
//        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
//        genericGame.setScheduleID(1);
    }

    @AfterEach
    public void tearDown() {
        //tearDown function
    }

    // testing getting games by template
    @Test
    public void testGetByTemplate() {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);

        List<FantasyGame> test = List.of(genericGame);
        // testing when result is one element
        when(gameRepo.findByTemplate(1, null, null, null, null, null, null, null)).thenReturn(List.of(genericGame));
        List<FantasyGame> result = gameService.getGamesByTemplate(1, null, null, null, null, null, null, null);
        assertEquals(1, result.size());

        // testing when no result
        Integer nonExistingID = 9999999;
        when(gameRepo.findByTemplate(nonExistingID, null, null, null, null, null, null, null))
                .thenReturn(Collections.emptyList());
        assertEquals(0, gameService.getGamesByTemplate(nonExistingID, null, null, null, null, null, null, null).size());
    }

    // testing getting games by ID when doesn't exist
    @Test(expected = resourceNotFoundException.class)
    public void testGetByIDNotExisting() throws resourceNotFoundException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);

        // testing when result does not exist
        Integer nonExistingID = 9999999;
        when(gameRepo.findById(nonExistingID)).thenReturn(Optional.empty());
        gameService.getByID(nonExistingID);

    }

    // testing getting games when ID does exist
    @Test
    public void testGetByIDExisting() throws resourceNotFoundException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);

        // testing when result exists
        Integer existingID = 1;
        Optional<FantasyGame> existingResult = Optional.of(genericGame);
        when(gameRepo.findById(existingID)).thenReturn(existingResult);
        assertEquals(1, gameService.getByID(existingID).size());

    }

    // testing normal posting game functionality
    @Test
    public void testPostGame() {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);

        // testing normal operation
        when(gameRepo.save(genericGame)).thenReturn(genericGame);
        assertEquals(1, gameService.postGame(genericGame).size());
        // assert typing of result is the same too!
    }

    // testing normal put operation
    @Test
    public void testPutGame() throws resourceNotFoundException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);

        // testing normal put functionality
        when(gameRepo.existsById(genericGame.getScheduleID())).thenReturn(true);
        when(gameRepo.save(genericGame)).thenReturn(genericGame);
        List<FantasyGame> result = gameService.updateGame(genericGame);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getScheduleID());
        assertEquals(startDate, result.get(0).getGameStartDate());

    }

    // testing put operation where resource does not exist
    @Test(expected = resourceNotFoundException.class)
    public void testPutGameNotFoundException() throws resourceNotFoundException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);

        // testing put functionality where generic game does not already exist in DB
        when(gameRepo.existsById(genericGame.getScheduleID())).thenReturn(false);
        when(gameRepo.save(genericGame)).thenReturn(genericGame);
        gameService.updateGame(genericGame);

    }

    // testing regular deletion
    @Test
    public void testDeleteGameByID() throws resourceNotFoundException {

        when(gameRepo.existsById(1)).thenReturn(true);
        gameService.deleteGameById(1);

    }

    // testing deletion where ID does not exist
    @Test(expected = resourceNotFoundException.class)
    public void testDeleteGameByIDNotFoundException() throws resourceNotFoundException {

        when(gameRepo.existsById(1)).thenReturn(false);
        gameService.deleteGameById(1);
    }

    // testing checkPostInputs with invalid scheduleID
    @Test(expected = resourceException.class)
    public void testCheckPostInputsNonNullScheduleID() throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);
        gameService.checkPostInputs(genericGame);

    }

    // testing checkPostInputs with startDate after endDate
    @Test(expected = resourceException.class)
    public void testCheckPostInputsStartDateAfterEndDate () throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.minusWeeks(1));
        genericGame.setScheduleID(null);
        gameService.checkPostInputs(genericGame);
    }

    // testing checkPostInputs with less than 14 days between start and end date
    @Test(expected = resourceException.class)
    public void testCheckPostInputsDateTimeBetween() throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(null);
        gameService.checkPostInputs(genericGame);

    }

    // testing with dates not initialized
    @Test(expected = resourceException.class)
    public void testCheckPostInputsDatesNull() throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, null, null);
        genericGame.setScheduleID(null);
        gameService.checkPostInputs(genericGame);

    }

    // testing checkPostInputs with invalid scheduleID
    @Test(expected = resourceException.class)
    public void testCheckPutInputsNonNullScheduleID() throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);
        gameService.checkPutInputs(genericGame);

    }

    // testing checkPostInputs with invalid scheduleID
    @Test(expected = resourceException.class)
    public void testCheckPutInputsNullScheduleID() throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(null);
        gameService.checkPutInputs(genericGame);

    }

    // testing getGamesForWeek
    @Test
    public void testGetGamesForWeek() {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(4));
        when(gameRepo.findGamesGivenDate(startDate)).thenReturn(List.of(genericGame));

        assertEquals(1, gameService.getGamesForWeek(startDate).size());
    }

    @Test
    public void testGenerateStatsSheet() {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 2, 3,
                                    4, 5,
                                    startDate, startDate.plusWeeks(4),
                                    null, null, null,
                                    10, 11, 12, 13, 14, 15, 16,
                                    20, 21, 22, 23, 24, 25, 26);
        List<FantasyStats> result = gameService.generateStatsSheet(genericGame);
        assertEquals(14, result.size());

        // checking home team player IDs
        assertEquals(10, result.get(0).getPlayer_id());
        assertEquals(11, result.get(1).getPlayer_id());
        assertEquals(12, result.get(2).getPlayer_id());
        assertEquals(13, result.get(3).getPlayer_id());
        assertEquals(14, result.get(4).getPlayer_id());
        assertEquals(15, result.get(5).getPlayer_id());
        assertEquals(16, result.get(6).getPlayer_id());

        // checking away team player IDs
        assertEquals(20, result.get(7).getPlayer_id());
        assertEquals(21, result.get(8).getPlayer_id());
        assertEquals(22, result.get(9).getPlayer_id());
        assertEquals(23, result.get(10).getPlayer_id());
        assertEquals(24, result.get(11).getPlayer_id());
        assertEquals(25, result.get(12).getPlayer_id());
        assertEquals(26, result.get(13).getPlayer_id());

        // check to make sure schedule_id assigned correctly
        assertEquals(1, result.get(0).getSchedule_id());

        // check to make sure league_id assigned correctly
        assertEquals(2, result.get(0).getLeague_id());

        // check to make sure client_id assigned correctly
        assertEquals(3, result.get(0).getClient_id());
    }




}
