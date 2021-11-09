package FantasyBasketball.models;

import javax.persistence.*;

@Entity
@Table(name = "fantasyPlayer")
public class FantasyPlayer {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerID")
    private int playerID;

    @Column(name = "teamID")
    private int teamID;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "position")
    private String position;

//    @Column(name = "points")
//    private int points;

    @Column(name = "nbaTeam")
    private String nbaTeam;

    // class methods
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayedID(int playerID) {
        this.playerID = playerID;
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

//    public int getPoints() {
//        return points;
//    }
//
//    public void setPoints(int points) {
//        this.points = points;
//    }

    public String getNbaTeam() {
        return nbaTeam;
    }

    public void setNbaTeam(String nbaTeam) {
        this.nbaTeam = nbaTeam;
    }
}
