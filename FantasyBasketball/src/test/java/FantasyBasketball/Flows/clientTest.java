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
public class clientTest {

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
    public void sanity() {
        assertEquals(1, 1);
    }

}
