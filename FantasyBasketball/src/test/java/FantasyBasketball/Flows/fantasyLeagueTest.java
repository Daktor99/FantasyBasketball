package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.User;
import FantasyBasketball.models.Client;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import FantasyBasketball.repositories.userRepository;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.services.fantasyLeagueService;
import FantasyBasketball.services.clientService;
import FantasyBasketball.services.fantasyPlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class fantasyLeagueTest {

    @Autowired
    fantasyLeagueService leagueService;

    @Autowired
    clientService clientService;

    @MockBean
    clientService mockclientService;

    @MockBean
    fantasyPlayerService fantasyPlayerService;

    @MockBean
    fantasyLeagueRepository leagueRepo;

    @MockBean
    userRepository userRepo;

    @MockBean
    fantasyTeamRepository teamRepo;

    @MockBean
    fantasyGameRepository gameRepo;

    @Test
    public void testGetLeaguesByIDPass() throws resourceNotFoundException {
        Integer leagueID = 17;

        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague fakeFantasyLeague = new FantasyLeague(
                17,
                1,
                "fake league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );
        Optional<FantasyLeague> fakeFantasyLeagueOptional = Optional.of(fakeFantasyLeague);

        Mockito.when(leagueRepo.findById(leagueID)).thenReturn(fakeFantasyLeagueOptional);

        assertEquals(leagueService.getLeaguesByID(leagueID), List.of(fakeFantasyLeagueOptional.get()));
    }

    @Test(expected = resourceNotFoundException.class)
    public void testGetLeaguesByIDExcept() throws resourceNotFoundException {
        Integer leagueID = 17;
        Mockito.when(leagueRepo.findById(leagueID)).thenReturn(Optional.empty());

        // should return exception
        leagueService.getLeaguesByID(leagueID);
    }

    @Test
    public void testGetLeaguesByTemplate() {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        Integer league_id = 17;
        Integer client_id = 1;
        String league_name = "fake league";
        Integer admin_id = 4;
        Integer league_size = 4;
        Boolean draft_finished = Boolean.TRUE;
        LocalDate league_start_date = fake_league_start_date;
        Integer num_weeks = 8;
        // fake fantasyLeague to get back
        FantasyLeague fakeFantasyLeague = new FantasyLeague(
                league_id,
                client_id,
                league_name,
                admin_id,
                league_size,
                Boolean.TRUE,
                fake_league_start_date,
                num_weeks
        );

        Mockito.when(leagueRepo.findByTemplate(league_id,
                client_id,
                league_name,
                admin_id,
                league_size,
                Boolean.TRUE,
                league_start_date,
                num_weeks)).thenReturn(List.of(fakeFantasyLeague));

        assertEquals(leagueService.getLeaguesByTemplate(league_id,
                client_id,
                league_name,
                admin_id,
                league_size,
                Boolean.TRUE,
                league_start_date,
                num_weeks), List.of(fakeFantasyLeague));
    }

    @Test
    public void testCheckAdminTrue() {
        // Fake adminID that we're trying to see is valid user
        Integer adminID = 21;

        // Fake user that we're looking for / found
        User validUser = new User(21,
                1,
                "izzi@gmail.com",
                "izzi",
                "Isabella",
                "Cho");
        Optional<User> validUserOpt = Optional.of(validUser);

        // mock findById output
        Mockito.when(userRepo.findById(adminID)).thenReturn(validUserOpt);

        // assert that the admin_id got properly validated
        assertEquals(leagueService.checkAdmin(adminID), Boolean.TRUE);
    }

    @Test
    public void testCheckAdminFalse() {
        // Fake adminID that we're trying to see is valid user
        Integer adminID = 21;

        // Fake user that we're looking for / didn't find
        Optional<User> validUserOpt = Optional.empty();

        // mock findById output
        Mockito.when(userRepo.findById(adminID)).thenReturn(validUserOpt);

        // assert that the admin_id got properly identified as invalid
        assertEquals(leagueService.checkAdmin(adminID), Boolean.FALSE);
    }

    @Test
    public void testcheckDraftFinishedTrue() throws resourceException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);

        Integer leagueID = 17;

        // fake fantasyLeague to get back
        FantasyLeague fakeFantasyLeague = new FantasyLeague(
                17,
                1,
                "fake league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );
        Optional<FantasyLeague> fakeFantasyLeagueOptional = Optional.of(fakeFantasyLeague);

        Mockito.when(leagueRepo.findById(leagueID)).thenReturn(fakeFantasyLeagueOptional);

        assertEquals(leagueService.checkDraftFinished(leagueID), Boolean.TRUE);
    }

    @Test
    public void testcheckDraftFinishedFalse() throws resourceException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);

        Integer leagueID = 17;

        // fake fantasyLeague to get back
        FantasyLeague fakeFantasyLeague = new FantasyLeague(
                17,
                1,
                "fake league",
                4,
                4,
                Boolean.FALSE,
                fake_league_start_date,
                8
        );
        Optional<FantasyLeague> fakeFantasyLeagueOptional = Optional.of(fakeFantasyLeague);

        Mockito.when(leagueRepo.findById(leagueID)).thenReturn(fakeFantasyLeagueOptional);

        assertEquals(leagueService.checkDraftFinished(leagueID), Boolean.FALSE);
    }

    @Test
    public void testCheckDatesTrue() throws resourceException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);

        // assert that the valid dates were properly validated
        assertEquals(leagueService.checkDates(fake_league_start_date), Boolean.TRUE);
    }

    @Test(expected = resourceException.class)
    public void testCheckDatesExceptionStartInPast() throws resourceException {
        LocalDate league_start_date = LocalDate.of(2000, 12, 28);

        // checkDates should return exception
        leagueService.checkDates(league_start_date);
    }

    @Test
    public void testPostLeagues() throws IOException, resourceException {
        // fake start and end dates for fake fantasyLeague ctor
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);

        // fake fantasyLeague to post
        FantasyLeague fakeFantasyLeaguePost = new FantasyLeague();
        fakeFantasyLeaguePost.setClientID(1);
        fakeFantasyLeaguePost.setLeagueName("fake league");
        fakeFantasyLeaguePost.setAdminID(4);
        fakeFantasyLeaguePost.setDraftFinished(Boolean.TRUE);
        fakeFantasyLeaguePost.setLeagueSize(4);
        fakeFantasyLeaguePost.setLeagueStartDate(fake_league_start_date);

        // fake fantasyLeague to get back
        FantasyLeague fakeFantasyLeague = new FantasyLeague(
                17,
                1,
                "fake league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        // Fake adminID that we're trying to see is valid user
        Integer adminID = 4;

        // Fake user that we're looking for / found
        User validUser = new User(4,
                1,
                "patip@gmail.com",
                "pap2154",
                "Pati",
                "Przewoznik");
        Optional<User> validUserOpt = Optional.of(validUser);

        // mock findById output
        Mockito.when(userRepo.findById(adminID)).thenReturn(validUserOpt);

        // mock findById output
        Mockito.when(leagueRepo.save(fakeFantasyLeaguePost)).thenReturn(fakeFantasyLeague);

        // assert that the admin_id got properly validated
        List<FantasyLeague> listOfFakeFantasyLeague = List.of(fakeFantasyLeague);
        assertEquals(leagueService.postLeagues(fakeFantasyLeaguePost).get(0).getLeagueID(), listOfFakeFantasyLeague.get(0).getLeagueID());
    }

    @Test(expected = resourceException.class)
    public void testPostLeaguesAdminExcept() throws resourceException {
        // fake start and end dates for fake fantasyLeague ctor
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        // fake fantasyLeague to get back
        FantasyLeague fakeFantasyLeague = new FantasyLeague(
                17,
                1,
                "fake league",
                0,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.postLeagues(fakeFantasyLeague);
    }

    @Test(expected = resourceException.class)
    public void testPostLeaguesDatesExcept() throws resourceException {
        // fake start and end dates for fake fantasyLeague ctor
        LocalDate fake_league_start_date = LocalDate.of(2000, 12, 28);
        // fake fantasyLeague to get back
        FantasyLeague fakeFantasyLeague = new FantasyLeague(
                17,
                1,
                "fake league",
                0,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.postLeagues(fakeFantasyLeague);
    }

    @Test
    public void testUpdateLeaguesPass() throws resourceNotFoundException, resourceException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague referenceLeague = new FantasyLeague(
                17,
                1,
                "fake reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );
        Optional<FantasyLeague> referenceLeagueOpt = Optional.of(referenceLeague);
        Mockito.when(leagueRepo.findById(referenceLeague.getLeagueID())).thenReturn(referenceLeagueOpt);

        FantasyLeague newLeague = new FantasyLeague(
                17,
                1,
                "fake new league",
                null,
                null,
                null,
                null,
                null
        );
        Mockito.when(leagueRepo.save(referenceLeague)).thenReturn(newLeague);

        assertEquals(List.of(newLeague), leagueService.updateLeagues(newLeague));
    }

    @Test(expected = resourceNotFoundException.class)
    public void testUpdateLeaguesPresentExcept() throws resourceNotFoundException, resourceException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague referenceLeague = new FantasyLeague(
                17,
                1,
                "fake reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );
        Mockito.when(leagueRepo.findById(referenceLeague.getLeagueID())).thenReturn(Optional.empty());

        FantasyLeague newLeague = new FantasyLeague(
                17,
                1,
                "fake new league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.updateLeagues(newLeague);
    }

    @Test(expected = resourceException.class)
    public void testUpdateLeaguesEqualExcept() throws resourceNotFoundException, resourceException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague referenceLeague = new FantasyLeague(
                17,
                2,
                "fake reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );
        Optional<FantasyLeague> referenceLeagueOpt = Optional.of(referenceLeague);
        Mockito.when(leagueRepo.findById(referenceLeague.getLeagueID())).thenReturn(referenceLeagueOpt);

        FantasyLeague newLeague = new FantasyLeague(
                17,
                1,
                "fake new league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.updateLeagues(newLeague);
    }

    @Test(expected = resourceNotFoundException.class)
    public void testDeleteLeaguesExcept() throws resourceNotFoundException {
        Integer league_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.FALSE);
        leagueService.deleteLeagues(league_id);
    }

    @Test
    public void testCheckIfInvalidFalse() {
        String string = "hello";

        assertEquals(leagueService.checkIfInvalid(string), Boolean.FALSE);
    }

    @Test
    public void testCheckIfInvalidTrueLength() {
        String string = "hafbakjnvfsbjkdvaljhbdknavkdlsjhbdknklasvjhdnlsajk" +
                "jfdaslkhvdnlskajbknvsalvhjdnkfjhkfhvlkjhbnlkvdjkfjhnkldsan" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd" +
                "jafdklsahbjlkahdsfjkladsjgkfljdaskljgklasjkfjdklsjaklbjkjd";

        assertEquals(leagueService.checkIfInvalid(string), Boolean.TRUE);
    }

    @Test
    public void testCheckIfInvalidTrueEmpty() {
        String string = "";

        assertEquals(leagueService.checkIfInvalid(string), Boolean.TRUE);
    }

    @Test(expected = resourceException.class)
    public void testCheckInputsName() throws resourceException, resourceNotFoundException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague fantasyLeague = new FantasyLeague(
                17,
                1,
                "",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.checkInputs(fantasyLeague);
    }

    @Test(expected=resourceException.class)
    public void testCheckInputsNull() throws resourceException, resourceNotFoundException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague fantasyLeague = new FantasyLeague(
                17,
                1,
                null,
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );
        Mockito.when(fantasyLeague.getLeagueName()).thenThrow(NullPointerException.class);

        leagueService.checkInputs(fantasyLeague);
    }

    @Test(expected = resourceException.class)
    public void testCheckInputsSizeMin() throws resourceException, resourceNotFoundException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague badLeague = new FantasyLeague(
                17,
                1,
                "fake reference league",
                4,
                0,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.checkInputs(badLeague);
    }

    @Test(expected = resourceException.class)
    public void testCheckInputsSizeOdd() throws resourceException, resourceNotFoundException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);

        FantasyLeague badLeague = new FantasyLeague(
                17,
                1,
                "fake reference league",
                4,
                9,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.checkInputs(badLeague);
    }

    @Test(expected = resourceException.class)
    public void testCheckInputsWeeks() throws resourceException, resourceNotFoundException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);

        FantasyLeague badLeague = new FantasyLeague(
                17,
                1,
                "fake reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                0
        );

        leagueService.checkInputs(badLeague);
    }

    @Test
    public void testCheckInputsPass() throws resourceException, resourceNotFoundException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);

        FantasyLeague badLeague = new FantasyLeague(
                17,
                1,
                "fake reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.checkInputs(badLeague);
    }

    @Test(expected = resourceException.class)
    public void testCheckPostInputs() throws resourceException, resourceNotFoundException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague badLeague = new FantasyLeague(
                17,
                1,
                "fake reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.checkPostInputs(badLeague);
    }

    @Test(expected = resourceException.class)
    public void testCheckPutInputs() throws resourceException, resourceNotFoundException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague badLeague = new FantasyLeague(
                null,
                1,
                "fake reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        leagueService.checkPutInputs(badLeague);
    }

    @Test
    public void testGetTeamIDsPass() throws resourceException {
        Integer league_id = 2;
        Integer client_id = 1;

        List<Integer> teamList = Arrays.asList(1, 2, 3, 4, 5, 6);

        // mock output
        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(teamList);

        // assert that the admin_id got properly identified as invalid
        assertEquals(leagueService.getTeamIDs(league_id, client_id), teamList);
    }

    @Test(expected = resourceException.class)
    public void testGetTeamIDsExcept() throws resourceException {
        Integer league_id = 2;
        Integer client_id = 1;

        List<Integer> teamList = Arrays.asList(1, 2, 3);

        // mock output
        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(teamList);

        // should return exception
        leagueService.getTeamIDs(league_id, client_id);
    }

    @Test
    public void testPostGames() {
        LocalDate gametime1 = LocalDate.of(2022, 1, 1);
        LocalDate gametime2 = LocalDate.of(2022, 1, 8);
        List<Integer> game1 = Arrays.asList(1, 2);
        List<Integer> game2 = Arrays.asList(3, 4);
        List<Integer> game3 = Arrays.asList(3, 1);
        List<Integer> game4 = Arrays.asList(4, 2);
        List<List<Integer>> set1 = Arrays.asList(game1, game2);
        List<List<Integer>> set2 = Arrays.asList(game3, game4);

        Hashtable<LocalDate, List<List<Integer>>> schedule = new Hashtable() {
            {
                put(gametime1, set1);
                put(gametime2, set2);
            }
        };
        Integer league_id = 1;
        Integer client_id = 1;

        // making list of games to be saved
        List<FantasyGame> gameList = new ArrayList<>();
        Set<LocalDate> startDates = schedule.keySet();

        for(LocalDate startDate: startDates) {

            // loop through each individual matchup
            for(List<Integer> matchup: schedule.get(startDate)) {
                // initialize variables for insertion
                Integer home_team_id = matchup.get(0);
                Integer away_team_id = matchup.get(1);
                LocalDate endDate    = startDate.plusWeeks(1);

                // initialize FantasyGame instance & make sure scheduleID is not null (0 by default)
                FantasyGame game = new FantasyGame(league_id,
                        client_id,
                        home_team_id,
                        away_team_id,
                        startDate,
                        endDate);
                game.setScheduleID(0);

                // add this in list of games to be entered into DB
                gameList.add(game);
            } // end matchup looping

        } // end week looping

        Mockito.when(gameRepo.saveAll(gameList)).thenReturn(gameList);
        List<FantasyGame> expected = gameList;
        List<FantasyGame> returned = leagueService.postGames(schedule, league_id, client_id);
        for (Integer i=0; i<4; i++) {
            assertEquals(expected.get(i).getScheduleID(), returned.get(i).getScheduleID());
            assertEquals(expected.get(i).getLeagueID(), returned.get(i).getLeagueID());
            assertEquals(expected.get(i).getClientID(), returned.get(i).getClientID());
            assertEquals(expected.get(i).getHomeTeamID(), returned.get(i).getHomeTeamID());
            assertEquals(expected.get(i).getAwayTeamID(), returned.get(i).getAwayTeamID());
            assertEquals(expected.get(i).getGameStartDate(), returned.get(i).getGameStartDate());
            assertEquals(expected.get(i).getGameEndDate(), returned.get(i).getGameEndDate());
            assertEquals(expected.get(i).getWinner(), returned.get(i).getWinner());
            assertEquals(expected.get(i).getHomePoints(), returned.get(i).getHomePoints());
            assertEquals(expected.get(i).getAwayPoints(), returned.get(i).getAwayPoints());
            assertEquals(expected.get(i).getStartHomePG(), returned.get(i).getStartHomePG());
            assertEquals(expected.get(i).getStartHomeSG(), returned.get(i).getStartHomeSG());
            assertEquals(expected.get(i).getStartHomeSF(), returned.get(i).getStartHomeSF());
            assertEquals(expected.get(i).getStartHomePF(), returned.get(i).getStartHomePF());
            assertEquals(expected.get(i).getStartHomeC(), returned.get(i).getStartHomeC());
            assertEquals(expected.get(i).getHomeBench1(), returned.get(i).getHomeBench1());
            assertEquals(expected.get(i).getHomeBench2(), returned.get(i).getHomeBench2());
            assertEquals(expected.get(i).getStartAwayPG(), returned.get(i).getStartAwayPG());
            assertEquals(expected.get(i).getStartAwaySG(), returned.get(i).getStartAwaySG());
            assertEquals(expected.get(i).getStartAwaySF(), returned.get(i).getStartAwaySF());
            assertEquals(expected.get(i).getStartAwayPF(), returned.get(i).getStartAwayPF());
            assertEquals(expected.get(i).getStartAwayC(), returned.get(i).getStartAwayC());
            assertEquals(expected.get(i).getAwayBench1(), returned.get(i).getAwayBench1());
            assertEquals(expected.get(i).getAwayBench2(), returned.get(i).getAwayBench2());
        }
    }

    @Test(expected = resourceException.class)
    public void checkValidSize() throws resourceException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
        FantasyLeague badLeague = new FantasyLeague(
                17,
                1,
                "fake reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                0
        );
        Integer teamSize = 2;
        leagueService.checkValidSize(badLeague, teamSize);
    }

    @Test(expected = resourceException.class)
    public void checkDraftInputsLeagueExcept() throws resourceException, resourceNotFoundException {
        Integer league_id = null;
        Integer team_id = 1;
        Integer client_id = 1;
        leagueService.checkDraftInputs(league_id, team_id, client_id);
    }

    @Test(expected = resourceNotFoundException.class)
    public void checkDraftInputsExistsExcept() throws resourceException, resourceNotFoundException {
        Integer league_id = 0;
        Integer team_id = 1;
        Integer client_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.FALSE);
        leagueService.checkDraftInputs(league_id, team_id, client_id);
    }

    @Test(expected = resourceNotFoundException.class)
    public void checkDraftInputsTeamIDExcept() throws resourceException, resourceNotFoundException {
        Integer league_id = 1;
        Integer team_id = null;
        Integer client_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.TRUE);
        List<Integer> team_ids = Arrays.asList(1, 2, 3, 4, 5, 6);
        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(team_ids);
