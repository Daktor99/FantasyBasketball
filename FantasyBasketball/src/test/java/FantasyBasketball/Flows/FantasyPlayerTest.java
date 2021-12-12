package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.ResourceException;
import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.services.FantasyPlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FantasyPlayerTest {

    @Autowired
    private FantasyPlayerService playerService;

    @MockBean
    private fantasyPlayerRepository playerRepo;

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
    public void testUpdateFantasyPlayer() throws ResourceNotFoundException {

        // Initialize updated FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer updated_player = player.setNewPlayer(6468,1,1,
                "Tom","Murdia", "nets","F",1,0);

        List<FantasyPlayer> player_list = new ArrayList<>();
        player_list.add(updated_player);

        // FantasyPlayer exists
        Mockito.when(playerRepo.existsById(6468)).thenReturn(true);

        Mockito.when(playerRepo.findByTemplate(6468,1,null,
                1,null,null,null,null,null)).thenReturn(player_list);

        //Mockito.when(playerRepo.save(updated_player)).thenReturn(updated_player);
        Mockito.when(playerRepo.findByTemplate(6468,1,1,
                1,null,null,null,null,null)).thenReturn(player_list);

        playerService.updateFantasyPlayer(updated_player);
        //Mockito.when(playerRepo.setPlayerTeam(6468,1,1,1))

        // assert that FantasyPlayer gets correctly updated by checking the FantasyPlayerID
        assertEquals(player_list.get(0).getPlayerID(), 6468);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testExceptionUpdateFantasyPlayer() throws ResourceNotFoundException {

        // Initialize updated FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer updated_player = player.setNewPlayer(7836, 1, 1,
                "Tom", "Murdia", "nets", "F", 1, 0);

        // FantasyPlayerID does not exist
        Mockito.when(playerRepo.existsById(7836)).thenReturn(false);

        // call updateFantasyPlayer method
        playerService.updateFantasyPlayer(updated_player);
    }

    @Test()
    public void testGetPlayerIDsByTeam() {

        // Initialize FantasyPlayer

        List<Integer> teamId = new ArrayList<>();
        teamId.add(0);

        Mockito.when(playerRepo.getByTeamID(1)).thenReturn(teamId);

        assertEquals(playerService.getPlayerIDsByTeam(1).get(0), 0);
    }


    @Test
    public void testDeleteFantasyPlayerById() throws ResourceNotFoundException {

        // Initialize  FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer updated_player = player.setNewPlayer(6468,1,1,
                "Tom","Murdia", "nets","F",1,0);

        List<FantasyPlayer> player_list = new ArrayList<>();
        player_list.add(updated_player);

        // FantasyPlayer exists

        Mockito.when(playerRepo.findByTemplate(6468,1,null,
                1,null,null,null,null,null)).thenReturn(player_list);

        //call deleteFantasyPlayerById method
        playerService.deleteFantasyPlayer(6468,1,1);

        Mockito.when(playerRepo.existsById(6468)).thenReturn(false);

    }

     //Make sure that delete raises exception when FantasyPlayer not found
    @Test(expected = ResourceNotFoundException.class)
    public void testExceptionDeleteFantasyPlayerById() throws ResourceNotFoundException {

        // FantasyPlayerID does not exist
        Mockito.when(playerRepo.existsById(10000)).thenReturn(false);

        //call deleteFantasyPlayerById method
        playerService.deleteFantasyPlayer(10000,1,1);
    }

    @Test
    public void testFindByTemplateFantasyPlayers() {

        // Initialize  FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer new_player = player.setNewPlayer(6468, 1, 1,
                "Tom", "Murdia", "nets", "F", 1, 0);

        List<FantasyPlayer> db = new LinkedList<>();
        db.add(new_player);
        Mockito.when(playerRepo.findByTemplate(
                6468, 1,
                1, null,
                null, null,
                null, null,
                null)).thenReturn(db);

        List<FantasyPlayer> result = playerService.getPlayerByTemplate(6468, 1,
                1, null,
                null, null,
                null, null,
                null);

        assertTrue(result.size() > 0);
    }

    @Test
    public void testPostFantasyPlayers(){
        // Initialize FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer new_player = player.setNewPlayer(6468, 1, 1,
                "Tom", "Murdia", "nets", "F", 1, 0);

        List<FantasyPlayer> player_list = new ArrayList<>();
        player_list.add(new_player);

        // FantasyPlayer exists

        Mockito.when(playerRepo.findByTemplate(6468,1,1,
                1,0,null,null,null,null)).thenReturn(player_list);

        assertEquals(playerService.postFantasyPlayer(new_player).get(0).getPlayerID(), 6468);
    }
    @Test
    public void testGetAvailableFantasyPlayers() throws ResourceException {

        // Initialize FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer new_player = player.setNewPlayer(0, 1, 1,
                "Tom", "Murdia", "nets", "F", 1, 0);

        List<FantasyPlayer> db = new LinkedList<>();
        db.add(new_player);
        Mockito.when(playerRepo.getAvailablePlayers(1,1,null,null,null,null)).thenReturn(db);

        List<FantasyPlayer> result = playerService.getAvailablePlayers(1,
                1,null,null,null,null);

        assertTrue(result.size() > 0);
    }

    @Test
    public void testGetUndraftedFantasyPlayers() throws ResourceNotFoundException, ResourceException {

        List<Integer> db = new LinkedList<>();
        db.add(0);
        Mockito.when(playerRepo.getUndraftedPlayers(1,1)).thenReturn(db);

        List<Integer> result = playerService.getUndraftedPlayers(1,1);

        assertTrue(result.size() > 0);
    }

    @Test
    public void testGenerateNumber() {
        Integer ran=playerService.generateNumber(0,1);
        assertTrue(ran>=0);
        assertTrue(ran<=1);
    }

    @Test
    public void testDraftFantasyPlayer() throws ResourceNotFoundException {

        // Initialize updated FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer updated_player = player.setNewPlayer(6468,1,1,
                "Tom","Murdia", "nets","F",1,0);

        List<FantasyPlayer> player_list = new ArrayList<>();
        player_list.add(updated_player);

        // FantasyPlayer exists
        Mockito.when(playerRepo.existsById(6468)).thenReturn(true);

        Mockito.when(playerRepo.findByTemplate(6468,1,null,
                1,null,null,null,null,null)).thenReturn(player_list);

        //Mockito.when(playerRepo.save(updated_player)).thenReturn(updated_player);
        Mockito.when(playerRepo.findByTemplate(6468,1,1,
                1,null,null,null,null,null)).thenReturn(player_list);

        playerService.draftFantasyPlayer(updated_player);
        //Mockito.when(playerRepo.setPlayerTeam(6468,1,1,1))

        // assert that FantasyPlayer gets correctly updated by checking the FantasyPlayerID
        assertEquals(player_list.get(0).getPlayerID(), 6468);

    }

    @Test
    public void testGetPlayerByTemplate() {

        // Initialize updated FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer updated_player = player.setNewPlayer(6468, 1, 1,
                "Tom", "Murdia", "nets", "F", 1, 0);

        List<FantasyPlayer> player_list = new ArrayList<>();
        player_list.add(updated_player);

        // FantasyPlayer exists
        Mockito.when(playerRepo.existsById(6468)).thenReturn(true);

        Mockito.when(playerRepo.findByTemplate(6468,1,null,
                1,null,null,null,null,null)).thenReturn(player_list);

        //Mockito.when(playerRepo.save(updated_player)).thenReturn(updated_player);
        Mockito.when(playerRepo.findByTemplate(6468,1,1,
                1,null,null,null,null,null)).thenReturn(player_list);

        playerService.getPlayerByTemplate(6468,1,1,
                null,null,null,null,1,0);
        //Mockito.when(playerRepo.setPlayerTeam(6468,1,1,1))

        // assert that FantasyPlayer gets correctly updated by checking the FantasyPlayerID
        assertEquals(player_list.get(0).getPlayerID(), 6468);

    }

}
