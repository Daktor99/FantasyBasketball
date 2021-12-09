package FantasyBasketball;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.repositories.userRepository;
import FantasyBasketball.services.fantasyTeamService;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class fantasyTeamTests {

    @MockBean
    private fantasyTeamService teamService;

    @MockBean
    private fantasyTeamRepository teamRepo;

    @MockBean
    private fantasyLeagueService fantasyLeagueService;

    @Before
    public void setUp() {
        //tearDown function
    }

    @After
    public void tearDown() {
        //tearDown function
    }

    @Test
    public void testGetTeamsByTemplate() {
        //
    }

    @Test
    public void checkLeagueFullShouldPass() {
        LocalDate league_start_date = LocalDate.of(2022, 12, 12);
        FantasyLeague fantasyLeague = new FantasyLeague(
                1,
                1,
                "fake league name",
                1,
                8,
                Boolean.FALSE,
                league_start_date,
                4
        );

        Integer leagueID = 1;
        Integer leagueClientID = 1;
        List<Integer> teamsInLeague = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        Mockito.when(teamRepo.findTeamsInLeague(leagueID, leagueClientID)).thenReturn(teamsInLeague);
        assertEquals(Boolean.FALSE, teamService.checkLeagueFull(fantasyLeague));
    }

//    @Test
//    public void checkLeagueFullShouldFail() {
//        LocalDate league_start_date = LocalDate.of(2022, 12, 12);
//        FantasyLeague fantasyLeague = new FantasyLeague(
//                1,
//                1,
//                "fake league name",
//                1,
//                4,
//                Boolean.FALSE,
//                league_start_date,
//                4
//        );
//
//        Integer leagueID = 1;
//        Integer leagueClientID = 1;
//        List<Integer> teamsInLeague = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
//
//        Mockito.when(teamRepo.findTeamsInLeague(leagueID, leagueClientID)).thenReturn(teamsInLeague);
//        assertEquals(Boolean.TRUE, teamService.checkLeagueFull(fantasyLeague));
//    }

    // Test that the team_id is correctly updated by teamUser method
    @Test
    public void testPostTeam() throws resourceException {

        // Initialize team before postTeam called
        FantasyTeam beforeTeam = new FantasyTeam(null,
                1,
                "TEST TEAM",
                1,
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        // Create newly inserted fantasyTeam
        FantasyTeam afterTeam = new FantasyTeam(12,
                1,
                "TEST TEAM",
                1,
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        // save the team
        Mockito.when(teamRepo.save(beforeTeam)).thenReturn(afterTeam);

        // assert that the team_id gets correctly updated
        assertEquals(teamService.postTeam(beforeTeam).get(0).getTeamID(), 12);
    }

    //Test that fantasyTeam gets correctly updated
    @Test
    public void testUpdateTeam() throws resourceNotFoundException, resourceException {

        //Initialize updated fantasyTeam
        FantasyTeam updatedTeam = new FantasyTeam(12,
                1,
                "TEST TEAM",
                1,
                2,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        // fantasyTeam exists
        Mockito.when(teamRepo.existsById(12)).thenReturn(true);

        // save the changes
        Mockito.when(teamRepo.save(updatedTeam)).thenReturn(updatedTeam);

        // assert that team gets correctly updated by checking that all data members are equal to the updatedTeam
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getTeamID(), updatedTeam.getTeamID());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getClientID(), updatedTeam.getClientID());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getLeagueID(), updatedTeam.getLeagueID());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getOwnerID(), updatedTeam.getOwnerID());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartPG(), updatedTeam.getStartPG());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartSG(), updatedTeam.getStartSG());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartSF(), updatedTeam.getStartSF());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartPF(), updatedTeam.getStartPF());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartC(), updatedTeam.getStartC());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getBench1(), updatedTeam.getBench1());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getBench2(), updatedTeam.getBench2());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getTeamWins(), updatedTeam.getTeamWins());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getTeamLosses(), updatedTeam.getTeamLosses());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getPointsScored(), updatedTeam.getPointsScored());
        assertEquals(teamService.updateTeam(updatedTeam).get(0).getPointsAgainst(), updatedTeam.getPointsAgainst());
    }

    //Make sure exception raised when the fantasyTeam does not exist
    @Test(expected = resourceNotFoundException.class)
    public void testExceptionUpdateTeam() throws resourceNotFoundException, resourceException {

        //Initialize updated fantasyTeam
        FantasyTeam updatedTeam = new FantasyTeam(12,
                1,
                "TEST TEAM",
                1,
                2,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        // teamID does not exist
        Mockito.when(teamRepo.existsById(12)).thenReturn(false);

        //call updateTeam method
        teamService.updateTeam(updatedTeam);
    }

    // Make sure that delete raises exception when fantasyTeam not found
    @Test(expected = resourceNotFoundException.class)
    public void testExceptionDeleteTeamById() throws resourceNotFoundException {

        // teamID does not exist
        Mockito.when(teamRepo.existsById(2)).thenReturn(false);

        //call deleteTeamById method
        teamService.deleteTeamById(2);
    }

    // Test that exception is raised when team_id provided that is not null
    @Test(expected = resourceException.class)
    public void testCheckPostInputs1() throws resourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(2,
                1,
                "TEST TEAM",
                1,
                2,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        teamService.checkPostInputs(testTeam);
    }

    // Test that exception is raised when team name provided is longer than 128 characters
    @Test(expected = resourceException.class)
    public void testCheckPostInputs2() throws resourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(2,
                1,
                "TEST TEAM TEST TEAM TEST TEAM " +
                        "TEST TEAM TEST TEAM TEST TEAM " +
                        "TEST TEAM TEST TEAM TEST TEAM " +
                        "TEST TEAM TEST TEAM TEST TEAM " +
                        "TEST TEAM TEST TEAM TEST TEAM",
                1,
                2,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        teamService.checkPostInputs(testTeam);
    }

    // Test that exception is raised when team name provided is blank
    @Test(expected = resourceException.class)
    public void testCheckPostInputs3() throws resourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(2,
                1,
                "     ",
                1,
                2,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        teamService.checkPostInputs(testTeam);
    }

    // Test that exception is raised when one of the data members that should not be null, is null
    @Test(expected = resourceException.class)
    public void testCheckPostInputs4() throws resourceException {

        // Initialize test team with league_id that is null
        FantasyTeam testTeam = new FantasyTeam(2,
                1,
                "TEST TEAM",
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        teamService.checkPostInputs(testTeam);
    }

    // Test that exception is raised when team_id provided is null
    @Test(expected = resourceException.class)
    public void testCheckPutInputs1() throws resourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(null,
                1,
                "TEST TEAM",
                1,
                2,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        teamService.checkPutInputs(testTeam);
    }

    // Test that exception is raised when team name provided is longer than 128 characters
    @Test(expected = resourceException.class)
    public void testCheckPutInputs2() throws resourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(2,
                1,
                "TEST TEAM TEST TEAM TEST TEAM " +
                        "TEST TEAM TEST TEAM TEST TEAM " +
                        "TEST TEAM TEST TEAM TEST TEAM " +
                        "TEST TEAM TEST TEAM TEST TEAM " +
                        "TEST TEAM TEST TEAM TEST TEAM",
                1,
                2,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        teamService.checkPutInputs(testTeam);
    }

    // Test that exception is raised when team name provided is blank
    @Test(expected = resourceException.class)
    public void testCheckPutInputs3() throws resourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(2,
                1,
                "     ",
                1,
                2,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                0,
                0);

        teamService.checkPutInputs(testTeam);
    }

    // Test that exception is raised when one of the data members that should not be null, is null
    @Test(expected = resourceException.class)
    public void testCheckPutInputs4() throws resourceException {

        // Initialize test team with null values
        FantasyTeam testTeam = new FantasyTeam();

        teamService.checkPutInputs(testTeam);
    }
}
