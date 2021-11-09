package FantasyBasketball.controllers;

import FantasyBasketball.services.fantasyLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class fantasyLeagueController {

    @Autowired
    fantasyLeagueService fantasyLeagueService;

}
