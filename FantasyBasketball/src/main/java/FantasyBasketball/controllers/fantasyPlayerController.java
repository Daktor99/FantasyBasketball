package FantasyBasketball.controllers;

import FantasyBasketball.services.fantasyPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class fantasyPlayerController {

    @Autowired
    fantasyPlayerService fantasyPlayerService;

}
