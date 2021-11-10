package FantasyBasketball.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fantasyLeague")
public class FantasyLeague {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "league_id")
    private int leagueID;

    @Column(name = "league_name")
    private String leagueName;

    @Column(name = "admin_id")
    private int adminID;

    @Column(name = "league_size")
    private int leagueSize;

    @Column(name = "league_start_date")
    private Date leagueStartDate;

    @Column(name = "league_end_date")
    private Date leagueEndDate;

//    @ManyToMany(mappedBy = "fantasyLeague", fetch = FetchType.LAZY)
//    private Set<User> participants = new HashSet<>();

    // class methods
    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getLeagueSize() {
        return leagueSize;
    }

    public void setLeagueSize(int leagueSize) {
        this.leagueSize = leagueSize;
    }

    public Date getLeagueStartDate() {
        return leagueStartDate;
    }

    public void setLeagueStartDate(Date leagueStartDate) {
        this.leagueStartDate = leagueStartDate;
    }

    public Date getLeagueEndDate() {
        return leagueEndDate;
    }

    public void setLeagueEndDate(Date leagueEndDate) {
        this.leagueEndDate = leagueEndDate;
    }

//    public Set<User> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(Set<User> participants) {
//        this.participants = participants;
//    }

}
