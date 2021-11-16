package FantasyBasketball.models;

import java.io.Serializable;

// Constructor for FantasyStats having Primary Keys as StatID, PlayerID and ScheduleID
public class FantasyStatsID implements Serializable {

    // data members
    private int playerID;

    private int scheduleID;

    // Default Constructor
    public FantasyStatsID(int playerID, int scheduleID) {
        this.playerID = playerID;
        this.scheduleID = scheduleID;
    }
}
