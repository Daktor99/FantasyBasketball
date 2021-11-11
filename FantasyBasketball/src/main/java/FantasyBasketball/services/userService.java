package FantasyBasketball.services;

import FantasyBasketball.exceptions.userException;
import FantasyBasketball.exceptions.userNotFoundException;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.userRepository;
import org.apache.commons.validator.routines.EmailValidator;
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
    public List<User> getByID(Integer userID) throws userNotFoundException {
        Optional<User> result = userRepo.findById(userID);
        if (result.isPresent()) {
            User userResult = result.get();
            return List.of(userResult);
        }
        else {
            throw new userNotFoundException("User not found by ID in DB, cannot update");
        }
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
    public List<User> updateUser(User user) throws userNotFoundException {
        if(userRepo.existsById(user.getUserID())) {
            User result = userRepo.save(user);
            return List.of(result);
        } else {
            throw new userNotFoundException("User not found by ID in DB, cannot update");
        }
    }

    // delete operation
    public void deleteUserById(Integer user_id) throws userNotFoundException {
        if(userRepo.existsById(user_id)) {
            userRepo.deleteById(user_id);
        } else {
            throw new userNotFoundException("User not found in DB, cannot delete");
        }
    }

    //uses email validator tool to check if email is valid
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
    private void checkInputs(User user) throws userException {

        try {
            if (!isValidEmail(user.getEmail())) {
                throw new userException("Email is invalid");
            } else if (checkIfInvalid(user.getEmail())) {
                throw new userException("Email must be between 1-128 characters.");
            } else if (checkIfInvalid(user.getUsername())) {
                throw new userException("Username must be between 1-128 characters.");
            } else if (checkIfInvalid(user.getFirstName())) {
                throw new userException("First name must be between 1-128 characters.");
            } else if (checkIfInvalid(user.getLastName())) {
                throw new userException("Last name must be between 1-128 characters.");
            }
        } catch (NullPointerException e) {
            throw new userException("User formatted incorrectly please provide the following:\n" +
                    "username, email, first_name, last_name");
        }
    }

    public void checkPostInputs(User user) throws userException {
        if (user.getUserID() != null) {
            throw new userException("Do not provide user_id.");
        }
        checkInputs(user);
    }

    public void checkPutInputs(User user) throws userException {
        if (user.getUserID() == null) {
            throw new userException("User formatted incorrectly please provide the following:\n" +
                    "user_id, username, email, first_name, last_name");
        }
        checkInputs(user);
    }
}
