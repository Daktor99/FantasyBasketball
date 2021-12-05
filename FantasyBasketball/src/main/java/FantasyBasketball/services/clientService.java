package FantasyBasketball.services;

import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.Client;
import FantasyBasketball.repositories.clientRepository;
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
            throw new resourceNotFoundException("Fantasy Game not found by ID in DB.");
        }
    }
}
