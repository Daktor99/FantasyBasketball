package FantasyBasketball.models;

import javax.persistence.*;

@Entity
@Table(name = "fantasyStats")
public class FantasyStats {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerID")
    private int playerID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduleID")
    private int scheduleID;

    @Column(name = "3P")
    private int threeP;

    @Column(name = "2P")
    private int twoP;

    @Column(name = "FT")
    private int freeThrows;

    @Column(name = "R")
    private int rebounds;

    @Column(name = "A")
    private int assists;

    @Column(name = "B")
    private int blocks;

    @Column(name = "S")
    private int steals;

    @Column(name = "TO")
    private int turnovers;

    @Column(name = "totPoints")
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
