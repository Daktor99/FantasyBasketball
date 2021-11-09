package FantasyBasketball.models;

public class FantasyPlayer {

    // data members
    private int playedID;
    private int teamID;
    private String firstName;
    private String lastName;
    private String position;
    private int points;
    private String nbaTeam;

    // class methods
    public int getPlayedID() {
        return playedID;
    }

    public void setPlayedID(int playedID) {
        this.playedID = playedID;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getNbaTeam() {
        return nbaTeam;
    }

    public void setNbaTeam(String nbaTeam) {
        this.nbaTeam = nbaTeam;
    }
}
