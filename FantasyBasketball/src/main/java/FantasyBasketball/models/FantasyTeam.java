package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "fantasyTeam")
public class FantasyTeam {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    @JsonProperty("team_id")
    private Integer teamID;

    @Column(name = "team_name")
    @JsonProperty("team_name")
    private String teamName;

    @Column(name = "owner_id")
    @JsonProperty("owner_id")
    private Integer ownerID;

    @Column(name = "league_id")
    @JsonProperty("league_id")
    private Integer leagueID;

    @Column(name = "start_pg_id")
    @JsonProperty("start_pg_id")
    private Integer startPG;

    @Column(name = "start_sg_id")
    @JsonProperty("start_sg_id")
    private Integer startSG;

    @Column(name = "start_sf_id")
    @JsonProperty("start_sf_id")
    private Integer startSF;

    @Column(name = "start_pf_id")
    @JsonProperty("start_pf_id")
    private Integer statPF;

    @Column(name = "start_c_id")
    @JsonProperty("start_c_id")
    private Integer startC;

    @Column(name = "bench_1_id")
    @JsonProperty("bench_1_id")
    private Integer bench1;

    @Column(name = "bench_2_id")
    @JsonProperty("bench_2_id")
    private Integer bench2;

    @Column(name = "team_wins")
    @JsonProperty("team_wins")
    private Integer teamWins;

    @Column(name = "team_losses")
    @JsonProperty("team_losses")
    private Integer teamLosses;

    @Column(name = "points_scored")
    @JsonProperty("points_scored")
    private Integer pointsScored;

    @Column(name = "points_against")
    @JsonProperty("points_against")
    private Integer pointsAgainst;

    // class methods

    public Integer getTeamID() {
        return teamID;
    }

    public void setTeamID(Integer teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }

    public Integer getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(Integer leagueID) {
        this.leagueID = leagueID;
    }

    public Integer getStartPG() {
        return startPG;
    }

    public void setStartPG(Integer startPG) {
        this.startPG = startPG;
    }

    public Integer getStartSG() {
        return startSG;
    }

    public void setStartSG(Integer startSG) {
        this.startSG = startSG;
    }

    public Integer getStartSF() {
        return startSF;
    }

    public void setStartSF(Integer startSF) {
        this.startSF = startSF;
    }

    public Integer getStatPF() {
        return statPF;
    }

    public void setStatPF(Integer statPF) {
        this.statPF = statPF;
    }

    public Integer getStartC() {
        return startC;
    }

    public void setStartC(Integer startC) {
        this.startC = startC;
    }

    public Integer getBench1() {
        return bench1;
    }

    public void setBench1(Integer bench1) {
        this.bench1 = bench1;
    }

    public Integer getBench2() {
        return bench2;
    }

    public void setBench2(Integer bench2) {
        this.bench2 = bench2;
    }

    public Integer getTeamWins() {
        return teamWins;
    }

    public void setTeamWins(Integer teamWins) {
        this.teamWins = teamWins;
    }

    public Integer getTeamLosses() {
        return teamLosses;
    }

    public void setTeamLosses(Integer teamLosses) {
        this.teamLosses = teamLosses;
    }

    public Integer getPointsScored() {
        return pointsScored;
    }

    public void setPointsScored(Integer pointsScored) {
        this.pointsScored = pointsScored;
    }

    public Integer getPointsAgainst() {
        return pointsAgainst;
    }

    public void setPointsAgainst(Integer pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }
}
