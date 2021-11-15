package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//@IdClass(FantasyStatsID.class)
@Table(name = "fantasy_stats")
public class FantasyStats implements Serializable {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stats_id")
    @JsonProperty("stats_id")
    private Integer stats_id;

//    @Id
    @Column(name = "player_id")
    @JsonProperty("player_id")
    private Integer player_id;

//    @Id
    @Column(name = "schedule_id")
    @JsonProperty("schedule_id")
    private Integer schedule_id;

    @Column(name = "client_id")
    @JsonProperty("client_id")
    private Integer client_id;

    @Column(name = "three_points")
    @JsonProperty("three_points")
    private int three_points;

    @Column(name = "two_points")
    @JsonProperty("two_points")
    private int two_points;

    @Column(name = "free_throws")
    @JsonProperty("free_throws")
    private int free_throws;

    @Column(name = "rebounds")
    @JsonProperty("rebounds")
    private int rebounds;

    @Column(name = "assists")
    @JsonProperty("assists")
    private int assists;

    @Column(name = "blocks")
    @JsonProperty("blocks")
    private int blocks;

    @Column(name = "steals")
    @JsonProperty("steals")
    private int steals;

    @Column(name = "turnovers")
    @JsonProperty("turnovers")
    private int turnovers;

    @Column(name = "tot_points")
    @JsonProperty("tot_points")
    private int tot_points;

    // class methods
    public FantasyStats() {
        this.stats_id=null;
        this.client_id=null;
        this.schedule_id=null;
        this.player_id=null;
    }

    public FantasyStats(Integer player_id,
                        Integer schedule_id,
                        Integer client_id,
                        Integer three_points,
                        Integer two_points,
                        Integer free_throws,
                        Integer rebounds,
                        Integer assists,
                        Integer blocks,
                        Integer steals,
                        Integer turnovers,
                        Integer tot_points) {
        this.stats_id = stats_id;
        this.player_id = player_id;
        this.schedule_id = schedule_id;
        this.client_id = client_id;
        this.three_points = three_points;
        this.two_points = two_points;
        this.free_throws = free_throws;
        this.rebounds = rebounds;
        this.assists = assists;
        this.blocks = blocks;
        this.steals = steals;
        this.turnovers = turnovers;
        this.tot_points = tot_points;

    }

    public void setStats_id(Integer stats_id) {
        this.stats_id = stats_id;
    }
    public Integer getStats_id() {
        return stats_id;
    }

    public Integer getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int playerID) {
        this.player_id = playerID;
    }

    public Integer getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int scheduleID) {
        this.schedule_id = scheduleID;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer clientID) {
        this.client_id = clientID;
    }

    public int getThree_points() {
        return three_points;
    }

    public void setThree_points(int threeP) {
        this.three_points = threeP;
    }

    public int getTwo_points() {
        return two_points;
    }

    public void setTwo_points(int twoP) {
        this.two_points = twoP;
    }

    public int getFree_throws() {
        return free_throws;
    }

    public void setFree_throws(int freeThrows) {
        this.free_throws = freeThrows;
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

    public int getTot_points() {
        return tot_points;
    }

    public void setTot_points(int totPoints) {
        this.tot_points = totPoints;
    }


    @Override
    public String toString() {
        return "\nFantasyPlayer {" +
                "\n\t stats_id=" + stats_id +
                ",\n\t player_id=" + player_id +
                ",\n\t schedule_id=" + schedule_id +
                ",\n\t client_id='" + client_id + '\'' +
                ",\n\t three_points='" + three_points + '\'' +
                ",\n\t two_points='" + two_points + '\'' +
                ",\n\t free_throws='" + free_throws + '\'' +
                ",\n\t rebounds=" + rebounds +
                ",\n\t assists='" + assists + '\'' +
                ",\n\t blocks='" + blocks + '\'' +
                ",\n\t steals='" + steals + '\'' +
                ",\n\t turnovers=" + turnovers +
                ",\n\t tot_points=" + tot_points +
                "\n\t}";
    }

}
