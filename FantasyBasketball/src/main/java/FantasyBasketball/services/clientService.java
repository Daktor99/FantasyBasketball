package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.Client;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.clientRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class clientService {

    @Autowired
    clientRepository clientRepo;

    // find by ID
    public List<Client> getByID(Integer clientID) throws resourceNotFoundException {
        Optional<Client> result = clientRepo.findById(clientID);
        if (result.isPresent()) {
            Client clientResult = result.get();
            return List.of(clientResult);
        } else {
            throw new resourceNotFoundException("Client not found by ID in DB.");
        }
    }

    public List<Client> getClientsByTemplate(Integer client_id,
                                         String email,
                                         String company_name,
                                         String client_name) {
        return clientRepo.findByTemplate(client_id, email, company_name, client_name);
    }

    public List<Client> postClient(Client client) {
        client.setClientID(0);
        Client result = clientRepo.save(client);
        return List.of(result);
    }

    // uses email validator tool to check if email is valid
    public boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    // checking:    - length of string is <128 characters
    //              - that string is not blank ex "   "
    //              - string is initialize (not == null)
    // returns true if follows rules above, false otherwise
    public boolean checkIfInvalid(String string) {
        return string.length() > 128 || string.isBlank();
    }

    // check and sanitize inputs
    public void checkInputs(Client client) throws resourceException {

        try {
            if (!isValidEmail(client.getEmail())) {
                throw new resourceException("Email is invalid");
            } else if (checkIfInvalid(client.getEmail())) {
                throw new resourceException("Email must be between 1-128 characters.");
            } else if (checkIfInvalid(client.getCompany_name())) {
                throw new resourceException("Company Name must be between 1-128 characters.");
            } else if (checkIfInvalid(client.getClient_name())) {
                throw new resourceException("Client name must be between 1-128 characters.");
            }
        } catch (NullPointerException e) {
            throw new resourceException("Client formatted incorrectly please provide the following:\n" +
                    "email, company_name, client_name");
        }
    }
    // checking ClientID before Posting
    public void checkPostInputs(Client client) throws resourceException {
        if (client.getClientID() != null) {
            throw new resourceException("Do not provide client_id.");
        }
        checkInputs(client);
    }

    // put operation
    public List<Client> updateClient(Client client) throws resourceNotFoundException {
        if(clientRepo.existsById(client.getClientID())) {
            Client result = clientRepo.save(client);
            return List.of(result);
        } else {
            throw new resourceNotFoundException("Client not found by ID in DB, cannot update.");
        }
    }

    // checking ClientID before Putting
    public void checkPutInputs(Client client) throws resourceException {
        if (client.getClientID() == null) {
            throw new resourceException("User formatted incorrectly please provide the following:\n" +
                    "client_id, email, company_name, client_name");
        }
        checkInputs(client);
    }

    // delete operation
    public void deleteClientById(Integer client_id) throws resourceNotFoundException {
        if(clientRepo.existsById(client_id)) {
            clientRepo.deleteById(client_id);
        } else {
            throw new resourceNotFoundException("Client not found in DB, cannot delete");
        }
    }
}
