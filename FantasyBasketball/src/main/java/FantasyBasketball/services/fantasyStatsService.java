package FantasyBasketball.services;

import FantasyBasketball.repositories.fantasyStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class fantasyStatsService {

    @Autowired
    fantasyStatsRepository repo;

}
