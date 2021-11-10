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
    private userRepository userRepo;

    // find by ID
    public List<User> getByID(Integer userID) {
        Optional<User> result = userRepo.findById(userID);
        if (result.isPresent()) {
            User userResult = result.get();
            return List.of(userResult);
        }
        return Collections.emptyList();
    }

    // get operation
    public List<User> getUsersByTemplate(Integer user_id,
                                         String email,
                                         String username,
                                         String first_name,
                                         String last_name) {
        return userRepo.findByTemplate(user_id, email, username, first_name, last_name);
    }

    // post operation
    public List<User> postUser(User user) {
        user.setUserID(0);
        User result = userRepo.save(user);
        return List.of(result);
    }

    // put operation
    public List<User> updateUser(User user) throws IllegalArgumentException {
        if (getByID(user.getUserID()).size() >= 1) {
            User result = userRepo.save(user);
            return List.of(result);
        } else {
            throw new IllegalArgumentException("Resource not found by ID in DB, cannot update");
        }
    }

    // delete operation
    public void deleteUserById(Integer user_id) {
        if (getByID(user_id).size() == 1) {
            userRepo.deleteById(user_id);
        } else {
            throw new IllegalArgumentException("Resource not found in DB, cannot delete");
        }
    }
}
