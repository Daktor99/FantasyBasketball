package FantasyBasketball.controllers;

import FantasyBasketball.exceptions.userException;
import FantasyBasketball.exceptions.userNotFoundException;
import FantasyBasketball.models.User;
import FantasyBasketball.repositories.userRepository;
import FantasyBasketball.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class userController {

    @Autowired
    userService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getUsersByTemplate(@RequestParam(value = "user_id", required = false) Integer user_id,
                                     @RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "first_name", required = false) String first_name,
                                     @RequestParam(value = "last_name", required = false) String last_name) {

        List<User> result = userService.getUsersByTemplate(user_id, email, username, first_name, last_name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> postUser(@RequestBody User newUser) {

        try {

            // Checks to make sure the input is valid to insert in DB
            userService.checkPostInputs(newUser);

            // Regular post
            List<User> result = userService.postUser(newUser);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch(userException e) {
            // exception thrown if User instance is not formatted correctly
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // all other exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {

            // Checks to make sure the input is valid to insert in DB
            userService.checkPutInputs(user);

            // Regular put
            List<User> result = userService.updateUser(user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (userException e) {
            // exception thrown if User instance is not formatted correctly
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (userNotFoundException e) {
            // If user not found in the database, throw exception not found
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // all other exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestParam(value = "user_id", required = true) Integer user_id) {
        try {
            userService.deleteUserById(user_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (userNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // all other exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
