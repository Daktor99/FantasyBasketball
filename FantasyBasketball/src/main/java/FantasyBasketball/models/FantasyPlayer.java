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
    private Integer playerID;

    @Column(name = "team_id")
    @JsonProperty("team_id")
    private Integer teamID;

    @Column(name = "client_id")
    @JsonProperty("client_id")
    private Integer clientID = 1;

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
    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayedID(Integer playerID) {
        this.playerID = playerID;
    }

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

    public String getNbaTeam() {
        return nbaTeam;
    }

    public void setNbaTeam(String nbaTeam) {
        this.nbaTeam = nbaTeam;
    }

    public Integer getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(Integer leagueID) {
        this.leagueID = leagueID;
    }

    //    public int getPoints() {
//        return points;
//    }
//
//    public void setPoints(int points) {
//        this.points = points;
//    }


    @Override
    public String toString() {
        return "\nFantasyPlayer {" +
                "\n\t playerID=" + playerID +
                ",\n\t teamID=" + teamID +
                ",\n\t clientID=" + clientID +
                ",\n\t firstName='" + firstName + '\'' +
                ",\n\t lastName='" + lastName + '\'' +
                ",\n\t position='" + position + '\'' +
                ",\n\t nbaTeam='" + nbaTeam + '\'' +
                ",\n\t leagueID=" + leagueID +
                "\n\t}";
    }
}
