package FantasyBasketball.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fantasyGame")
public class FantasyGame {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleID;

    @Column(name = "league_id")
    private Integer leagueID;

    @Column(name = "home_team_id")
    private Integer homeTeamID;

    @Column(name = "away_team_id")
    private Integer awayTeamID;

    @Column(name = "game_start_date")
    private Date gameStartDate;

    @Column(name = "game_end_date")
    private Date gameEndDate;

    @Column(name = "winner_id")
    private Integer winner;

    @Column(name = "home_points")
    private Integer homePoints;

    @Column(name = "away_points")
    private Integer awayPoints;

    @Column(name = "home_start_pg_id")
    private Integer startHomePG;

    @Column(name = "home_start_sg_id")
    private Integer startHomeSG;

    @Column(name = "home_start_sf_id")
    private Integer startHomeSF;

    @Column(name = "home_start_pf_id")
    private Integer startHomePF;

    @Column(name = "home_start_c_id")
    private Integer startHomeC;

    @Column(name = "home_bench_1_id")
    private Integer homebench1;

    @Column(name = "home_bench_2_id")
    private Integer homebench2;

    @Column(name = "away_start_pg_id")
    private Integer startAwayPG;

    @Column(name = "away_start_sg_id")
    private Integer startAwaySG;

    @Column(name = "away_start_sf_id")
    private Integer startAwaySF;

    @Column(name = "away_start_pf_id")
    private Integer startAwayPF;

    @Column(name = "away_start_c_id")
    private Integer startAwayC;

    @Column(name = "away_bench_1_id")
    private Integer awaybench1;

    @Column(name = "away_bench_2_id")
    private Integer awaybench2;


    // class methods


    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Integer getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(Integer leagueID) {
        this.leagueID = leagueID;
    }

    public Integer getHomeTeamID() {
        return homeTeamID;
    }

    public void setHomeTeamID(Integer homeTeamID) {
        this.homeTeamID = homeTeamID;
    }

    public Integer getAwayTeamID() {
        return awayTeamID;
    }

    public void setAwayTeamID(Integer awayTeamID) {
        this.awayTeamID = awayTeamID;
    }

    public Date getGameStartDate() {
        return gameStartDate;
    }

    public void setGameStartDate(Date gameStartDate) {
        this.gameStartDate = gameStartDate;
    }

    public Date getGameEndDate() {
        return gameEndDate;
    }

    public void setGameEndDate(Date gameEndDate) {
        this.gameEndDate = gameEndDate;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getHomePoints() {
        return homePoints;
    }

    public void setHomePoints(Integer homePoints) {
        this.homePoints = homePoints;
    }

    public Integer getAwayPoints() {
        return awayPoints;
    }

    public void setAwayPoints(Integer awayPoints) {
        this.awayPoints = awayPoints;
    }

    public Integer getStartHomePG() {
        return startHomePG;
    }

    public void setStartHomePG(Integer startHomePG) {
        this.startHomePG = startHomePG;
    }

    public Integer getStartHomeSG() {
        return startHomeSG;
    }

    public void setStartHomeSG(Integer startHomeSG) {
        this.startHomeSG = startHomeSG;
    }

    public Integer getStartHomeSF() {
        return startHomeSF;
    }

    public void setStartHomeSF(Integer startHomeSF) {
        this.startHomeSF = startHomeSF;
    }

    public Integer getStartHomePF() {
        return startHomePF;
    }

    public void setStartHomePF(Integer startHomePF) {
        this.startHomePF = startHomePF;
    }

    public Integer getStartHomeC() {
        return startHomeC;
    }

    public void setStartHomeC(Integer startHomeC) {
        this.startHomeC = startHomeC;
    }

    public Integer getHomebench1() {
        return homebench1;
    }

    public void setHomebench1(Integer homebench1) {
        this.homebench1 = homebench1;
    }

    public Integer getHomebench2() {
        return homebench2;
    }

    public void setHomebench2(Integer homebench2) {
        this.homebench2 = homebench2;
    }

    public Integer getStartAwayPG() {
        return startAwayPG;
    }

    public void setStartAwayPG(Integer startAwayPG) {
        this.startAwayPG = startAwayPG;
    }

    public Integer getStartAwaySG() {
        return startAwaySG;
    }

    public void setStartAwaySG(Integer startAwaySG) {
        this.startAwaySG = startAwaySG;
    }

    public Integer getStartAwaySF() {
        return startAwaySF;
    }

    public void setStartAwaySF(Integer startAwaySF) {
        this.startAwaySF = startAwaySF;
    }

    public Integer getStartAwayPF() {
        return startAwayPF;
    }

    public void setStartAwayPF(Integer startAwayPF) {
        this.startAwayPF = startAwayPF;
    }

    public Integer getStartAwayC() {
        return startAwayC;
    }

    public void setStartAwayC(Integer startAwayC) {
        this.startAwayC = startAwayC;
    }

    public Integer getAwaybench1() {
        return awaybench1;
    }

    public void setAwaybench1(Integer awaybench1) {
        this.awaybench1 = awaybench1;
    }

    public Integer getAwaybench2() {
        return awaybench2;
    }

    public void setAwaybench2(Integer awaybench2) {
        this.awaybench2 = awaybench2;
    }
}
