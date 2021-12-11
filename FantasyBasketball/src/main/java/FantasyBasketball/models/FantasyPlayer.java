package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Integer clientID;

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "position")
    @JsonProperty("position")
    private String position;

    @Column(name = "nba_team")
    @JsonProperty("nba_team")
    private String nbaTeam;

    @Column(name = "league_id")
    @JsonProperty("league_id")
    private Integer leagueID;

    @Column(name = "ball_api_id")
    @JsonProperty("ball_api_id")
    private Integer ballapiID;

    // class methods

    //default constructor
    public FantasyPlayer() {
        this.playerID=null;
        this.teamID=null;
        this.clientID=null;
        this.leagueID=null;
        this.firstName=null;
        this.lastName=null;
        this.nbaTeam=null;
        this.position=null;
    }
  
    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Integer playerID) {
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

    public Integer getBallapiID() {
        return ballapiID;
    }

    public void setBallapiID(Integer ballapiID) {
        this.ballapiID = ballapiID;
    }

    public FantasyPlayer setNewPlayer(Integer playerID, Integer teamID,
                                      Integer clientID, String firstName,
                                      String lastName, String nbaTeam,
                                      String position, Integer leagueID,
                                      Integer ballapiID) {
        this.playerID=playerID;
        this.teamID=teamID;
        this.clientID=clientID;
        this.firstName=firstName;
        this.lastName=lastName;
        this.nbaTeam=nbaTeam;
        this.position=position;
        this.leagueID=leagueID;
        this.ballapiID=ballapiID;
        return this;
    }

    // overriding toString() function
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
