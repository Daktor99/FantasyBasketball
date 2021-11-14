package FantasyBasketball.utilities;

import FantasyBasketball.models.FantasyTeam;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class robinRoundScheduler {

    private List<FantasyTeam> teamList;
    private LocalDate startDate;
    private LocalDate endDate;

    // constructor
    public robinRoundScheduler(List<FantasyTeam> teamList, LocalDate startDate, LocalDate endDate) {
        this.teamList   = new ArrayList<>(teamList);
        this.startDate  = startDate;
        this.endDate    = endDate;
    }


}
