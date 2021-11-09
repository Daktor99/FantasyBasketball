package FantasyBasketball.models;

public class FantasyStats {

    // data members
    private int playerID;
    private int scheduleID;
    private int threeP;
    private int twoP;
    private int freeThrows;
    private int rebounds;
    private int assists;
    private int blocks;
    private int steals;
    private int turnovers;
    private int totPoints;

    // class methods
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getThreeP() {
        return threeP;
    }

    public void setThreeP(int threeP) {
        this.threeP = threeP;
    }

    public int getTwoP() {
        return twoP;
    }

    public void setTwoP(int twoP) {
        this.twoP = twoP;
    }

    public int getFreeT() {
        return freeThrows;
    }

    public void setFreeT(int freeThrows) {
        this.freeThrows = freeThrows;
    }

    public int getRebounds() {
        return rebounds;
    }

    public void setRebounds(int rebounds) {
        this.rebounds = rebounds;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public int getSteals() {
        return steals;
    }

    public void setSteals(int steals) {
        this.steals = steals;
    }

    public int getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(int turnovers) {
        this.turnovers = turnovers;
    }

    public int getTotPoints() {
        return totPoints;
    }

    public void setTotPoints(int totPoints) {
        this.totPoints = totPoints;
    }
}
