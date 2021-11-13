package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "fantasyGame")
public class FantasyGame {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    @JsonProperty("schedule_id")
    private Integer scheduleID;

    @Column(name = "league_id")
    @JsonProperty("league_id")
    private Integer leagueID;

    @Column(name = "home_team_id")
    @JsonProperty("home_team_id")
    private Integer homeTeamID;

    @Column(name = "away_team_id")
    @JsonProperty("away_team_id")
    private Integer awayTeamID;

    @Column(name = "game_start_date")
    @JsonProperty("game_start_date")
    private LocalDate gameStartDate;

    @Column(name = "game_end_date")
    @JsonProperty("game_end_date")
    private LocalDate gameEndDate;

    @Column(name = "winner_id")
    @JsonProperty("winner_id")
    private Integer winner;

    @Column(name = "home_points")
    @JsonProperty("home_points")
    private Integer homePoints = 0;

    @Column(name = "away_points")
    @JsonProperty("away_points")
    private Integer awayPoints = 0;

    @Column(name = "home_start_pg_id")
    @JsonProperty("home_start_pg_id")
    private Integer startHomePG;

    @Column(name = "home_start_sg_id")
    @JsonProperty("home_start_sg_id")
    private Integer startHomeSG;

    @Column(name = "home_start_sf_id")
    @JsonProperty("home_start_sf_id")
    private Integer startHomeSF;

    @Column(name = "home_start_pf_id")
    @JsonProperty("home_start_pf_id")
    private Integer startHomePF;

    @Column(name = "home_start_c_id")
    @JsonProperty("home_start_c_id")
    private Integer startHomeC;

    @Column(name = "home_bench_1_id")
    @JsonProperty("home_bench_1_id")
    private Integer homeBench1;

    @Column(name = "home_bench_2_id")
    @JsonProperty("home_bench_2_id")
    private Integer homeBench2;

    @Column(name = "away_start_pg_id")
    @JsonProperty("away_start_pg_id")
    private Integer startAwayPG;

    @Column(name = "away_start_sg_id")
    @JsonProperty("away_start_sg_id")
    private Integer startAwaySG;

    @Column(name = "away_start_sf_id")
    @JsonProperty("away_start_sf_id")
    private Integer startAwaySF;

    @Column(name = "away_start_pf_id")
    @JsonProperty("away_start_pf_id")
    private Integer startAwayPF;

    @Column(name = "away_start_c_id")
    @JsonProperty("away_start_c_id")
    private Integer startAwayC;

    @Column(name = "away_bench_1_id")
    @JsonProperty("away_bench_1_id")
    private Integer awayBench1;

    @Column(name = "away_bench_2_id")
    @JsonProperty("away_bench_2_id")
    private Integer awayBench2;


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

    public LocalDate getGameStartDate() {
        return gameStartDate;
    }

    public void setGameStartDate(LocalDate gameStartDate) {
        this.gameStartDate = gameStartDate;
    }

    public LocalDate getGameEndDate() {
        return gameEndDate;
    }

    public void setGameEndDate(LocalDate gameEndDate) {
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

    public Integer getHomeBench1() {
        return homeBench1;
    }

    public void setHomeBench1(Integer homeBench1) {
        this.homeBench1 = homeBench1;
    }

    public Integer getHomeBench2() {
        return homeBench2;
    }

    public void setHomeBench2(Integer homeBench2) {
        this.homeBench2 = homeBench2;
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

    public Integer getAwayBench1() {
        return awayBench1;
    }

    public void setAwayBench1(Integer awayBench1) {
        this.awayBench1 = awayBench1;
    }

    public Integer getAwayBench2() {
        return awayBench2;
    }

    public void setAwayBench2(Integer awayBench2) {
        this.awayBench2 = awayBench2;
    }

    @Override
    public String toString() {
        return "\nFantasyGame {" +
                "\n\tscheduleID=" + scheduleID +
                ",\n\t leagueID=" + leagueID +
                ",\n\t homeTeamID=" + homeTeamID +
                ",\n\t awayTeamID=" + awayTeamID +
                ",\n\t gameStartDate=" + gameStartDate +
                ",\n\t gameEndDate=" + gameEndDate +
                ",\n\t winner=" + winner +
                ",\n\t homePoints=" + homePoints +
                ",\n\t awayPoints=" + awayPoints +
                ",\n\t startHomePG=" + startHomePG +
                ",\n\t startHomeSG=" + startHomeSG +
                ",\n\t startHomeSF=" + startHomeSF +
                ",\n\t startHomePF=" + startHomePF +
                ",\n\t startHomeC=" + startHomeC +
                ",\n\t homeBench1=" + homeBench1 +
                ",\n\t homeBench2=" + homeBench2 +
                ",\n\t startAwayPG=" + startAwayPG +
                ",\n\t startAwaySG=" + startAwaySG +
                ",\n\t startAwaySF=" + startAwaySF +
                ",\n\t startAwayPF=" + startAwayPF +
                ",\n\t startAwayC=" + startAwayC +
                ",\n\t awayBench1=" + awayBench1 +
                ",\n\t awayBench2=" + awayBench2 +
                "\n\t}";
    }
}
