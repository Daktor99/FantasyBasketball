package FantasyBasketball.models;

import javax.persistence.*;

@Entity
@Table(name = "fantasyTeam")
public class FantasyTeam {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int teamID;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "owner_id")
    private int ownerID;

    @Column(name = "league_id")
    private int leagueID;

    @Column(name = "start_pg_id")
    private int startPG;

    @Column(name = "start_sg_id")
    private int startSG;

    @Column(name = "start_sf_id")
    private int startSF;

    @Column(name = "start_pf_id")
    private int statPF;

    @Column(name = "start_c_id")
    private int startC;

    @Column(name = "bench_1_id")
    private int bench1;

    @Column(name = "bench_2_id")
    private int bench2;

    @Column(name = "team_wins")
    private int teamWins;

    @Column(name = "team_losses")
    private int teamLosses;

    @Column(name = "points_scored")
    private int pointsScored;

    @Column(name = "points_against")
    private int pointsAgainst;

    // class methods
    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public int getStartPG() {
        return startPG;
    }

    public void setStartPG(int startPG) {
        this.startPG = startPG;
    }

    public int getStartSG() {
        return startSG;
    }

    public void setStartSG(int startSG) {
        this.startSG = startSG;
    }

    public int getStartSF() {
        return startSF;
    }

    public void setStartSF(int startSF) {
        this.startSF = startSF;
    }

    public int getStatPF() {
        return statPF;
    }

    public void setStatPF(int statPF) {
        this.statPF = statPF;
    }

    public int getStartC() {
        return startC;
    }

    public void setStartC(int startC) {
        this.startC = startC;
    }

    public int getBench1() {
        return bench1;
    }

    public void setBench1(int bench1) {
        this.bench1 = bench1;
    }

    public int getBench2() {
        return bench2;
    }

    public void setBench2(int bench2) {
        this.bench2 = bench2;
    }

    public int getTeamWins() {
        return teamWins;
    }

    public void setTeamWins(int teamWins) {
        this.teamWins = teamWins;
    }

    public int getTeamLosses() {
        return teamLosses;
    }

    public void setTeamLosses(int teamLosses) {
        this.teamLosses = teamLosses;
    }

    public int getPointsScored() {
        return pointsScored;
    }

    public void setPointsScored(int pointsScored) {
        this.pointsScored = pointsScored;
    }

    public int getPointsAgainst() {
        return pointsAgainst;
    }

    public void setPointsAgainst(int pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }
}
