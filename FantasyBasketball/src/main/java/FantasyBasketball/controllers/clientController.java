package FantasyBasketball.controllers;

import FantasyBasketball.services.clientService;
import FantasyBasketball.services.fantasyGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class clientController {

    @Autowired
    clientService clientService;

    private static final Logger log = LoggerFactory.getLogger(fantasyGameController.class);

    private final HttpServletRequest request;

    // default constructor for fantasyGameController
    @Autowired
    public clientController(HttpServletRequest request) { this.request = request; }

}
