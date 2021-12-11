package FantasyBasketball.controllers;
import FantasyBasketball.exceptions.resourceException;
import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.models.Client;
import FantasyBasketball.models.User;
import FantasyBasketball.services.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static FantasyBasketball.controllers.controllerUtils.*;

@RestController
public class userController {

    @Autowired
    userService userService;

    private static final Logger log = LoggerFactory.getLogger(userController.class);

    private final HttpServletRequest request;

    // default constructor for userController class
    @Autowired
    public userController(HttpServletRequest request) { this.request = request; }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getUsersByTemplate(@RequestParam(value = "user_id", required = false) Integer user_id,
                                     @RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "first_name", required = false) String first_name,
                                     @RequestParam(value = "last_name", required = false) String last_name) {

        try {

            // log GET request and get client id
            logGetRequest(request, log);
            Integer client_id = getClientId(request);

            // get users given client info and requested query parameters
            List<User> result = userService.getUsersByTemplate(user_id, client_id, email, username, first_name, last_name);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception on GET: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> postUser(@RequestBody User newUser) {

        try {

            // log post request and get client details
            logPostRequest(request, log, newUser.toString());
            Integer client_id = getClientId(request);

            // make sure new user has correct client id
            newUser.setClientID(client_id);

            // Checks to make sure the input is valid to insert in DB
            userService.checkPostInputs(newUser);

            // Regular post
            List<User> result = userService.postUser(newUser);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch(resourceException e) {
            // exception thrown if User instance is not formatted correctly
            log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on POST: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // other exceptions
            log.error("Exception on POST: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {

            // log put request and get client id
            logPutRequest(request, log, user.toString());
            Integer client_id = getClientId(request);

            // Ensure correct client_id, check to make sure the input is valid to insert in DB
            user.setClientID(client_id);
            userService.checkPutInputs(user);

            // Regular put
            List<User> result = userService.updateUser(user);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (resourceException e) {
            // exception thrown if User instance is not formatted correctly
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (resourceNotFoundException e) {
            // If user not found in the database, throw exception not found
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // other exceptions
            log.error("Exception on PUT: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestParam(value = "user_id") Integer user_id) {
        try {

            // log delete request
            logDeleteRequest(request, log);

            // delete from
            userService.deleteUserById(user_id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (resourceNotFoundException e) {
            log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>("This action is not allowed, please check values and try again.", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            // other exceptions
            log.error("Exception on DELETE: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
