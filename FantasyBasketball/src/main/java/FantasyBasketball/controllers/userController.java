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

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public ResponseEntity<?> getByID(@RequestParam(value = "userID", required = true) Integer userID) {
        List<User> result = userService.getByID(userID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