//        Mockito.when(teamRepo.existsById(team_id)).thenReturn(Boolean.FALSE);
        leagueService.checkDraftInputs(league_id, team_id, client_id);
    }

    @Test(expected = resourceNotFoundException.class)
    public void checkDraftInputsTeamExistsExcept() throws resourceException, resourceNotFoundException {
        Integer league_id = 1;
        Integer team_id = 1;
        Integer client_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.TRUE);
        List<Integer> team_ids = Arrays.asList(1, 2, 3, 4, 5, 6);
        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(team_ids);
        Mockito.when(teamRepo.existsById(team_id)).thenReturn(Boolean.FALSE);
        leagueService.checkDraftInputs(league_id, team_id, client_id);
    }

    @Test(expected = resourceNotFoundException.class)
    public void checkDraftInputsTeamInLeagueExcept() throws resourceException, resourceNotFoundException {
        Integer league_id = 1;
        Integer team_id = 1;
        Integer client_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.TRUE);
        List<Integer> team_ids = Arrays.asList(7, 2, 3, 4, 5, 6);
        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(team_ids);
        Mockito.when(teamRepo.existsById(team_id)).thenReturn(Boolean.FALSE);
        leagueService.checkDraftInputs(league_id, team_id, client_id);
    }

    @Test(expected = resourceException.class)
    public void checkPickPlayerPlayers() throws resourceException, resourceNotFoundException {
        FantasyPlayer player = new FantasyPlayer();
        player.setNewPlayer(
                null,
                1,
                1,
                "Alex",
                "Abrines",
                "Oklahoma City Thunder",
                "G",
                1,
                1
        );
        Mockito.when(fantasyPlayerService.getAvailablePlayers(
                1,
                57,
                null,
                null,
                null,
                null)
        ).thenReturn(Collections.emptyList());
        Integer client_id = 1;
        leagueService.pickPlayer(player, client_id);
    }

    @Test(expected = resourceException.class)
    public void checkPickPlayerContains() throws resourceException, resourceNotFoundException {
        FantasyPlayer player = new FantasyPlayer();
        player.setNewPlayer(
                7,
                1,
                1,
                "Alex",
                "Abrines",
                "Oklahoma City Thunder",
                "G",
                1,
                1
        );
        List<Integer> wrongcontains = Arrays.asList(0);
        Mockito.when(fantasyPlayerService.getUndraftedPlayers(1, 1)
        ).thenReturn(wrongcontains);
        Integer client_id = 1;
        leagueService.pickPlayer(player, client_id);
    }

    @Test
    public void checkRandomOrder() throws resourceNotFoundException {
        Integer league_id = 1;
        Integer client_id = 1;
        List<Integer> team_ids = Arrays.asList(2, 4, 6, 8, 10, 12);
        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(team_ids);
        Collections.shuffle(team_ids);
        List<Integer> order = new ArrayList<>();
        Client myClient = new Client(
                1,
                "izzi@gmail.com",
                "12083941938420183",
                "company",
                "client",
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1,
                30,
                2
        );
        List<Client> clientList = Arrays.asList(myClient);
        Mockito.when(mockclientService.getByID(client_id)).thenReturn(clientList);
        Client client = clientList.get(0);
        Integer team_size = client.getMax_team_size();
        for (int i = 0; i < team_size; i++) {
            order.addAll(team_ids);
        }
        assertEquals(order.size(), leagueService.randomOrder(league_id, client_id).size());
    }

    @Test(expected = resourceException.class)
    public void checkIfValidLeagueLeagueIDExcept() throws resourceNotFoundException, resourceException {
        Integer league_id = null;
        leagueService.checkIfValidLeague(league_id);
    }

    @Test(expected = resourceNotFoundException.class)
    public void checkIfValidLeagueLeagueExistsExcept() throws resourceNotFoundException, resourceException {
        Integer league_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.FALSE);
        leagueService.checkIfValidLeague(league_id);
    }

    @Test(expected = resourceException.class)
    public void checkIfScheduleGenerated() throws resourceException {
        Integer league_id = 1;
        List<FantasyGame> games = Collections.emptyList();
        Mockito.when(gameRepo.findGamesByLeagueID(league_id)).thenReturn(games);
        leagueService.checkIfScheduleGenerated(league_id);
    }

}
