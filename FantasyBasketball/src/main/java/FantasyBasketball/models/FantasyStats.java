package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fantasy_stats")
@IdClass(FantasyStatsID.class)
public class FantasyStats implements Serializable {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    @JsonProperty("player_id")
    private Integer playerID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    @JsonProperty("schedule_id")
    private Integer scheduleID;

    @Column(name = "client_id")
    @JsonProperty("client_id")
    private Integer clientID = 1;

    @Column(name = "3_p")
    @JsonProperty("3_p")
    private int threeP;

    @Column(name = "2_p")
    @JsonProperty("2_p")
    private int twoP;

    @Column(name = "ft")
    @JsonProperty("ft")
    private int freeThrows;

    @Column(name = "r")
    @JsonProperty("r")
    private int rebounds;

    @Column(name = "a")
    @JsonProperty("a")
    private int assists;

    @Column(name = "b")
    @JsonProperty("b")
    private int blocks;

    @Column(name = "s")
    @JsonProperty("s")
    private int steals;

    @Column(name = "to")
    @JsonProperty("to")
    private int turnovers;

    @Column(name = "tot_points")
    @JsonProperty("tot_points")
    private int totPoints;

    // class methods

    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
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

    public int getFreeThrows() {
        return freeThrows;
    }

    public void setFreeThrows(int freeThrows) {
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
