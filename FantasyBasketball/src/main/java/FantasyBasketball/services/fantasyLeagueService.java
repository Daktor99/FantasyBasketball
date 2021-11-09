package FantasyBasketball.services;

import FantasyBasketball.repositories.fantasyLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class fantasyLeagueService {

    @Autowired
    fantasyLeagueRepository repo;

}
