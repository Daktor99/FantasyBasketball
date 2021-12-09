package FantasyBasketball.scheduling;

import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.repositories.clientRepository;
import FantasyBasketball.repositories.fantasyGameRepository;
import FantasyBasketball.repositories.fantasyPlayerRepository;
import FantasyBasketball.repositories.fantasyStatsRepository;
import FantasyBasketball.services.fantasyGameService;
import FantasyBasketball.services.fantasyStatsService;
import FantasyBasketball.services.fantasyTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Scheduler {


    @Autowired
    fantasyTeamService teamService;

    @Autowired
    fantasyGameService gameService;

    @Autowired
    fantasyGameRepository gameRepo;

    @Autowired
    fantasyStatsService statService;

    @Autowired
    fantasyPlayerRepository playerRepo;

    @Autowired
    fantasyStatsRepository statsRepo;

    @Autowired
    clientRepository clientRepo;


    //@Scheduled(cron = "0 0 */1 * * *")
    @Async
    @Scheduled(cron = "*/15 * * * * *")
    public void hourlySchedule() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("Java cron job expression:: " + strDate);
//
//        System.out.println("\t\t\tRun Daily function being called.");
//        HourlyUpdate hourly = new HourlyUpdate();
//        try {
//            hourly.runHourly(playerRepo, gameRepo, statsRepo, clientRepo, now);
//        } catch (resourceNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    //@Scheduled(cron = "0 0 0 * * SUN")
    @Async
    @Scheduled(cron = "0 */1 * * * *")
    public void weeklySchedule() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        System.out.println("\t\t\tJava cron job expression:: " + strDate);

        System.out.println("\t\t\tRun Weekly function being called.");
        WeeklyUpdate weekly = new WeeklyUpdate();
        try {
            weekly.runWeekly(teamService, gameService, statService);
        } catch (resourceNotFoundException e) {
            e.printStackTrace();
        }
    }
}
