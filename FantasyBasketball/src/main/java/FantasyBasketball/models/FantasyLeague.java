package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fantasy_league")
public class FantasyLeague {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("league_id")
    @Column(name = "league_id")
    private Integer leagueID;

    @JsonProperty("client_id")
    @Column(name = "client_id")
    private Integer clientID;

    @JsonProperty("league_name")
    @Column(name = "league_name")
    private String leagueName;

    @JsonProperty("admin_id")
    @Column(name = "admin_id")
    private Integer adminID;

    @JsonProperty("league_size")
    @Column(name = "league_size")
    private Integer leagueSize;

    @JsonProperty("league_start_date")
    @Column(name = "league_start_date")
    private LocalDate leagueStartDate;

    @JsonProperty("league_end_date")
    @Column(name = "league_end_date")
    private LocalDate leagueEndDate;

//    @ManyToMany(mappedBy = "fantasyLeague", fetch = FetchType.LAZY)
//    private Set<User> participants = new HashSet<>();

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

    public LocalDate getLeagueStartDate() {
        return leagueStartDate;
    }

    public void setLeagueStartDate(LocalDate leagueStartDate) {
        this.leagueStartDate = leagueStartDate;
    }

    public LocalDate getLeagueEndDate() {
        return leagueEndDate;
    }

    public void setLeagueEndDate(LocalDate leagueEndDate) {
        this.leagueEndDate = leagueEndDate;
    }

//    public Set<User> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(Set<User> participants) {
//        this.participants = participants;
//    }

    // TODO: do i need to do @Override public String toString() {}???


    @Override
    public String toString() {
        return "FantasyLeague {" +
                "\n\tleagueID = " + leagueID +
                ",\n\tclientID = " + clientID +
                ",\n\tleagueName = '" + leagueName + '\'' +
                ",\n\tadminID = " + adminID +
                ",\n\tleagueSize = " + leagueSize +
                ",\n\tleagueStartDate = " + leagueStartDate +
                ",\n\tleagueEndDate = " + leagueEndDate +
                "\n\t}";
    }
}
