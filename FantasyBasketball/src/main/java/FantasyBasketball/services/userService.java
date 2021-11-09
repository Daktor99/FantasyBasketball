package FantasyBasketball.services;

import FantasyBasketball.models.User;
import FantasyBasketball.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class userService {

    @Autowired
    private userRepository repo;

    public List<User> getByID(int userID) {
        Optional<User> result = repo.findById(userID);
        if (result.isPresent()) {
            User userResult = result.get();
            return List.of(userResult);
        }
        return Collections.emptyList();
    }
}
