package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.FantasyGame;
import FantasyBasketball.models.FantasyLeague;
import FantasyBasketball.models.FantasyPlayer;
import FantasyBasketball.models.User;
import FantasyBasketball.models.Client;
import FantasyBasketball.repositories.*;
import FantasyBasketball.services.fantasyLeagueService;
import FantasyBasketball.services.clientService;
import FantasyBasketball.services.fantasyPlayerService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class clientTest {

    @Autowired
    fantasyLeagueService leagueService;

    @Autowired
    clientService clientService;

    @MockBean
    fantasyPlayerService fantasyPlayerService;

    @MockBean
    fantasyLeagueRepository leagueRepo;

    @MockBean
    userRepository userRepo;

    @MockBean
    clientRepository clientRepo;

    @MockBean
    fantasyTeamRepository teamRepo;

    @MockBean
    fantasyGameRepository gameRepo;

    @Test
    public void testCheckGetByIdExists() throws resourceNotFoundException {

        Integer client_id = 1;
        Client newClient = new Client(1,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);
        Optional<Client> optClient = Optional.of(newClient);
        Mockito.when(clientRepo.findById(client_id)).thenReturn(optClient);

        Client result = clientService.getByID(client_id).get(0);

        assertEquals(client_id, result.getClientID());
    }

    @Test(expected = resourceNotFoundException.class)
    public void testCheckGetByIdNotExists() throws resourceNotFoundException {
        Integer client_id = 1;
        Optional<Client> optClient = Optional.empty();
        Mockito.when(clientRepo.findById(client_id)).thenReturn(optClient);
        clientService.getByID(client_id);
    }

    @Test
    public void testGetClientsByTemplate() {
        Client client = new Client(1,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        Mockito.when(clientRepo.findByTemplate(null,
                "emanueldaka@gmail.com",
                null,
                null)).thenReturn(List.of(client));
        Client result = clientService.getClientsByTemplate(null,
                "emanueldaka@gmail.com",
                null,
                null).get(0);
        assertEquals(result.getClientID(), client.getClientID());
    }

    @Test
    public void testGetClientByGoogleIdExist() throws resourceException {
        Client client = new Client(1,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        Mockito.when(clientRepo.findClientByGoogle_id("12345678"))
                .thenReturn(List.of(client));
        Client result = clientService.getClientByGoogleId("12345678").get(0);
        assertEquals(client.getGoogle_id(), result.getGoogle_id());
        assertEquals(client.getClientID(), result.getClientID());
    }

    @Test(expected = resourceException.class)
    public void testGetClientByGoogleIdNotExists() throws resourceException {
        Client client1 = new Client(1,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);
        Client client2 = new Client(2,
                "pati@gmail.com",
                "12345678",
                "Pati Inc.",
                "Pati Trycia",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        Mockito.when(clientRepo.findClientByGoogle_id("12345678"))
                .thenReturn(List.of(client1, client2));
        clientService.getClientByGoogleId("12345678");
    }

    @Test
    public void testPostClient() {
        Client client1 = new Client(null,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        Client client2 = new Client(136,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        Mockito.when(clientRepo.save(client1)).thenReturn(client2);

        Assertions.assertNotEquals(client1.getClientID(), client2.getClientID());
        assertEquals(client1.getGoogle_id(), client2.getGoogle_id());
        assertEquals(client1.getCompany_name(), client2.getCompany_name());
    }

    @Test
    public void testIsValidEmail() {

        assertTrue(clientService.isValidEmail("emanueldaka@gmail.com"));
        assertFalse(clientService.isValidEmail("12"));

    }

    @Test
    public void testCheckIfInvalid() {

        assertTrue(clientService.checkIfInvalid(""));
        assertFalse(clientService.checkIfInvalid("String"));

    }

    @Test
    public void testCheckInputs() throws resourceException {

        Client client = new Client(null,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        clientService.checkInputs(client);

    }

    @Test(expected = resourceException.class)
    public void testCheckInputsInvalidEmail() throws resourceException {

        Client client = new Client(null,
                "7",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        clientService.checkInputs(client);

    }

    @Test(expected = resourceException.class)
    public void testCheckInputsInvalidEmailLength() throws resourceException {

        Client client = new Client(null,
                "emanueldakaemanueldakaemanueldakaemanueldakaemanueldakaemanueldakaemanueldakaemanueldaka" +
                        "emanueldakaemanueldakaemanueldakaemanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        clientService.checkInputs(client);

    }

    @Test(expected = resourceException.class)
    public void testCheckInputsInvalidCompanyNameLength() throws resourceException {

        Client client = new Client(null,
                "emanueldaka@gmail.com",
                "12345678",
                "",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        clientService.checkInputs(client);

    }

    @Test(expected = resourceException.class)
    public void testCheckInputsInvalidClientLength() throws resourceException {

        Client client = new Client(null,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        clientService.checkInputs(client);

    }

    @Test
    public void testCheckPostInputsValid() throws resourceException {

        Client client = new Client(null,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        clientService.checkPostInputs(client);
    }

    @Test(expected = resourceException.class)
    public void testCheckPostInputsInvalid() throws resourceException {

        Client client = new Client(13,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        clientService.checkPostInputs(client);
    }

    @Test
    public void testUpdateClient() throws resourceNotFoundException {

        Client client = new Client(13,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        Mockito.when(clientRepo.existsById(13)).thenReturn(Boolean.TRUE);
        Mockito.when(clientRepo.save(client)).thenReturn(client);

        Client result = clientService.updateClient(client).get(0);
        assertEquals(client.getClientID(), result.getClientID());
        assertEquals(client.getGoogle_id(), result.getGoogle_id());

    }

    @Test(expected = resourceNotFoundException.class)
    public void testUpdateClientNotFound() throws resourceNotFoundException {
        Client client = new Client(13,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);
        Mockito.when(clientRepo.existsById(13)).thenReturn(Boolean.FALSE);
        clientService.updateClient(client);
    }

    @Test
    public void testCheckPutInputs() throws resourceException {

        Client client = new Client(13,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        clientService.checkPutInputs(client);

    }

    @Test(expected = resourceException.class)
    public void testCheckPutInputsInvalid() throws resourceException {

        Client client = new Client(null,
                "emanueldaka@gmail.com",
                "12345678",
                "Emanuel Inc.",
                "Emanuel Daka",
                1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                4,
                4,
                4);

        clientService.checkPutInputs(client);

    }

    @Test
    public void testDeleteClientById() throws resourceNotFoundException {

        Integer client_id = 10;

        Mockito.when(clientRepo.existsById(client_id)).thenReturn(Boolean.TRUE);
        clientService.deleteClientById(client_id);

    }

    @Test(expected = resourceNotFoundException.class)
    public void testDeleteClientByIdNotFound() throws resourceNotFoundException {

        Integer client_id = 10;

        Mockito.when(clientRepo.existsById(client_id)).thenReturn(Boolean.FALSE);
        clientService.deleteClientById(client_id);

    }

}
