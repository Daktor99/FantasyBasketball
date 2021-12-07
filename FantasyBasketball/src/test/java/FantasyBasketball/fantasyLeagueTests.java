package FantasyBasketball;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.User;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.repositories.userRepository;
import FantasyBasketball.services.fantasyLeagueService;
import FantasyBasketball.services.userService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class fantasyLeagueTests {

//    @Autowired
//    fantasyLeagueService fantasyLeagueService;
//
//    @Autowired
//    FantasyBasketball.services.userService userService;
//
//    @Autowired
//    fantasyTeamRepository teamRepo;
//
//    @MockBean
//    fantasyLeagueRepository leagueRepo;
//
//    @MockBean
//    userRepository userRepo;
//
//    @Before
//    public void setUp() {
//        //tearDown function
//    }
//
//    @After
//    public void tearDown() {
//        //tearDown function
//    }
//
//    @Test
//    public void testGetLeaguesByTemplate() {
//        //
//    }
//
//    @Test
//    public void testCheckAdminTrue() {
//
//        // Fake adminID that we're trying to see is valid user
//        Integer adminID = 21;
//
//        // Fake user that we're looking for / found
//        User validUser = new User(21,
//                1,
//                "izzi@gmail.com",
//                "isabella",
//                "Isabella",
//                "Cho");
//        Optional<User> validUserOpt = Optional.of(validUser);
//
//        // mock findById output
//        Mockito.when(userRepo.findById(adminID)).thenReturn(validUserOpt);
//
//        // assert that the admin_id got properly validated
//        assertEquals(fantasyLeagueService.checkAdmin(adminID), Boolean.TRUE);
//    }
//
//    @Test
//    public void testCheckAdminFalse() {
//        // Fake adminID that we're trying to see is valid user
//        Integer adminID = 21;
//
//        // Fake user that we're looking for / didn't find
//        Optional<User> validUserOpt = Optional.empty();
//
//        // mock findById output
//        Mockito.when(userRepo.findById(adminID)).thenReturn(validUserOpt);
//
//        // assert that the admin_id got properly identified as invalid
//        assertEquals(fantasyLeagueService.checkAdmin(adminID), Boolean.FALSE);
//    }
//
//    @Test
//    public void testCheckDatesTrue() throws resourceException {
//        LocalDate league_start_date = LocalDate.of(2021, 12, 12);
//        LocalDate league_end_date = LocalDate.of(2022, 2, 12);
//
//        // assert that the valid dates were properly validated
//        assertEquals(fantasyLeagueService.checkDates(league_start_date, league_end_date), Boolean.TRUE);
//    }
//
//    @Test(expected = resourceException.class)
//    public void testCheckDatesException2Weeks() throws resourceException {
//        LocalDate league_start_date = LocalDate.of(2021, 12, 12);
//        LocalDate league_end_date = LocalDate.of(2021, 12, 13);
//
//        // checkDates should return exception
//        fantasyLeagueService.checkDates(league_start_date, league_end_date);
//    }
//
//    @Test(expected = resourceException.class)
//    public void testCheckDatesExceptionStartAfterEnd() throws resourceException {
//        LocalDate league_start_date = LocalDate.of(2022, 2, 13);
//        LocalDate league_end_date = LocalDate.of(2021, 12, 12);
//
//        // checkDates should return exception
//        fantasyLeagueService.checkDates(league_start_date, league_end_date);
//    }
//
//    @Test(expected = resourceException.class)
//    public void testCheckDatesExceptionStartInPast() throws resourceException {
//        LocalDate league_start_date = LocalDate.of(2020, 12, 12);
//        LocalDate league_end_date = LocalDate.of(2022, 2, 12);
//
//        // checkDates should return exception
//        fantasyLeagueService.checkDates(league_start_date, league_end_date);
//    }
//
//    @Test
//    public void testPostLeagues() throws IOException, resourceException {
//        // fake start and end dates for fake fantasyLeague ctor
//        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 11);
//        LocalDate fake_league_end_date = LocalDate.of(2022, 1, 30);
//
//        // fake fantasyLeague to post
//        FantasyLeague fakeFantasyLeaguePost = new FantasyLeague();
//        fakeFantasyLeaguePost.setClientID(1);
//        fakeFantasyLeaguePost.setLeagueName("fake league");
//        fakeFantasyLeaguePost.setAdminID(4);
//        fakeFantasyLeaguePost.setLeagueSize(4);
//        fakeFantasyLeaguePost.setLeagueStartDate(fake_league_start_date);
//        fakeFantasyLeaguePost.setLeagueEndDate(fake_league_end_date);
//
//        // fake fantasyLeague to get back
//        FantasyLeague fakeFantasyLeague = new FantasyLeague(
//                17,
//                1,
//                "fake league",
//                4,
//                4,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//
//        // Fake adminID that we're trying to see is valid user
//        Integer adminID = 4;
//
//        // Fake user that we're looking for / found
//        User validUser = new User(4,
//                1,
//                "patip@gmail.com",
//                "pap2154",
//                "Pati",
//                "Przewoznik");
//        Optional<User> validUserOpt = Optional.of(validUser);
//
//        // mock findById output
//        Mockito.when(userRepo.findById(adminID)).thenReturn(validUserOpt);
//
//        // mock findById output
//        Mockito.when(leagueRepo.save(fakeFantasyLeaguePost)).thenReturn(fakeFantasyLeague);
//
//        // assert that the admin_id got properly validated
//        List<FantasyLeague> listOfFakeFantasyLeague = List.of(fakeFantasyLeague);
//        assertEquals(fantasyLeagueService.postLeagues(fakeFantasyLeaguePost).get(0).getLeagueID(), listOfFakeFantasyLeague.get(0).getLeagueID());
//    }
//
//    @Test
//    public void testCheckEqualWithoutLeagueNamesTrue() {
//        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 11);
//        LocalDate fake_league_end_date = LocalDate.of(2022, 1, 30);
//
//        FantasyLeague referenceLeague = new FantasyLeague(
//                17,
//                1,
//                "fake reference league",
//                4,
//                4,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//        FantasyLeague comparisonLeague = new FantasyLeague(
//                17,
//                1,
//                "fake comparison league",
//                4,
//                4,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//
//        // assert that the admin_id got properly validated
//        assertEquals(fantasyLeagueService.checkEqualWithoutLeagueName(referenceLeague, comparisonLeague), Boolean.TRUE);
//    }
//
//    @Test
//    public void testCheckEqualWithoutLeagueNamesFalse() {
//        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 11);
//        LocalDate fake_league_end_date = LocalDate.of(2022, 1, 30);
//
//        FantasyLeague referenceLeague = new FantasyLeague(
//                17,
//                1,
//                "fake reference league",
//                4,
//                4,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//        FantasyLeague comparisonLeague = new FantasyLeague(
//                17,
//                1,
//                "fake comparison league",
//                4,
//                6,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//
//        // assert that the admin_id got properly validated
//        assertEquals(fantasyLeagueService.checkEqualWithoutLeagueName(referenceLeague, comparisonLeague), Boolean.FALSE);
//    }
//
//    @Test
//    public void testUpdateLeagues() {
//        //
//    }
//
//    @Test
//    public void testDeleteLeagues() {
//        //
//    }
//
//    @Test
//    public void testCheckIfInvalid() {
//        String string = "hello";
//
//        assertEquals(fantasyLeagueService.checkIfInvalid(string), Boolean.FALSE);
//    }
//
//    @Test(expected = resourceException.class)
//    public void testCheckInputsName() throws resourceException {
//        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 11);
//        LocalDate fake_league_end_date = LocalDate.of(2022, 1, 30);
//
//        FantasyLeague badLeague = new FantasyLeague(
//                17,
//                1,
//                "",
//                4,
//                4,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//
//        fantasyLeagueService.checkInputs(badLeague);
//    }
//
//    @Test(expected = resourceException.class)
//    public void testCheckInputsSize() throws resourceException {
//        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 11);
//        LocalDate fake_league_end_date = LocalDate.of(2022, 1, 30);
//
//        FantasyLeague badLeague = new FantasyLeague(
//                17,
//                1,
//                "bad league",
//                4,
//                -5,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//
//        fantasyLeagueService.checkInputs(badLeague);
//    }
//
//    @Test(expected = resourceException.class)
//    public void testCheckInputsFormat() throws resourceException {
//        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 11);
//        LocalDate fake_league_end_date = LocalDate.of(2022, 1, 30);
//
//        FantasyLeague badLeague = new FantasyLeague(
//                17,
//                1,
//                "bad league",
//                null,
//                null,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//
//        fantasyLeagueService.checkInputs(badLeague);
//    }
//
//    @Test(expected = resourceException.class)
//    public void testCheckPostInputs() throws resourceException {
//        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 11);
//        LocalDate fake_league_end_date = LocalDate.of(2022, 1, 30);
//
//        FantasyLeague badLeague = new FantasyLeague(
//                17,
//                1,
//                "bad league",
//                4,
//                4,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//
//        fantasyLeagueService.checkPostInputs(badLeague);
//    }
//
//    @Test(expected = resourceException.class)
//    public void testCheckPutInputs() throws resourceException {
//        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 11);
//        LocalDate fake_league_end_date = LocalDate.of(2022, 1, 30);
//
//        FantasyLeague badLeague = new FantasyLeague(
//                null,
//                1,
//                "bad league",
//                4,
//                4,
//                fake_league_start_date,
//                fake_league_end_date
//        );
//
//        fantasyLeagueService.checkPutInputs(badLeague);
//    }
//
//    @Test
//    public void testGetTeamIDs() throws resourceException {
////        Integer league_id = 2;
////        Integer client_id = 1;
////
////        List<Integer> teamList = new ArrayList<>();
////        teamList.add(1);
////        teamList.add(2);
////
////        // mock findById output
////        Mockito.when(teamRepo.findTeamsInLeague(league_id, client_id)).thenReturn(teamList);
////
////        // assert that the admin_id got properly identified as invalid
////        assertEquals(fantasyLeagueService.getTeamIDs(league_id, client_id), teamList);
//    }
//
//    @Test
//    public void testPostGames() {
//        //
//    }

}