package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "fantasy_team")
public class FantasyTeam {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    @JsonProperty("team_id")
    private Integer teamID;

    @Column(name = "client_id")
    @JsonProperty("client_id")
    private Integer clientID = 1;

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
    private Integer startPF;

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

    // default constructor

    public FantasyTeam() {
        this.teamID = null;
        this.clientID = 1;
        this.teamName = null;
        this.ownerID = null;
        this.leagueID = null;
        this.startPG = null;
        this.startSG = null;
        this.startSF = null;
        this.startPF = null;
        this.startC = null;
        this.bench1 = null;
        this.bench2 = null;
        this.teamWins = 0;
        this.teamLosses = 0;
        this.pointsScored = 0;
        this.pointsAgainst = 0;
    }

    public FantasyTeam(Integer teamID,
                       Integer clientID,
                       String teamName,
                       Integer ownerID,
                       Integer leagueID,
                       Integer startPG,
                       Integer startSG,
                       Integer startSF,
                       Integer startPF,
                       Integer startC,
                       Integer bench1,
                       Integer bench2,
                       Integer teamWins,
                       Integer teamLosses,
                       Integer pointsScored,
                       Integer pointsAgainst) {
        this.teamID = teamID;
        this.clientID = clientID;
        this.teamName = teamName;
        this.ownerID = ownerID;
        this.leagueID = leagueID;
        this.startPG = startPG;
        this.startSG = startSG;
        this.startSF = startSF;
        this.startPF = startPF;
        this.startC = startC;
        this.bench1 = bench1;
        this.bench2 = bench2;
        this.teamWins = teamWins;
        this.teamLosses = teamLosses;
        this.pointsScored = pointsScored;
        this.pointsAgainst = pointsAgainst;
    }

    // Getter and Setters

    public Integer getTeamID() {
        return teamID;
    }

    public void setTeamID(Integer teamID) {
        this.teamID = teamID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
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

    public Integer getStartPF() {
        return startPF;
    }

    public void setStartPF(Integer startPF) {
        this.startPF = startPF;
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

    // Return as JSON string

    @Override
    public String toString() {
        return "\nFantasyTeam {" +
                "\n\tteamID=" + teamID +
                ",\n\t clientID=" + clientID +
                ",\n\t teamName='" + teamName + '\'' +
                ",\n\t ownerID=" + ownerID +
                ",\n\t leagueID=" + leagueID +
                ",\n\t startPG=" + startPG +
                ",\n\t startSG=" + startSG +
                ",\n\t startSF=" + startSF +
                ",\n\t statPF=" + startPF +
                ",\n\t startC=" + startC +
                ",\n\t bench1=" + bench1 +
                ",\n\t bench2=" + bench2 +
                ",\n\t teamWins=" + teamWins +
                ",\n\t teamLosses=" + teamLosses +
                ",\n\t pointsScored=" + pointsScored +
                ",\n\t pointsAgainst=" + pointsAgainst +
                "\n\t}";
    }
}
