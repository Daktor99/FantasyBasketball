package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import FantasyBasketball.services.fantasyTeamService;
import FantasyBasketball.services.fantasyPlayerService;
import FantasyBasketball.services.fantasyLeagueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class fantasyTeamTest {

    @Autowired
    fantasyTeamService teamService;

    @Autowired
    fantasyPlayerService playerService;

    @MockBean
    fantasyTeamRepository teamRepo;

    @MockBean
    fantasyLeagueRepository leagueRepo;

    @MockBean
    fantasyLeagueService fantasyLeagueService;

    @Test
    public void testGetByIDPass() throws resourceNotFoundException {
        Integer teamID = 1;
        FantasyTeam fantasyTeam = new FantasyTeam(
                1,
                1,
                "my team",
                1,
                1,
                99,
                100,
                101,
                102,
                103,
                104,
                105,
                0,
                0,
                0,
                0
        );
        Optional<FantasyTeam> fantasyTeamOptional = Optional.of(fantasyTeam);
        Mockito.when(teamRepo.findById(teamID)).thenReturn(fantasyTeamOptional);
        assertEquals(List.of(fantasyTeamOptional.get()), teamService.getByID(teamID));
    }

    @Test(expected = resourceNotFoundException.class)
    public void testGetByIDExcept() throws resourceNotFoundException {
        Integer teamID = 1;
        Mockito.when(teamRepo.findById(teamID)).thenReturn(Optional.empty());
        teamService.getByID(teamID);
    }

    @Test
    public void testGetTeamsByTemplate() {
        Integer team_id = 1;
        Integer client_id = 1;
        String team_name = "my team";
        Integer owner_id = 1;
        Integer league_id = 1;
        FantasyTeam fantasyTeam = new FantasyTeam(
                1,
                1,
                "my team",
                1,
                1,
                99,
                100,
                101,
                102,
                103,
                104,
                105,
                0,
                0,
                0,
                0
        );
        Mockito.when(teamRepo.findByTemplate(team_id, client_id, team_name, owner_id, league_id))
                .thenReturn(Arrays.asList(fantasyTeam));
        assertEquals(Arrays.asList(fantasyTeam),
                teamService.getTeamsByTemplate(
                        team_id,
                        client_id,
                        team_name,
                        owner_id,
                        league_id));
    }

    @Test
    public void checkLeagueFullFalse() {
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

    @Test
    public void checkLeagueFullTrue() {
        LocalDate league_start_date = LocalDate.of(2022, 12, 12);
        FantasyLeague fantasyLeague = new FantasyLeague(
                1,
                1,
                "fake league name",
                1,
                4,
                Boolean.FALSE,
                league_start_date,
                4
        );

        Integer leagueID = 1;
        Integer leagueClientID = 1;
        List<Integer> teamsInLeague = Arrays.asList(1, 2, 3, 4);
        Mockito.when(teamRepo.findTeamsInLeague(leagueID, leagueClientID)).thenReturn(teamsInLeague);
        assertEquals(Boolean.TRUE, teamService.checkLeagueFull(fantasyLeague));
    }

    @Test
    public void testPostTeam() throws resourceException {
        // Test that the team_id is correctly updated by teamUser method
        Integer leagueID = 1;
        // Initialize team before postTeam called
        FantasyTeam beforeTeam = new FantasyTeam(
                1,
                1,
                "TEST TEAM",
                1,
                1,
                70,
                71,
                72,
                73,
                74,
                75,
                76,
                0,
                0,
                0,
                0
        );

        LocalDate fake_league_start_date = LocalDate.of(2021, 12, 31);
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
        Optional<FantasyLeague> fantasyLeagueOptional = Optional.of(fakeFantasyLeague);
        Mockito.when(leagueRepo.findById(leagueID)).thenReturn(fantasyLeagueOptional);

//        Mockito.when(teamService.checkLeagueFull(fakeFantasyLeague)).thenReturn(Boolean.FALSE);

        // Create newly inserted fantasyTeam
        FantasyTeam afterTeam = new FantasyTeam(12,
                1,
                "TEST TEAM",
                1,
                1,
                70,
                71,
                72,
                73,
                74,
                75,
                76,
                0,
                0,
                0,
                0);

        // save the team
        Mockito.when(teamRepo.save(beforeTeam)).thenReturn(afterTeam);

        // assert that the team_id gets correctly updated
        assertEquals(List.of(afterTeam), teamService.postTeam(beforeTeam));
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

    @Test
    public void testPostMultipleTeams() throws resourceException {
        // Initialize team before postTeam called
        FantasyTeam beforeTeam = new FantasyTeam(
                1,
                1,
                "TEST TEAM",
                1,
                1,
                70,
                71,
                72,
                73,
                74,
                75,
                76,
                0,
                0,
                0,
                0
        );

        // Create newly inserted fantasyTeam
        FantasyTeam afterTeam = new FantasyTeam(
                12,
                1,
                "other TEAM",
                1,
                1,
                70,
                71,
                72,
                73,
                74,
                75,
                76,
                0,
                0,
                0,
                0);

        List<FantasyTeam> teamslist = Arrays.asList(beforeTeam, afterTeam);

        // save the team
        Mockito.when(teamRepo.saveAll(teamslist)).thenReturn(teamslist);

        // assert that the team_id gets correctly updated
        assertEquals(teamslist, teamService.postMultipleTeams(teamslist));
    }

    // Test that the team_id is correctly updated by teamUser method
    @Test
    public void testUpdateTeam() throws resourceNotFoundException, resourceException {

        //Initialize updated fantasyTeam
        FantasyTeam dbTeam = new FantasyTeam(12,
                1,
                "TEST TEAM",
                null,
                null,
                93,
                94,
                95,
                96,
                97,
                98,
                99,
                0,
                0,
                0,
                0);

        //Initialize updated fantasyTeam
        FantasyTeam updatedTeam = new FantasyTeam(12,
                1,
                "TEST TEAM",
                1,
                1,
                93,
                94,
                95,
                96,
                97,
                98,
                99,
                0,
                0,
                0,
                0);

        // fantasyTeam exists
        Mockito.when(teamRepo.existsById(dbTeam.getTeamID())).thenReturn(Boolean.TRUE);

        ArrayList list = new ArrayList();
        list.add(dbTeam);
        Mockito.when(teamRepo.findByTemplate(
                dbTeam.getTeamID(),
                dbTeam.getClientID(), null, null, null))
                .thenReturn(Arrays.asList(dbTeam));

        // save the changes
        Mockito.when(teamRepo.save(dbTeam)).thenReturn(dbTeam);

        // assert that team gets correctly updated by checking that all data members are equal to the updatedTeam
        assertEquals(teamService.updateTeam(dbTeam).get(0).getTeamID(), dbTeam.getTeamID());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getClientID(), updatedTeam.getClientID());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getLeagueID(), updatedTeam.getLeagueID());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getOwnerID(), updatedTeam.getOwnerID());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartPG(), updatedTeam.getStartPG());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartSG(), updatedTeam.getStartSG());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartSF(), updatedTeam.getStartSF());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartPF(), updatedTeam.getStartPF());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getStartC(), updatedTeam.getStartC());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getBench1(), updatedTeam.getBench1());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getBench2(), updatedTeam.getBench2());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getTeamWins(), updatedTeam.getTeamWins());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getTeamLosses(), updatedTeam.getTeamLosses());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getPointsScored(), updatedTeam.getPointsScored());
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getPointsAgainst(), updatedTeam.getPointsAgainst());
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

    //Make sure exception raised when the fantasyTeam does not exist
    @Test(expected = resourceException.class)
    public void testExceptionUpdateTeamTeamID() throws resourceNotFoundException, resourceException {

        //Initialize updated fantasyTeam
        FantasyTeam updatedTeam = new FantasyTeam(null,
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

//    @Test
//    public void testGetPlayersOnTeam() {
//        Integer teamID = 1;
//        List<Integer> list = Arrays.asList(1, 2, 3, 4);
//        Mockito.when(playerService.getPlayerIDsByTeam(teamID)).thenReturn(list);
//        assertEquals(list, teamService.getPlayersOnTeam(teamID));
//    }
//
//    @Test
//    public void testGetPlayerPositionMap() {
//        Integer teamID = 1;
//        FantasyPlayer player = new FantasyPlayer();
//        player.setNewPlayer(
//                null,
//                1,
//                1,
//                "Alex",
//                "Abrines",
//                "Oklahoma City Thunder",
//                "G",
//                1,
//                1
//        );
//        List<FantasyPlayer> playerlist = Arrays.asList(player);
//        HashMap<Integer, String> playerPositionMap = new HashMap<>();
//        Mockito.when(playerService.getPlayerByTemplate(
//                null,
//                null,
//                teamID,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        )).thenReturn(playerlist);
//        for (FantasyPlayer each: playerlist) {
//            playerPositionMap.put(each.getPlayerID(), each.getPosition());
//        }
//        assertEquals(playerPositionMap, teamService.getPlayerPositionMap(teamID));
//    }

    @Test(expected = resourceException.class)
    public void checkOwnerAndLeagueNotUpdated1() throws resourceException {
        Integer ownerID = null;
        Integer leagueID = 1;
        teamService.checkOwnerAndLeagueNotUpdated(ownerID, leagueID);
    }

    @Test(expected = resourceException.class)
    public void checkOwnerAndLeagueNotUpdated2() throws resourceException {
        Integer ownerID = 1;
        Integer leagueID = null;
        teamService.checkOwnerAndLeagueNotUpdated(ownerID, leagueID);
    }

    @Test(expected = resourceException.class)
    public void testUpdatePG1() throws resourceException {
        FantasyTeam currentTeam =  new FantasyTeam(
                1,
                1,
                "my team",
                1,
                1,
                99,
                100,
                101,
                102,
                103,
                104,
                105,
                0,
                0,
                0,
                0
        );
        HashMap<Integer, String> playerPositionMap =  new HashMap<>();
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = null;
        teamService.updatePG(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = resourceException.class)
    public void testUpdatePG2() throws resourceException {
        FantasyTeam currentTeam =  new FantasyTeam(
                1,
                1,
                "my team",
                1,
                1,
                99,
                100,
                101,
                102,
                103,
                104,
                105,
                0,
                0,
                0,
                0
        );
        HashMap<Integer, String> playerPositionMap =  new HashMap<>();
        List<Integer> teamPlayerList = Arrays.asList(6, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updatePG(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = resourceException.class)
    public void testUpdatePG3() throws resourceException {
        FantasyTeam currentTeam =  new FantasyTeam(
                1,
                1,
                "my team",
                1,
                1,
                99,
                100,
                101,
                102,
                103,
                104,
                105,
                0,
                0,
                0,
                0
        );
        HashMap<Integer, String> playerPositionMap =  new HashMap<>();
        playerPositionMap.put(99, "X");
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updatePG(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

}
