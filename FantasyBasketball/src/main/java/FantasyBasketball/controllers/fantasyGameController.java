package FantasyBasketball.controllers;

import FantasyBasketball.services.fantasyGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class fantasyGameController {

    @Autowired
    fantasyGameService fantasyGameService;
    
}
