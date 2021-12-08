package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fantasyLeague")
public class FantasyLeague {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "league_id")
    @JsonProperty("league_id")
    private Integer leagueID;

    @Column(name = "client_id")
    @JsonProperty("client_id")
    private Integer clientID;

    @Column(name = "league_name")
    @JsonProperty("league_name")
    private String leagueName;

    @Column(name = "admin_id")
    @JsonProperty("admin_id")
    private Integer adminID;

    @Column(name = "league_size")
    @JsonProperty("league_size")
    private Integer leagueSize;

    @Column(name = "draft_finished")
    @JsonProperty("draft_finished")
    private Boolean draftFinished;

    @Column(name = "league_start_date")
    @JsonProperty("league_start_date")
    private LocalDate leagueStartDate;

    @Column(name = "num_weeks")
    @JsonProperty("num_weeks")
    private Integer numWeeks;


    // default constructor
    public FantasyLeague() {
        this.leagueID=null;
        this.clientID=null;
        this.leagueName=null;
        this.adminID=null;
        this.leagueSize=null;
        this.draftFinished=null;
        this.leagueStartDate=null;
        this.numWeeks=null;
    }

    // constructor
    public FantasyLeague(Integer league_id,
                         Integer client_id,
                         String league_name,
                         Integer admin_id,
                         Integer league_size,
                         Boolean draft_finished,
                         LocalDate league_start_date,
                         Integer num_weeks) {
        this.leagueID=league_id;
        this.clientID=client_id;
        this.leagueName=league_name;
        this.adminID=admin_id;
        this.leagueSize=league_size;
        this.draftFinished=draft_finished;
        this.leagueStartDate=league_start_date;
        this.numWeeks=num_weeks;
    }

    // class methods

    public Integer getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(Integer leagueID) {
        this.leagueID = leagueID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public Integer getLeagueSize() {
        return leagueSize;
    }

    public void setLeagueSize(Integer leagueSize) {
        this.leagueSize = leagueSize;
    }

    public Boolean getDraftFinished() {
        return draftFinished;
    }

    public void setDraftFinished(Boolean draftFinished) {
        this.draftFinished = draftFinished;
    }

    public LocalDate getLeagueStartDate() {
        return leagueStartDate;
    }

    public void setLeagueStartDate(LocalDate leagueStartDate) {
        this.leagueStartDate = leagueStartDate;
    }

    public Integer getNumWeeks() {
        return numWeeks;
    }

    public void setNumWeeks(Integer numWeeks) {
        this.numWeeks = numWeeks;
    }

    // TODO: Implement toString() function

}
