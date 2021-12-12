package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyStats;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.services.fantasyGameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class fantasyGameTest {

    @Autowired
    fantasyGameService gameService;

    @MockBean
    fantasyGameRepository gameRepo;

    LocalDate genericdate1 = LocalDate.of(2022, 1, 1);
    LocalDate genericdate2 = LocalDate.of(2022, 1, 8);

    FantasyGame genericGame = new FantasyGame(
            1,
            1,
            1,
            1,
            2,
            genericdate1,
            genericdate2,
            null,
            0,
            0,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    );

    @Test
    public void testGetByID() throws resourceNotFoundException {
        Integer fantasyGameID = 1;
        Optional<FantasyGame> result = Optional.of(genericGame);
        Mockito.when(gameRepo.findById(fantasyGameID)).thenReturn(result);
        assertEquals(List.of(result.get()), gameService.getByID(fantasyGameID));
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

    @Test(expected = resourceNotFoundException.class)
    public void testGetByIDExcept() throws resourceNotFoundException {
        Integer fantasyGameID = 1;
        Optional<FantasyGame> result = Optional.empty();
        Mockito.when(gameRepo.findById(fantasyGameID)).thenReturn(result);
        gameService.getByID(fantasyGameID);
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

    // testing normal posting game functionality
    @Test
    public void testPostMultipleGames() {
        List<FantasyGame> gamesList = Arrays.asList(genericGame);
        Mockito.when(gameRepo.saveAll(gamesList)).thenReturn(gamesList);
        assertEquals(gamesList, gameService.postMultipleGames(gamesList));

    }

    @Test(expected = resourceNotFoundException.class)
    public void testUpdateGameExcept() throws resourceNotFoundException {
        Mockito.when(gameRepo.existsById(genericGame.getScheduleID())).thenReturn(Boolean.FALSE);
        gameService.updateGame(genericGame);
    }

    @Test
    public void testUpdateGame() throws resourceNotFoundException {
        Mockito.when(gameRepo.existsById(genericGame.getScheduleID())).thenReturn(Boolean.TRUE);
        Mockito.when(gameRepo.save(genericGame)).thenReturn(genericGame);
        assertEquals(List.of(genericGame), gameService.updateGame((genericGame)));
    }

    @Test(expected = resourceNotFoundException.class)
    public void testDeleteGameByIdExcept() throws resourceNotFoundException {
        Integer game_id = 1;
        Mockito.when(gameRepo.existsById(game_id)).thenReturn(Boolean.FALSE);
        gameService.updateGame(genericGame);
    }

    @Test(expected = resourceException.class)
    public void testCheckIfInDBExcept() throws resourceException {
        Mockito.when(gameService.getGamesByTemplate(
                null,
                genericGame.getLeagueID(),
                genericGame.getClientID(),
                genericGame.getHomeTeamID(),
                genericGame.getAwayTeamID(),
                genericGame.getGameStartDate(),
                genericGame.getGameEndDate(),
                null)).thenReturn(Arrays.asList(genericGame));
        gameService.checkIfInDB(genericGame);
    }

    // testing checkPostInputs with invalid scheduleID
    @Test(expected = resourceException.class)
    public void testCheckPostInputsNonNullScheduleID() throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);
        gameService.checkPostInputs(genericGame);

    }

    // testing checkPostInputs with invalid scheduleID
    @Test(expected = resourceException.class)
    public void testCheckPostInputshome() throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, null, 2, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);
        gameService.checkPostInputs(genericGame);

    }

    // testing checkPostInputs with invalid scheduleID
    @Test(expected = resourceException.class)
    public void testCheckPostInputsaway() throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(1, 1, 1, null, startDate, startDate.plusWeeks(1));
        genericGame.setScheduleID(1);
        gameService.checkPostInputs(genericGame);

    }

    // testing checkPostInputs with invalid scheduleID
    @Test(expected = resourceException.class)
    public void testCheckPostInputsleaguemissing() throws resourceException {

        LocalDate startDate = LocalDate.of(2022, 12, 10);
        genericGame = new FantasyGame(null, 1, 1, 1, startDate, startDate.plusWeeks(1));
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

    @Test(expected = resourceException.class)
    public void testCheckInputsLeagueIDExcept() throws resourceException {
        FantasyGame badGame = new FantasyGame(
                1,
                null,
                1,
                1,
                2,
                genericdate1,
                genericdate2,
                null,
                0,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        gameService.checkInputs(badGame);
    }

    @Test(expected = resourceException.class)
    public void testCheckInputsStartDate() throws resourceException {
        LocalDate bad = LocalDate.of(2000, 1, 8);
        LocalDate good = LocalDate.of(2000, 2, 9);

        FantasyGame badGame = new FantasyGame(
                1,
                1,
                1,
                1,
                2,
                bad,
                good,
                null,
                0,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        gameService.checkInputs(badGame);
    }

    @Test(expected = resourceException.class)
    public void testCheckInputsDayOfWeek() throws resourceException {
        LocalDate bad = LocalDate.of(2022, 2, 9);

        FantasyGame badGame = new FantasyGame(
                1,
                1,
                1,
                1,
                2,
                genericdate1,
                bad,
                null,
                0,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        gameService.checkInputs(badGame);
    }

    @Test(expected = resourceException.class)
    public void testCheckInputsend() throws resourceException {
        LocalDate bad = LocalDate.of(2022, 1, 2);
        LocalDate ba2d = LocalDate.of(2022, 1, 10);

        FantasyGame badGame = new FantasyGame(
                1,
                1,
                1,
                1,
                2,
                null,
                bad,
                null,
                0,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        gameService.checkInputs(badGame);
    }

    @Test(expected = resourceException.class)
    public void testCheckInputsmessup() throws resourceException {
        LocalDate bad = LocalDate.of(2022, 1, 9);
        LocalDate badafter = LocalDate.of(2022, 1, 2);

        FantasyGame badGame = new FantasyGame(
                1,
                1,
                1,
                1,
                2,
                bad,
                badafter,
                null,
                0,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        gameService.checkInputs(badGame);
    }

    @Test(expected = resourceException.class)
    public void testCheckInputssixdays() throws resourceException {
        LocalDate bad = LocalDate.of(2022, 1, 2);
        LocalDate badafter = LocalDate.of(2022, 1, 16);

        FantasyGame badGame = new FantasyGame(
                1,
                1,
                1,
                1,
                2,
                bad,
                badafter,
                null,
                0,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        gameService.checkInputs(badGame);
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
