package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "fantasy_player")
public class FantasyPlayer {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    @JsonProperty("player_id")
    private int playerID;

    @Column(name = "team_id")
    @JsonProperty("team_id")
    private int teamID;

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "position")
    @JsonProperty("position")
    private String position;

//    @Column(name = "points")
//    private int points;

    @Column(name = "nba_team")
    @JsonProperty("nba_team")
    private String nbaTeam;

    @Column(name = "league_id")
    @JsonProperty("league_id")
    private Integer leagueID;

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

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }
}
