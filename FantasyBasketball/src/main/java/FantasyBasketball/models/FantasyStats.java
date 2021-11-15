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

    @Column(name = "league_id")
    @JsonProperty("league_id")
    private Integer leagueID;

    @Column(name = "3_p")
    @JsonProperty("3_p")
    private Integer threeP;

    @Column(name = "2_p")
    @JsonProperty("2_p")
    private Integer twoP;

    @Column(name = "ft")
    @JsonProperty("ft")
    private Integer freeThrows;

    @Column(name = "r")
    @JsonProperty("r")
    private Integer rebounds;

    @Column(name = "a")
    @JsonProperty("a")
    private Integer assists;

    @Column(name = "b")
    @JsonProperty("b")
    private Integer blocks;

    @Column(name = "s")
    @JsonProperty("s")
    private Integer steals;

    @Column(name = "to")
    @JsonProperty("to")
    private Integer turnovers;

    @Column(name = "tot_points")
    @JsonProperty("tot_points")
    private Integer totPoints;

    public FantasyStats(Integer player_id, Integer schedule_id, Integer client_id, Integer league_id) {
        this.playerID = player_id;
        this.scheduleID = schedule_id;
        this.clientID = client_id;
        this.leagueID = league_id;
    }

    // class methods

    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Integer playerID) {
        this.playerID = playerID;
    }

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public Integer getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(Integer leagueID) {
        this.leagueID = leagueID;
    }

    public Integer getThreeP() {
        return threeP;
    }

    public void setThreeP(Integer threeP) {
        this.threeP = threeP;
    }

    public Integer getTwoP() {
        return twoP;
    }

    public void setTwoP(Integer twoP) {
        this.twoP = twoP;
    }

    public Integer getFreeThrows() {
        return freeThrows;
    }

    public void setFreeThrows(Integer freeThrows) {
        this.freeThrows = freeThrows;
    }

    public Integer getRebounds() {
        return rebounds;
    }

    public void setRebounds(Integer rebounds) {
        this.rebounds = rebounds;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public Integer getSteals() {
        return steals;
    }

    public void setSteals(Integer steals) {
        this.steals = steals;
    }

    public Integer getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(Integer turnovers) {
        this.turnovers = turnovers;
    }

    public Integer getTotPoints() {
        return totPoints;
    }

    public void setTotPoints(Integer totPoints) {
        this.totPoints = totPoints;
    }

    @Override
    public String toString() {
        return "FantasyStats {" +
                "\n\tplayerID=" + playerID +
                ",\n\t scheduleID=" + scheduleID +
                ",\n\t clientID=" + clientID +
                ",\n\t leagueID=" + leagueID +
                ",\n\t threeP=" + threeP +
                ",\n\t twoP=" + twoP +
                ",\n\t freeThrows=" + freeThrows +
                ",\n\t rebounds=" + rebounds +
                ",\n\t assists=" + assists +
                ",\n\t blocks=" + blocks +
                ",\n\t steals=" + steals +
                ",\n\t turnovers=" + turnovers +
                ",\n\t totPoints=" + totPoints +
                "\n\t}";
    }
}
