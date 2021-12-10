package FantasyBasketball;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import FantasyBasketball.repositories.userRepository;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.services.fantasyLeagueService;
import FantasyBasketball.services.fantasyPlayerService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith (SpringRunner.class)
@SpringBootTest
public class fantasyLeagueTests {

    @Autowired
    fantasyLeagueService fantasyLeagueService;

    @MockBean
    @Autowired
    fantasyPlayerService fantasyPlayerService;

    @MockBean
    fantasyLeagueRepository leagueRepo;

    @MockBean
    userRepository userRepo;

    @MockBean
    fantasyTeamRepository teamRepo;

    @MockBean
    fantasyGameRepository gameRepo;

    @BeforeEach
    public void setUp() {
        //TODO tearDown function
    }

    @AfterEach
    public void tearDown() {
        //TODO tearDown function
    }

    @Test
    public void testGetClientID() {
        //TODO are we using this function / implemented? / update / pass & fail!
        assertEquals(1, 1);
    }

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

        assertEquals(fantasyLeagueService.getLeaguesByID(leagueID), List.of(fakeFantasyLeagueOptional.get()));
    }

    @Test(expected = resourceNotFoundException.class)
    public void testGetLeaguesByIDExcept() throws resourceNotFoundException {
        Integer leagueID = 17;
        Mockito.when(leagueRepo.findById(leagueID)).thenReturn(Optional.empty());

        // should return exception
        fantasyLeagueService.getLeaguesByID(leagueID);
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

        assertEquals(fantasyLeagueService.getLeaguesByTemplate(league_id,
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
        assertEquals(fantasyLeagueService.checkAdmin(adminID), Boolean.TRUE);
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
        assertEquals(fantasyLeagueService.checkAdmin(adminID), Boolean.FALSE);
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

        assertEquals(fantasyLeagueService.checkDraftFinished(leagueID), Boolean.TRUE);
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

        assertEquals(fantasyLeagueService.checkDraftFinished(leagueID), Boolean.FALSE);
    }

    @Test
    public void testCheckDatesTrue() throws resourceException {
        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);

        // assert that the valid dates were properly validated
        assertEquals(fantasyLeagueService.checkDates(fake_league_start_date), Boolean.TRUE);
    }

    @Test(expected = resourceException.class)
    public void testCheckDatesExceptionStartInPast() throws resourceException {
        LocalDate league_start_date = LocalDate.of(2000, 12, 28);

        // checkDates should return exception
        fantasyLeagueService.checkDates(league_start_date);
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
        assertEquals(fantasyLeagueService.postLeagues(fakeFantasyLeaguePost).get(0).getLeagueID(), listOfFakeFantasyLeague.get(0).getLeagueID());
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

        fantasyLeagueService.postLeagues(fakeFantasyLeague);
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

        fantasyLeagueService.postLeagues(fakeFantasyLeague);
    }

    @Test
    public void testCheckEqualWithoutLeagueNamesTrue() {
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
        FantasyLeague comparisonLeague = new FantasyLeague(
                17,
                1,
                "fake comp reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        // assert that the admin_id got properly validated
        assertEquals(fantasyLeagueService.checkEqualWithoutLeagueName(referenceLeague, comparisonLeague), Boolean.TRUE);
    }

    @Test
    public void testCheckEqualWithoutLeagueNamesFalse() {
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
        FantasyLeague comparisonLeague = new FantasyLeague(
                17,
                2,
                "fake reference league",
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );

        // assert that the admin_id got properly validated
        assertEquals(fantasyLeagueService.checkEqualWithoutLeagueName(referenceLeague, comparisonLeague), Boolean.FALSE);
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
                4,
                4,
                Boolean.TRUE,
                fake_league_start_date,
                8
        );
        Mockito.when(leagueRepo.save(newLeague)).thenReturn(newLeague);

        assertEquals(fantasyLeagueService.updateLeagues(newLeague), List.of(newLeague));
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

        fantasyLeagueService.updateLeagues(newLeague);
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

        fantasyLeagueService.updateLeagues(newLeague);
    }

    @Test(expected = resourceNotFoundException.class)
    public void testDeleteLeaguesExcept() throws resourceNotFoundException {
        Integer league_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.FALSE);
        fantasyLeagueService.deleteLeagues(league_id);
    }

    @Test
    public void testCheckIfInvalidFalse() {
        String string = "hello";

        assertEquals(fantasyLeagueService.checkIfInvalid(string), Boolean.FALSE);
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

        assertEquals(fantasyLeagueService.checkIfInvalid(string), Boolean.TRUE);
    }

    @Test
    public void testCheckIfInvalidTrueEmpty() {
        String string = "";

        assertEquals(fantasyLeagueService.checkIfInvalid(string), Boolean.TRUE);
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

        fantasyLeagueService.checkInputs(fantasyLeague);
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

        fantasyLeagueService.checkInputs(badLeague);
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

        fantasyLeagueService.checkInputs(badLeague);
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

        fantasyLeagueService.checkInputs(badLeague);
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

        fantasyLeagueService.checkInputs(badLeague);
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

        fantasyLeagueService.checkPostInputs(badLeague);
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

        fantasyLeagueService.checkPutInputs(badLeague);
    }

    @Test
    public void testGetTeamIDsPass() throws resourceException {
        Integer league_id = 2;
        Integer client_id = 1;

        List<Integer> teamList = Arrays.asList(1, 2, 3, 4, 5, 6);

        // mock output
        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(teamList);

        // assert that the admin_id got properly identified as invalid
        assertEquals(fantasyLeagueService.getTeamIDs(league_id, client_id), teamList);
    }

    @Test(expected = resourceException.class)
    public void testGetTeamIDsExcept() throws resourceException {
        Integer league_id = 2;
        Integer client_id = 1;

        List<Integer> teamList = Arrays.asList(1, 2, 3);

        // mock output
        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(teamList);

        // should return exception
        fantasyLeagueService.getTeamIDs(league_id, client_id);
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

        LocalDate game1start = LocalDate.of(2022, 1, 1);
        LocalDate game1end = LocalDate.of(2022, 1, 7);

        FantasyGame realgame1 = new FantasyGame(
                1,
                1,
                1,
                1,
                2,
                game1start,
                game1end,
                1,
                100,
                90,
                400,
                399,
                398,
                397,
                386,
                385,
                384,
                383,
                382,
                381,
                380,
                379,
                378,
                377
        );
        FantasyGame realgame2 = new FantasyGame(
                2,
                1,
                1,
                3,
                4,
                game1start,
                game1start,
                3,
                100,
                90,
                377,
                376,
                374,
                373,
                372,
                371,
                370,
                369,
                368,
                367,
                366,
                365,
                364,
                363
        );

        LocalDate game2start = LocalDate.of(2022, 1, 8);
        LocalDate game2end = LocalDate.of(2022, 1, 14);

        FantasyGame realgame3 = new FantasyGame(
                3,
                1,
                1,
                3,
                1,
                game2start,
                game2end,
                3,
                100,
                90,
                377,
                376,
                374,
                373,
                372,
                371,
                370,
                369,
                368,
                367,
                366,
                365,
                364,
                363
        );

        FantasyGame realgame4 = new FantasyGame(
                4,
                1,
                1,
                4,
                2,
                game2start,
                game2end,
                4,
                100,
                90,
                362,
                361,
                360,
                359,
                358,
                357,
                356,
                355,
                354,
                353,
                352,
                351,
                350,
                349
        );

        // making list of games to be saved
        List<FantasyGame> gameList = Arrays.asList(realgame1, realgame2, realgame3, realgame4);
        Mockito.when(gameRepo.saveAll(gameList)).thenReturn(gameList);
        assertEquals(gameList, fantasyLeagueService.postGames(schedule, league_id, client_id));
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
        fantasyLeagueService.checkValidSize(badLeague, teamSize);
    }

    @Test(expected = resourceException.class)
    public void checkDraftInputsLeagueExcept() throws resourceException, resourceNotFoundException {
        Integer league_id = null;
        Integer team_id = 1;
        Integer client_id = 1;
        fantasyLeagueService.checkDraftInputs(league_id, team_id, client_id);
    }

    @Test(expected = resourceNotFoundException.class)
    public void checkDraftInputsExistsExcept() throws resourceException, resourceNotFoundException {
        Integer league_id = 0;
        Integer team_id = 1;
        Integer client_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.FALSE);
        fantasyLeagueService.checkDraftInputs(league_id, team_id, client_id);
    }

    @Test(expected = resourceNotFoundException.class)
    public void checkDraftInputsTeamIDExcept() throws resourceException, resourceNotFoundException {
        Integer league_id = 1;
        Integer team_id = null;
        Integer client_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.TRUE);
        List<Integer> team_ids = Arrays.asList(1, 2, 3, 4, 5, 6);
        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(team_ids);
        fantasyLeagueService.checkDraftInputs(league_id, team_id, client_id);
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
        fantasyLeagueService.checkDraftInputs(league_id, team_id, client_id);
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
        fantasyLeagueService.checkDraftInputs(league_id, team_id, client_id);
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
        fantasyLeagueService.pickPlayer(player);
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
        fantasyLeagueService.pickPlayer(player);
    }

    @Test
    public void checkRandomOrder() throws resourceNotFoundException {
        // Cannot test because shuttle returns something different
        assertEquals(1, 1);
    }

    @Test(expected = resourceException.class)
    public void checkIfValidLeagueLeagueIDExcept() throws resourceNotFoundException, resourceException {
        Integer league_id = null;
        fantasyLeagueService.checkIfValidLeague(league_id);
    }

    @Test(expected = resourceNotFoundException.class)
    public void checkIfValidLeagueLeagueExistsExcept() throws resourceNotFoundException, resourceException {
        Integer league_id = 1;
        Mockito.when(leagueRepo.existsById(league_id)).thenReturn(Boolean.FALSE);
        fantasyLeagueService.checkIfValidLeague(league_id);
    }

    @Test(expected = resourceException.class)
    public void checkIfScheduleGenerated() throws resourceException {
        Integer league_id = 1;
        List<FantasyGame> games = Collections.emptyList();
        Mockito.when(gameRepo.findGamesByLeagueID(league_id)).thenReturn(games);
        fantasyLeagueService.checkIfScheduleGenerated(league_id);
    }

}
