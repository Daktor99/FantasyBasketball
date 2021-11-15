package FantasyBasketball.models;

import java.io.Serializable;

public class FantasyStatsID implements Serializable {

    private int playerID;

    private int scheduleID;

    // Had to create this constructor since the creation of fantasyStats was not working
    public FantasyStatsID(){}

    public FantasyStatsID(int playerID, int scheduleID) {
        this.playerID = playerID;
        this.scheduleID = scheduleID;
    }
}
