package FantasyBasketball.Flows;

import FantasyBasketball.exceptions.ResourceException;
import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.Client;
import FantasyBasketball.repositories.*;
import FantasyBasketball.services.FantasyLeagueService;
import FantasyBasketball.services.ClientService;
import FantasyBasketball.services.FantasyPlayerService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientTest {

    @Autowired
    FantasyLeagueService leagueService;

    @Autowired
    ClientService clientService;

    @MockBean
    FantasyPlayerService fantasyPlayerService;

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
    public void testCheckGetByIdExists() throws ResourceNotFoundException {

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

    @Test(expected = ResourceNotFoundException.class)
    public void testCheckGetByIdNotExists() throws ResourceNotFoundException {

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
    public void testGetClientByGoogleIdExist() throws ResourceException {
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

    @Test(expected = ResourceException.class)
    public void testGetClientByGoogleIdNotExists() throws ResourceException {
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
    public void testCheckInputs() throws ResourceException {

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

    @Test(expected = ResourceException.class)
    public void testCheckInputsInvalidEmail() throws ResourceException {

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

    @Test(expected = ResourceException.class)
    public void testCheckInputsInvalidEmailLength() throws ResourceException {

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

    @Test(expected = ResourceException.class)
    public void testCheckInputsInvalidCompanyNameLength() throws ResourceException {

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

    @Test(expected = ResourceException.class)
    public void testCheckInputsInvalidClientLength() throws ResourceException {

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
    public void testCheckPostInputsValid() throws ResourceException {

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

    @Test(expected = ResourceException.class)
    public void testCheckPostInputsInvalid() throws ResourceException {

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
    public void testUpdateClient() throws ResourceNotFoundException {

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

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateClientNotFound() throws ResourceNotFoundException {
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
    public void testCheckPutInputs() throws ResourceException {

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

    @Test(expected = ResourceException.class)
    public void testCheckPutInputsInvalid() throws ResourceException {

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
    public void testDeleteClientById() throws ResourceNotFoundException {

        Integer client_id = 10;

        Mockito.when(clientRepo.existsById(client_id)).thenReturn(Boolean.TRUE);
        clientService.deleteClientById(client_id);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteClientByIdNotFound() throws ResourceNotFoundException {

        Integer client_id = 10;

        Mockito.when(clientRepo.existsById(client_id)).thenReturn(Boolean.FALSE);
        clientService.deleteClientById(client_id);

    }

}
