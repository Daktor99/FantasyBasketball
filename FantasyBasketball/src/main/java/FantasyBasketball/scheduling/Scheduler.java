package FantasyBasketball.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

import FantasyBasketball.exceptions.resourceNotFoundException;
import FantasyBasketball.scheduling.WeeklyUpdate;

import FantasyBasketball.services.fantasyGameService;
import FantasyBasketball.services.fantasyTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {


    @Autowired
    fantasyTeamService teamService;

    @Autowired
    fantasyGameService gameService;


    //@Scheduled(cron = "0 0 */1 * * *")
    @Async
    @Scheduled(cron = "*/5 * * * * *")
    public void hourlySchedule() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        Date now = new Date();
//        String strDate = sdf.format(now);
//        System.out.println("Java cron job expression:: " + strDate);
    }

    //    @Scheduled(cron = "0 0 0 * * SUN")
    @Async
    @Scheduled(cron = "*/10 * * * * *")
    public void weeklySchedule() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        Date now = new Date();
//        String strDate = sdf.format(now);
//        System.out.println("\t\t\tJava cron job expression:: " + strDate);
//
//        System.out.println("\t\t\tRun Weekly function being called.");
//        WeeklyUpdate weekly = new WeeklyUpdate();
//        try {
//            weekly.runWeekly(teamService, gameService);
//        } catch (resourceNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
