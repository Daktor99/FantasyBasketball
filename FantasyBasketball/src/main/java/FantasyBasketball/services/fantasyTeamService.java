package FantasyBasketball.services;

import FantasyBasketball.repositories.fantasyTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class fantasyTeamService {

    @Autowired
    fantasyTeamRepository repo;

}
