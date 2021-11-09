package FantasyBasketball.services;

import FantasyBasketball.repositories.fantasyGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class fantasyGameService {

    @Autowired
    fantasyGameRepository repo;

}
