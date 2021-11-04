package FantasyBasketball.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ResponseEntity<?> displayHomepage() {
        return new ResponseEntity<>("Hello, welcome to Fantasy Basketball!", HttpStatus.OK);
    }
}
