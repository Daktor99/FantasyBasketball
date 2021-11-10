package FantasyBasketball.controllers;

import FantasyBasketball.models.User;
import FantasyBasketball.repositories.userRepository;
import FantasyBasketball.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class userController {

    @Autowired
    userService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getByID(@RequestParam(value = "user_id", required = false) Integer user_id,
                                     @RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "first_name", required = false) String first_name,
                                     @RequestParam(value = "last_name", required = false) String last_name) {
        List<User> result = userService.getUsersByTemplate(user_id, email, username, first_name, last_name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
