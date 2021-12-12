package FantasyBasketball.scheduling;

import FantasyBasketball.exceptions.ResourceNotFoundException;
import FantasyBasketball.repositories.clientRepository;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.repositories.fantasyStatsRepository;
import FantasyBasketball.utilities.FantasyStatsUtility;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class HourlyUpdate {

    public void runHourly(fantasyPlayerRepository playerRepo, fantasyGameRepository gameRepo,
                          fantasyStatsRepository statsRepo, clientRepository clientRepo, Date end_date)
            throws ResourceNotFoundException, IOException, ParseException {
        FantasyStatsUtility statsUtility = new FantasyStatsUtility();
        statsUtility.API_player_stats_importation(playerRepo, gameRepo, statsRepo, clientRepo, end_date);
    }
}
