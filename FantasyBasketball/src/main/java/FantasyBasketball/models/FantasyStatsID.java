package FantasyBasketball.models;

import java.io.Serializable;

public class FantasyStatsID implements Serializable {

    private int playerID;

    private int scheduleID;

    public FantasyStatsID(){}

    public FantasyStatsID(int playerID, int scheduleID) {
        this.playerID = playerID;
        this.scheduleID = scheduleID;
    }
}
