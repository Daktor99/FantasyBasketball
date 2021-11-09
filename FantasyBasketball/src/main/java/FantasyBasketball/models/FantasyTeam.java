package FantasyBasketball.models;

public class FantasyTeam {

    // data members
    private int teamID;
    private String teamName;
    private int ownerID;
    private int leagueID;
    private int startPG;
    private int startSG;
    private int startSF;
    private int statPF;
    private int startC;
    private int bench1;
    private int bench2;
    private int teamWins;
    private int teamLosses;
    private int pointsScored;
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
