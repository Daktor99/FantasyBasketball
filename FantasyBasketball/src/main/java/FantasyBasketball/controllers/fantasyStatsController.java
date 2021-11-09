package FantasyBasketball.controllers;

import FantasyBasketball.services.fantasyStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class fantasyStatsController {

    @Autowired
    fantasyStatsService fantasyStatsService;

}
