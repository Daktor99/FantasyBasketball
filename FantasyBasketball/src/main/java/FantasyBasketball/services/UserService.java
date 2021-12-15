package FantasyBasketball.services;

import FantasyBasketball.exceptions.ResourceException;
import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.userRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private userRepository userRepo;

    // find by ID
    public List<User> getByID(Integer userID) throws ResourceNotFoundException {
        Optional<User> result = userRepo.findById(userID);
        if (result.isPresent()) {
            User userResult = result.get();
            return List.of(userResult);
        } else {
            throw new ResourceNotFoundException("User not found by ID in DB.");
        }
    }

    // get operation
    public List<User> getUsersByTemplate(Integer user_id,
                                         Integer client_id,
                                         String email,
                                         String username,
                                         String first_name,
                                         String last_name) {
        return userRepo.findByTemplate(user_id, client_id, email, username, first_name, last_name);
    }

    // post operation
    public List<User> postUser(User user) {

        user.setUserID(0);
        User result = userRepo.save(user);
        return List.of(result);
    }

    // put operation
    public List<User> updateUser(User user) throws ResourceNotFoundException {
        if(userRepo.existsById(user.getUserID())) {
            User newuser = getByID(user.getUserID()).get(0);
            user.setEmail(newuser.getEmail());
            User result = userRepo.save(user);
            return List.of(result);
        } else {
            throw new ResourceNotFoundException("User not found by ID in DB, cannot update.");
        }
    }

    // delete operation
    public void deleteUserById(Integer user_id) throws ResourceNotFoundException {
        if(userRepo.existsById(user_id)) {
            userRepo.deleteById(user_id);
        } else {
            throw new ResourceNotFoundException("User not found in DB, cannot delete");
        }
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
    public void checkInputs(User user) throws ResourceException {

        try {
            if (!isValidEmail(user.getEmail())) {
                throw new ResourceException("Email is invalid");
            } else if (checkIfInvalid(user.getEmail())) {
                throw new ResourceException("Email must be between 1-128 characters.");
            } else if (checkIfInvalid(user.getUsername())) {
                throw new ResourceException("Username must be between 1-128 characters.");
            } else if (checkIfInvalid(user.getFirstName())) {
                throw new ResourceException("First name must be between 1-128 characters.");
            } else if (checkIfInvalid(user.getLastName())) {
                throw new ResourceException("Last name must be between 1-128 characters.");
            }

        } catch (NullPointerException e) {
            throw new ResourceException("User formatted incorrectly please provide the following:\n" +
                    "username, email, first_name, last_name");
        }
    }
    // checking UserID before Posting
    public void checkPostInputs(User user) throws ResourceException {
        if (user.getUserID() != null) {
            throw new ResourceException("Do not provide user_id.");
        }
        List<User> emails = userRepo.findByTemplate(null,
                user.getClientID(),
                user.getEmail(),
                null,
                null,
                null);
        if (emails.size() != 0) {
            throw new ResourceException("This email is already registered. You need to use another email.");
        }
        checkInputs(user);
    }

    public void checkInputsPut(User user) throws ResourceException {
        if (user.getEmail() != null){
            throw new ResourceException("You cannot change the email. Do not provide it.");
        } else if (checkIfInvalid(user.getUsername())) {
            throw new ResourceException("Username must be between 1-128 characters.");
        } else if (checkIfInvalid(user.getFirstName())) {
            throw new ResourceException("First name must be between 1-128 characters.");
        } else if (checkIfInvalid(user.getLastName())) {
            throw new ResourceException("Last name must be between 1-128 characters.");
        }
    }

    // checking UserID before Putting
    public void checkPutInputs(User user) throws ResourceException {
        if (user.getUserID() == null) {
            throw new ResourceException("Please provide user_id.");
        }
        checkInputsPut(user);
    }
}
