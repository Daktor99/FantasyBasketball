package FantasyBasketball;

import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.services.fantasyPlayerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class fantasyPlayerTests {

    @Autowired
    private fantasyPlayerService playerService;

    @MockBean
    private fantasyPlayerRepository playerRepo;

    @Before
    public void setUp() {
        //setUp function
    }

    @After
    public void tearDown() {
        //tearDown function
    }

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
        FantasyPlayer check_player=playerService.postFantasyPlayer(before_player).get(0);
        assertEquals(check_player.getPlayerID(), 7835);
        assertEquals(check_player.getClientID(), 1);
        assertEquals(check_player.getTeamID(), 1);
        assertEquals(check_player.getFirstName(), "Tom");
        assertEquals(check_player.getLastName(), "Murdia");
        assertEquals(check_player.getNbaTeam(), "nets");
        assertEquals(check_player.getPosition(), "F");
        assertEquals(check_player.getLeagueID(), 1);
        assertEquals(check_player.getBallapiID(), 0);
        assertEquals(check_player.toString(),player1.toString());
    }

    @Test
    public void testUpdateFantasyPlayer() throws resourceNotFoundException {

        // Initialize updated FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer updated_player = player.setNewPlayer(6468,1,1,
                "Tom","Murdia", "nets","F",1,0);

        // FantasyPlayer exists
        Mockito.when(playerRepo.existsById(6468)).thenReturn(true);

        // save the changes
        Mockito.when(playerRepo.save(updated_player)).thenReturn(updated_player);

        // assert that FantasyPlayer gets correctly updated by checking the FantasyPlayerID
        assertEquals(playerService.updateFantasyPlayer(updated_player).get(0).getPlayerID(), 6468);

    }

    @Test(expected = resourceNotFoundException.class)
    public void testExceptionUpdateFantasyPlayer() throws resourceNotFoundException {

        // Initialize updated FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer updated_player = player.setNewPlayer(7836,1,1,
                "Tom","Murdia", "nets","F",1,0);

        // FantasyPlayerID does not exist
        Mockito.when(playerRepo.existsById(7836)).thenReturn(false);

        // call updateFantasyPlayer method
        playerService.updateFantasyPlayer(updated_player);
    }

    @Test (expected = Test.None.class)
    public void testFindByID() throws resourceNotFoundException {

        // Initialize FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer new_player = player.setNewPlayer(0,1,1,
                "Tom","Murdia", "nets","F",1,0);

        Optional<FantasyPlayer> db = Optional.of(new_player);

        when(playerRepo.findById(0)).thenReturn(db);

        List<FantasyPlayer> result = playerService.getByID(0);
        assertEquals(result.get(0).getPlayerID(), 0);
    }


    @Test(expected = resourceNotFoundException.class)
    public void testGetIDFantasyPlayerException() throws resourceNotFoundException {

        // Initialize  FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer new_player = player.setNewPlayer(7836,1,1,
                "Tom","Murdia", "nets","F",1,0);

        Mockito.when(playerRepo.existsById(7836)).thenReturn(false);

        playerService.getByID(7836);
    }


    @Test
    public void testDeleteFantasyPlayerById() throws resourceNotFoundException {

        // Initialize  FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer new_player = player.setNewPlayer(6468,1,1,
                "Tom","Murdia", "nets","F",1,0);

        // FantasyPlayer exists
        Mockito.when(playerRepo.existsById(6468)).thenReturn(true);

        //call deleteFantasyPlayerById method
        playerService.deleteFantasyPlayerById(new_player.getPlayerID());

        Mockito.when(playerRepo.existsById(6468)).thenReturn(false);
    }

    // Make sure that delete raises exception when FantasyPlayer not found
    @Test(expected = resourceNotFoundException.class)
    public void testExceptionDeleteFantasyPlayerById() throws resourceNotFoundException {

        // FantasyPlayerID does not exist
        Mockito.when(playerRepo.existsById(10000)).thenReturn(false);

        //call deleteFantasyPlayerById method
        playerService.deleteFantasyPlayerById(10000);
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
        FantasyPlayer new_player = player.setNewPlayer(0, 1, 1,
                "Tom", "Murdia", "nets", "F", 1, 0);

        FantasyPlayer player1 = new FantasyPlayer();
        FantasyPlayer new_player1 = player.setNewPlayer(0, 1, 1,
                "Tom", "Murdia", "nets", "F", 1, 0);


        when(playerRepo.save(new_player)).thenReturn(new_player1);

        assertEquals(playerService.postFantasyPlayer(new_player).get(0).getPlayerID(), 0);
    }
    @Test
    public void testGetAvailableFantasyPlayers() {

        // Initialize FantasyPlayer
        FantasyPlayer player = new FantasyPlayer();
        FantasyPlayer new_player = player.setNewPlayer(0, 1, 1,
                "Tom", "Murdia", "nets", "F", 1, 0);

        List<FantasyPlayer> db = new LinkedList<>();
        db.add(new_player);
        Mockito.when(playerRepo.getAvailablePlayers(
                1, 1,1)).thenReturn(db);

        List<FantasyPlayer> result = playerService.getAvailablePlayers(1, 1,
                1);

        assertTrue(result.size() > 0);
    }

}
