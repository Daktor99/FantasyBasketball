package FantasyBasketball.utilities;

import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.repositories.playerDataRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FantasyLeagueUtilityTests {
    private final Integer PLAYER_ID = 1;
    private FantasyPlayer PLAYER_AWAY_TEST;

    private final FantasyLeagueUtility fantasyLeagueUtility = new FantasyLeagueUtility();
    @MockBean
    private playerDataRepository dataRepo;

    @Before
    public void setUp() {
        PLAYER_AWAY_TEST = new FantasyPlayer();
        PLAYER_AWAY_TEST.setPlayerID(PLAYER_ID);
    }

    @After
    public void tearDown() {
        //tearDown function
    }

    @Test
    public void test_successful_API_import() throws IOException {

        fantasyLeagueUtility.API_player_importation(dataRepo);
        List<FantasyPlayer> player_list = List.of(PLAYER_AWAY_TEST);

        Mockito.when(dataRepo.findByTemplate(1, null, null,
                null, null, null)).thenReturn(player_list);
        Assertions.assertEquals(PLAYER_ID, player_list.get(0).getPlayerID());
    }

}
