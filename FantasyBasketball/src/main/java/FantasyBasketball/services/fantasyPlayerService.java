package FantasyBasketball.services;

import FantasyBasketball.repositories.fantasyPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class fantasyPlayerService {

    @Autowired
    fantasyPlayerRepository repo;

}
