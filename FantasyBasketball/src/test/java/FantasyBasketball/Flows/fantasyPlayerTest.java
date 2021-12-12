package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.FantasyTeam;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.services.fantasyPlayerService;
import FantasyBasketball.services.fantasyTeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class fantasyPlayerTest {

    @Autowired
    fantasyPlayerService playerService;

    @MockBean
    fantasyPlayerRepository playerRepo;

    @MockBean
    fantasyTeamService teamService;

    @Test
    public void testFantasyPlayer() {

        // Initialize FantasyPlayer before postFantasyPlayer called
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer before_player = player.setNewPlayer(0,1,1,
                "Tom","Murdia", "nets","F",1,0);

        // Create newly inserted FantasyPlayer
        FantasyPlayer player1 = new FantasyPlayer();
        player1.setPlayerID(7835);
        player1.setTeamID(1);
        player1.setClientID(1);
        player1.setFirstName("Tom");
        player1.setLastName("Murdia");
        player1.setNbaTeam("nets");
        player1.setPosition("F");
        player1.setLeagueID(1);
        player1.setBallapiID(0);

        // save the FantasyPlayer
        Mockito.when(playerRepo.save(before_player)).thenReturn(player1);

        //assert that the FantasyPlayer_id gets correctly updated
        //List<FantasyPlayer> check_player=playerService.postFantasyPlayer(before_player);
        List<FantasyPlayer> check_player=playerService.postFantasyPlayer(before_player);
        assertEquals(player1.getPlayerID(), 7835);
        assertEquals(player1.getClientID(), 1);
        assertEquals(player1.getTeamID(), 1);
        assertEquals(player1.getFirstName(), "Tom");
        assertEquals(player1.getLastName(), "Murdia");
        assertEquals(player1.getNbaTeam(), "nets");
        assertEquals(player1.getPosition(), "F");
        assertEquals(player1.getLeagueID(), 1);
        assertEquals(player1.getBallapiID(), 0);
        assertEquals(player1.toString(),player1.toString());
        assertEquals(check_player.size(),0);
    }

    @Test
    public void testGetPlayerByTemplate() {
        Integer player_id = 1;
        Integer client_id = 1;
        Integer team_id = 1;
        String position  = "C";
        String first_name = "Izzi";
        String last_name = "Cho";
        String nba_team = "Nets";
        Integer league_id = 1;
        Integer ball_api_id = 69;

        // Initialize FantasyPlayer before postFantasyPlayer called
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer my_player = player.setNewPlayer(
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

        List<FantasyPlayer> fantasyPlayerList = Arrays.asList(my_player);
        Mockito.when(playerRepo.findByTemplate(
                player_id,
                client_id,
                team_id,
                league_id,
                ball_api_id,
                first_name,
                last_name,
                nba_team,
                position)).thenReturn(fantasyPlayerList);
        assertEquals(fantasyPlayerList, playerService.getPlayerByTemplate(
                player_id,
                client_id,
                team_id,
                position,
                first_name,
                last_name,
                nba_team,
                league_id,
                ball_api_id
        ));
    }

    @Test()
    public void testGetPlayerIDsByTeam() {

        // Initialize FantasyPlayer
        List teamId = new ArrayList<>();
        teamId.add(0);

        Mockito.when(playerRepo.getByTeamID(1)).thenReturn(teamId);

        assertEquals(playerService.getPlayerIDsByTeam(1).get(0), 0);
    }

    @Test
    public void testPostFantasyPlayers(){
        Integer player_id = 1;
        Integer client_id = 1;
        Integer team_id = 1;
        String position  = "C";
        String first_name = "Izzi";
        String last_name = "Cho";
        String nba_team = "Nets";
        Integer league_id = 1;
        Integer ball_api_id = 69;

        // Initialize FantasyPlayer before postFantasyPlayer called
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer my_player = player.setNewPlayer(
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
        List<FantasyPlayer> fantasyPlayerList = Arrays.asList(my_player);

        Mockito.when(playerRepo.findByTemplate(
                player_id,
                client_id,
                team_id,
                league_id,
                ball_api_id,
                null,
                null,
                null,
                null)).thenReturn(fantasyPlayerList);

        assertEquals(fantasyPlayerList.get(0).getPlayerID(), playerService.postFantasyPlayer(my_player).get(0).getPlayerID());
    }

    @Test
    public void testUpdateFantasyPlayer() throws resourceNotFoundException {
        Integer player_id = 1;
        Integer client_id = 1;
        Integer team_id = 1;
        Integer league_id = 1;

        // Initialize FantasyPlayer before postFantasyPlayer called
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer my_player = player.setNewPlayer(
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
        List<FantasyPlayer> fantasyPlayerList = Arrays.asList(my_player);

        Mockito.when(playerRepo.findByTemplate(
                player_id,
                client_id,
                null,
                league_id,
                null,
                null,
                null,
                null,
                null)).thenReturn(fantasyPlayerList);

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
        List<FantasyTeam> teamList = Arrays.asList(fantasyTeam);


        Mockito.when(teamService.getByID(my_player.getTeamID())).thenReturn(teamList);


        Mockito.when(playerRepo.findByTemplate(
                player_id,
                client_id,
                team_id,
                league_id,
                null,
                null,
                null,
                null,
                null)).thenReturn(fantasyPlayerList);

        assertEquals(fantasyPlayerList.get(0).getPlayerID(), playerService.updateFantasyPlayer(my_player).get(0).getPlayerID());
    }

    @Test (expected = resourceNotFoundException.class)
    public void testUpdateFantasyPlayerExceptTeam() throws resourceNotFoundException {
        Integer player_id = 1;
        Integer client_id = 1;
        Integer league_id = 1;

        // Initialize FantasyPlayer before postFantasyPlayer called
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer my_player = player.setNewPlayer(
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
        List<FantasyPlayer> fantasyPlayerList = Arrays.asList(my_player);

        Mockito.when(playerRepo.findByTemplate(
                player_id,
                client_id,
                null,
                league_id,
                null,
                null,
                null,
                null,
                null)).thenReturn(fantasyPlayerList);

        Mockito.when(teamService.getByID(my_player.getTeamID())).thenReturn(Collections.emptyList());

        playerService.updateFantasyPlayer(my_player);
    }

    @Test (expected = resourceNotFoundException.class)
    public void testUpdateFantasyPlayerExceptSize() throws resourceNotFoundException {
        Integer player_id = 1;
        Integer client_id = 1;
        Integer league_id = 1;

        // Initialize FantasyPlayer before postFantasyPlayer called
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer my_player = player.setNewPlayer(
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

        Mockito.when(playerRepo.findByTemplate(
                player_id,
                client_id,
                null,
                league_id,
                null,
                null,
                null,
                null,
                null)).thenReturn(Collections.emptyList());
        playerService.updateFantasyPlayer(my_player);
    }

    @Test (expected = resourceNotFoundException.class)
    public void testDeleteFantasyPlayerExcept() throws resourceNotFoundException {
        Integer player_id = 1;
        Integer client_id = 1;
        Integer league_id = 1;

        Mockito.when(playerRepo.findByTemplate(
                player_id,
                client_id,
                null,
                league_id,
                null,
                null,
                null,
                null,
                null)).thenReturn(Collections.emptyList());
        playerService.deleteFantasyPlayer(player_id, client_id, league_id);
    }

    @Test
    public void testGetAvailableFantasyPlayers() throws resourceException {
        // Initialize FantasyPlayer before postFantasyPlayer called
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer my_player = player.setNewPlayer(
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

        List<FantasyPlayer> fantasyPlayerList = Arrays.asList(my_player);

        Integer client_id = 1;
        String position  = "C";
        String first_name = "Izzi";
        String last_name = "Cho";
        String nba_team = "Nets";
        Integer league_id = 1;
        Mockito.when(playerRepo.getAvailablePlayers(
                league_id,
                client_id,
                first_name,
                last_name,
                nba_team,
                position)).thenReturn(fantasyPlayerList);

        assertEquals(fantasyPlayerList, playerService.getAvailablePlayers(
                league_id,
                client_id,
                first_name,
                last_name,
                nba_team,
                position
        ));
    }

    @Test (expected = resourceException.class)
    public void testGetAvailableFantasyPlayersExceptLeague() throws resourceException {
        Integer client_id = 1;
        String position  = "C";
        String first_name = "Izzi";
        String last_name = "Cho";
        String nba_team = "Nets";
        Integer league_id = null;
        playerService.getAvailablePlayers(
                league_id,
                client_id,
                first_name,
                last_name,
                nba_team,
                position
        );
    }

    @Test (expected = resourceException.class)
    public void testGetAvailableFantasyPlayersExceptClient() throws resourceException {
        Integer client_id = null;
        String position  = "C";
        String first_name = "Izzi";
        String last_name = "Cho";
        String nba_team = "Nets";
        Integer league_id = 1;
        playerService.getAvailablePlayers(
                league_id,
                client_id,
                first_name,
                last_name,
                nba_team,
                position
        );
    }

    @Test
    public void testGetUndraftedFantasyPlayers() throws resourceNotFoundException, resourceException {
        List<Integer> fantasyPlayerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        Integer client_id = 1;
        Integer league_id = 1;
        Mockito.when(playerRepo.getUndraftedPlayers(
                league_id,
                client_id)).thenReturn(fantasyPlayerList);

        assertEquals(fantasyPlayerList, playerService.getUndraftedPlayers(
                league_id,
                client_id
        ));
    }

    @Test (expected = resourceException.class)
    public void testGetUndraftedFantasyPlayersExceptLeague() throws resourceException {
        Integer client_id = 1;
        Integer league_id = null;
        playerService.getUndraftedPlayers(
                league_id,
                client_id
        );
    }

    @Test (expected = resourceException.class)
    public void testGetUndraftedFantasyPlayersExceptClient() throws resourceException {
        Integer client_id = null;
        Integer league_id = 1;
        playerService.getUndraftedPlayers(
                league_id,
                client_id
        );
    }

    @Test
    public void testGenerateNumber() {
        Integer ran=playerService.generateNumber(0, 1);
        assertTrue(ran>=0);
        assertTrue(ran<=1);
    }

}
