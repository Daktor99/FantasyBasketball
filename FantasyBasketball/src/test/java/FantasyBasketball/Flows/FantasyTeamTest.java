package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.ResourceException;
import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.repositories.fantasyTeamRepository;
import FantasyBasketball.repositories.fantasyLeagueRepository;
import FantasyBasketball.services.FantasyTeamService;
import FantasyBasketball.services.FantasyPlayerService;
import FantasyBasketball.services.FantasyLeagueService;
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

//@Import (FantasyTeamTest.Config.class)

@RunWith(SpringRunner.class)
@SpringBootTest
public class FantasyTeamTest {

    @Autowired
    FantasyTeamService teamService;

//    @TestConfiguration
//    static
//    class Config {
//        @Bean
//        FantasyTeamService mockTeamService() {
//            return Mockito.mock(FantasyTeamService.class);
//        }
//    }

    @MockBean
    FantasyPlayerService playerService;

//    @MockBean
//    FantasyTeamService mockteamService;

    @MockBean
    fantasyTeamRepository teamRepo;

    @MockBean
    fantasyLeagueRepository leagueRepo;

    @MockBean
    FantasyLeagueService fantasyLeagueService;

    @Test
    public void testGetByIDPass() throws ResourceNotFoundException {
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

    @Test(expected = ResourceNotFoundException.class)
    public void testGetByIDExcept() throws ResourceNotFoundException {
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
    public void testPostTeam() throws ResourceException {
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

    @Test(expected = ResourceException.class)
    public void testPostTeamLeagueNotExist() throws ResourceException {
        // Test that the team_id is correctly updated by teamUser method
        Integer leagueID = 1;
        // Initialize team before postTeam called
        FantasyTeam beforeTeam = new FantasyTeam(
                1,
                1,
                "TEST TEAM",
                1,
                1234,
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

        Optional<FantasyLeague> fantasyLeagueOptional = Optional.empty();
        teamService.postTeam(beforeTeam);
    }

    @Test(expected = ResourceException.class)
    public void testPostTeamExceptcheckleaguefull() throws ResourceException {
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
                0,
                Boolean.FALSE,
                fake_league_start_date,
                8
        );
        Optional<FantasyLeague> fantasyLeagueOptional = Optional.of(fakeFantasyLeague);
        Mockito.when(leagueRepo.findById(leagueID)).thenReturn(fantasyLeagueOptional);
        List<Integer> teamsInLeague = Arrays.asList(1, 2, 3, 4);
        Integer leagueClientID = 1;
        Mockito.when(teamRepo.findTeamsInLeague(leagueID, leagueClientID)).thenReturn(teamsInLeague);

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
        teamService.postTeam(beforeTeam);
    }

    // Test that exception is raised when team_id provided that is not null
    @Test(expected = ResourceException.class)
    public void testCheckPostInputs1() throws ResourceException {

        // Initialize test team with team_id that is not null
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

    // Test that exception is raised when team_id provided that is not null
    @Test
    public void testCheckPostInputswholethingworkperfect() throws ResourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(null,
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

        teamService.checkPostInputs(testTeam);
    }

    // Test that exception is raised when team name provided is longer than 128 characters
    @Test(expected = ResourceException.class)
    public void testCheckPostInputs2() throws ResourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(null,
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
    @Test(expected = ResourceException.class)
    public void testCheckPostInputs3() throws ResourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(null,
                1,
                "",
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
    @Test(expected = ResourceException.class)
    public void testCheckPostInputs4() throws ResourceException {

        // Initialize test team with league_id that is null
        FantasyTeam testTeam = new FantasyTeam(null,
                1,
                "mybad dude",
                null,
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
    @Test(expected = ResourceException.class)
    public void testCheckPostInputs6() throws ResourceException {

        // Initialize test team with league_id that is null
        FantasyTeam testTeam = new FantasyTeam(null,
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
    public void testPostMultipleTeams() throws ResourceException {
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

//     Test that the team_id is correctly updated by teamUser method
//    @Test
//    public void testUpdateTeam() throws ResourceNotFoundException, ResourceException {
//
//        //Initialize updated fantasyTeam
//        FantasyTeam team = new FantasyTeam(12,
//                1,
//                "TEST TEAM",
//                null,
//                null,
//                93,
//                94,
//                95,
//                96,
//                97,
//                98,
//                99,
//                0,
//                0,
//                0,
//                0);
//
//        //Initialize updated fantasyTeam
//        FantasyTeam dbteam = new FantasyTeam(12,
//                1,
//                "TEST TEAM",
//                null,
//                null,
//                93,
//                94,
//                95,
//                96,
//                97,
//                98,
//                99,
//                0,
//                0,
//                0,
//                0);
//
//        // fantasyTeam exists
//        Mockito.when(teamRepo.existsById(team.getTeamID())).thenReturn(Boolean.TRUE);
//
//        Mockito.when(teamRepo.findByTemplate(
//                team.getTeamID(),
//                team.getClientID(), null, null, null))
//                .thenReturn(List.of(dbteam));
//
//        FantasyTeam updatedTeam = teamService.updateValues(dbteam, team);
//
//        // save the changes
//        Mockito.when(teamRepo.save(updatedTeam)).thenReturn(updatedTeam);
//        assertEquals(updatedTeam, teamService.updateTeam(team).get(0));
//
//
////         assert that team gets correctly updated by checking that all data members are equal to the updatedTeam
//        assertEquals(teamService.updateTeam(updatedTeam).get(0).getTeamID(), updatedTeam.getTeamID());
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
//    }

    //Make sure exception raised when the fantasyTeam does not exist
    @Test(expected = ResourceNotFoundException.class)
    public void testExceptionUpdateTeam() throws ResourceNotFoundException, ResourceException {

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
    @Test(expected = ResourceException.class)
    public void testExceptionUpdateTeamTeamID() throws ResourceNotFoundException, ResourceException {

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
    @Test(expected = ResourceNotFoundException.class)
    public void testExceptionDeleteTeamById() throws ResourceNotFoundException {

        // teamID does not exist
        Mockito.when(teamRepo.existsById(2)).thenReturn(false);

        //call deleteTeamById method
        teamService.deleteTeamById(2);
    }

    // Make sure that delete raises exception when fantasyTeam not found
    @Test
    public void testDeleteTeamById() throws ResourceNotFoundException {

        // teamID does not exist
        Mockito.when(teamRepo.existsById(2)).thenReturn(true);

        //call deleteTeamById method
        teamService.deleteTeamById(2);
        Mockito.verify(teamRepo).deleteById(2);
    }

    // Test that exception is raised when team_id provided is null
    @Test
    public void testCheckPutInputs0perfect() throws ResourceException {

        // Initialize test team with team_id that is not null
        FantasyTeam testTeam = new FantasyTeam(1,
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

    // Test that exception is raised when team_id provided is null
    @Test(expected = ResourceException.class)
    public void testCheckPutInputs1() throws ResourceException {

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
    @Test(expected = ResourceException.class)
    public void testCheckPutInputs2() throws ResourceException {

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
    @Test(expected = ResourceException.class)
    public void testCheckPutInputs3() throws ResourceException {

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
    @Test(expected = ResourceException.class)
    public void testCheckPutInputs4() throws ResourceException {

        // Initialize test team with null values
        FantasyTeam testTeam = new FantasyTeam();

        teamService.checkPutInputs(testTeam);
    }

    @Test
    public void testgetPlayersOnTeam() {
        Integer teamID = 1;
        teamService.getPlayersOnTeam(teamID);
        Mockito.verify(playerService).getPlayerIDsByTeam(teamID);
    }

    @Test
    public void testgetPlayerPositionMap() {
        Integer teamID = 1;
        HashMap<Integer, String> playerPositionMap = new HashMap<>();
        FantasyPlayer player1 = new FantasyPlayer();
        FantasyPlayer my_player1 = player1.setNewPlayer(
                1,
                1,
                1,
                "Izzi",
                "Cho",
                "Nets",
                "C",
                1,
                69
        );
        FantasyPlayer player2 = new FantasyPlayer();
        FantasyPlayer my_player2 = player2.setNewPlayer(
                2,
                1,
                1,
                "Ema",
                "Dak",
                "Nets",
                "C",
                1,
                70
        );
        List<FantasyPlayer> playerList = Arrays.asList(my_player1, my_player2);
        Mockito.when(playerService.getPlayerByTemplate(
                null,
                null,
                teamID,
                null,
                null,
                null,
                null,
                null,
                null)).thenReturn(playerList);
        for (FantasyPlayer player: playerList) {
            playerPositionMap.put(player.getPlayerID(), player.getPosition());
        }
        assertEquals(playerPositionMap, teamService.getPlayerPositionMap(teamID));
    }

    @Test
    public void checkOwnerAndLeagueNotUpdated0() throws ResourceException {
        Integer ownerID = null;
        Integer leagueID = null;
        teamService.checkOwnerAndLeagueNotUpdated(ownerID, leagueID);
    }

    @Test(expected = ResourceException.class)
    public void checkOwnerAndLeagueNotUpdated1() throws ResourceException {
        Integer ownerID = null;
        Integer leagueID = 1;
        teamService.checkOwnerAndLeagueNotUpdated(ownerID, leagueID);
    }

    @Test(expected = ResourceException.class)
    public void checkOwnerAndLeagueNotUpdated2() throws ResourceException {
        Integer ownerID = 1;
        Integer leagueID = null;
        teamService.checkOwnerAndLeagueNotUpdated(ownerID, leagueID);
    }

    @Test
    public void testUpdatePG0() throws ResourceException {
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
        playerPositionMap.put(1, "G");
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 1;
        teamService.updatePG(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test()
    public void testUpdatePG1() throws ResourceException {
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

    @Test(expected = ResourceException.class)
    public void testUpdatePG2() throws ResourceException {
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

    @Test(expected = ResourceException.class)
    public void testUpdatePG3() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(99, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updatePG(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test
    public void testUpdateSG0() throws ResourceException {
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
        playerPositionMap.put(1, "G");
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 1;
        teamService.updateSG(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test()
    public void testUpdateSG1() throws ResourceException {
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
        teamService.updateSG(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateSG2() throws ResourceException {
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
        teamService.updateSG(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateSG3() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(99, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updateSG(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test
    public void testUpdateSF0() throws ResourceException {
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
        playerPositionMap.put(1, "F");
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 1;
        teamService.updateSF(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test()
    public void testUpdateSF1() throws ResourceException {
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
        teamService.updateSF(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateSF2() throws ResourceException {
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
        teamService.updateSF(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateSF3() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(99, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updateSF(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test
    public void testUpdatePF0() throws ResourceException {
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
        playerPositionMap.put(1, "F");
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 1;
        teamService.updatePF(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test()
    public void testUpdatePF1() throws ResourceException {
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
        teamService.updatePF(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdatePF2() throws ResourceException {
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
        teamService.updatePF(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdatePF3() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(99, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updatePF(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test
    public void testUpdateC0() throws ResourceException {
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
        playerPositionMap.put(1, "C");
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 1;
        teamService.updateC(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test()
    public void testUpdateC1() throws ResourceException {
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
        teamService.updateC(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateC2() throws ResourceException {
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
        teamService.updateC(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateC3() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(99, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updateC(currentTeam, playerPositionMap, teamPlayerList, playerID);
    }

    @Test
    public void testUpdateBench10() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 1;
        teamService.updateBench1(currentTeam, teamPlayerList, playerID);
    }

    @Test()
    public void testUpdateBench11() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = null;
        teamService.updateBench1(currentTeam, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateBench12() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(6, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updateBench1(currentTeam, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateBench13() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updateBench1(currentTeam, teamPlayerList, playerID);
    }

    @Test
    public void testUpdateBench20() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 1;
        teamService.updateBench2(currentTeam, teamPlayerList, playerID);
    }

    @Test()
    public void testUpdateBench21() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = null;
        teamService.updateBench2(currentTeam, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateBench22() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(6, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updateBench2(currentTeam, teamPlayerList, playerID);
    }

    @Test(expected = ResourceException.class)
    public void testUpdateBench23() throws ResourceException {
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
        List<Integer> teamPlayerList = Arrays.asList(1, 2, 3, 4, 5);
        Integer playerID = 99;
        teamService.updateBench2(currentTeam, teamPlayerList, playerID);
    }

    @Test
    public void checkDuplicatePlayers() {
        List<Integer> playerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        assertEquals(Boolean.FALSE, teamService.checkDuplicatePlayers(playerList));
    }

    @Test
    public void checkDuplicatePlayersDUPPED() {
        List<Integer> playerList = Arrays.asList(1, 1, 3, 4, 5, 6, 7);
        assertEquals(Boolean.TRUE, teamService.checkDuplicatePlayers(playerList));
    }

    @Test(expected = ResourceException.class)
    public void checkUpdateValues() throws ResourceException {
        // Initialize team before postTeam called
        FantasyTeam dbTeam = new FantasyTeam(
                12,
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
                0
        );

        // Create newly inserted fantasyTeam
        FantasyTeam updatedTeam = new FantasyTeam(
                12,
                1,
                "updated TEAM",
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
        Integer teamID = updatedTeam.getTeamID();
        String teamName = updatedTeam.getTeamName();
        HashMap<Integer, String> playerPositionMap = teamService.getPlayerPositionMap(teamID);
        List<Integer> teamPlayerIDs = teamService.getPlayersOnTeam(teamID);
        Integer start_pg = updatedTeam.getStartPG();
        Integer start_sg = updatedTeam.getStartSG();
        Integer start_sf = updatedTeam.getStartSF();
        Integer start_pf = updatedTeam.getStartPF();
        Integer start_c = updatedTeam.getStartC();
        Integer bench_1 = updatedTeam.getBench1();
        Integer bench_2 = updatedTeam.getBench2();

        teamService.updateValues(dbTeam, updatedTeam);
//
//        Mockito.verify(teamService).getPlayersOnTeam(teamID);
//        Mockito.verify(teamService).getPlayerPositionMap(teamID);
//
//        Mockito.verify(teamService).updatePG(dbTeam, playerPositionMap, teamService.getPlayersOnTeam(teamID), start_pg);
//        Mockito.verify(teamService, Mockito.times(1)).getPlayerPositionMap(teamID);
//        Mockito.verify(teamService).updatePG(dbTeam, playerPositionMap, teamPlayerIDs, start_pg);
//        Mockito.verify(teamService).updateSG(dbTeam, playerPositionMap, teamPlayerIDs, start_sg);
//        Mockito.verify(teamService).updateSF(dbTeam, playerPositionMap, teamPlayerIDs, start_sf);
//        Mockito.verify(teamService).updatePF(dbTeam, playerPositionMap, teamPlayerIDs, start_pf);
//        Mockito.verify(teamService).updateC(dbTeam, playerPositionMap, teamPlayerIDs, start_c);
//        Mockito.verify(teamService).updateBench1(dbTeam, teamPlayerIDs, bench_1);
//        Mockito.verify(teamService).updateBench2(dbTeam, teamPlayerIDs, bench_2);
//        List<Integer> playerList = Arrays.asList(dbTeam.getStartPG(),
//                dbTeam.getStartSG(),
//                dbTeam.getStartSF(),
//                dbTeam.getStartPF(),
//                dbTeam.getStartC(),
//                dbTeam.getBench1(),
//                dbTeam.getBench2());
//        Mockito.verify(teamService).checkDuplicatePlayers(playerList);
//        assertEquals(dbTeam, teamService.updateValues(dbTeam, updatedTeam));
    }

}
