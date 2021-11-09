package FantasyBasketball.controllers;

import FantasyBasketball.services.fantasyTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class fantasyTeamController {

    @Autowired
    fantasyTeamService fantasyTeamService;

}
