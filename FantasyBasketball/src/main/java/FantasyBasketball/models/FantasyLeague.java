package FantasyBasketball.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FantasyLeague {

    // data members
    private int leagueID;
    private String leagueName;
    private int adminID;
    private int leagueSize;
    private Date leagueStartDate;
    private Date leagueEndDate;
    private Set<User> participants = new HashSet<>();

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

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

}
