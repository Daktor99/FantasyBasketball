package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    @JsonProperty("client_id")
    private Integer clientID;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @Column(name = "company_name")
    @JsonProperty("company_name")
    private String company_name;

    @Column(name = "client_name")
    @JsonProperty("client_name")
    private String client_name;

    @Column(name = "3p_weight")
    @JsonProperty("3p_weight")
    private Integer three_p_weight;

    @Column(name = "2p_weight")
    @JsonProperty("2p_weight")
    private Integer two_p_weight;

    @Column(name = "ft_weight")
    @JsonProperty("ft_weight")
    private Integer ft_weight;

    @Column(name = "rebound_weight")
    @JsonProperty("rebound_weight")
    private Integer rebound_weight;

    @Column(name = "assist_weight")
    @JsonProperty("assist_weight")
    private Integer assist_weight;

    @Column(name = "block_weight")
    @JsonProperty("block_weight")
    private Integer block_weight;

    @Column(name = "turnover_weight")
    @JsonProperty("turnover_weight")
    private Integer turnover_weight;

    @Column(name = "min_league_dur")
    @JsonProperty("min_league_dur")
    private Integer min_league_dur;

    @Column(name = "team_size")
    @JsonProperty("team_size")
    private Integer team_size;


    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public Integer getThree_p_weight() {
        return three_p_weight;
    }

    public void setThree_p_weight(Integer three_p_weight) {
        this.three_p_weight = three_p_weight;
    }

    public Integer getTwo_p_weight() {
        return two_p_weight;
    }

    public void setTwo_p_weight(Integer two_p_weight) {
        this.two_p_weight = two_p_weight;
    }

    public Integer getFt_weight() {
        return ft_weight;
    }

    public void setFt_weight(Integer ft_weight) {
        this.ft_weight = ft_weight;
    }

    public Integer getRebound_weight() {
        return rebound_weight;
    }

    public void setRebound_weight(Integer rebound_weight) {
        this.rebound_weight = rebound_weight;
    }

    public Integer getAssist_weight() {
        return assist_weight;
    }

    public void setAssist_weight(Integer assist_weight) {
        this.assist_weight = assist_weight;
    }

    public Integer getBlock_weight() {
        return block_weight;
    }

    public void setBlock_weight(Integer block_weight) {
        this.block_weight = block_weight;
    }

    public Integer getTurnover_weight() {
        return turnover_weight;
    }

    public void setTurnover_weight(Integer turnover_weight) {
        this.turnover_weight = turnover_weight;
    }

    public Integer getMin_league_dur() {
        return min_league_dur;
    }

    public void setMin_league_dur(Integer min_league_dur) {
        this.min_league_dur = min_league_dur;
    }

    public Integer getTeam_size() {
        return team_size;
    }

    public void setTeam_size(Integer team_size) {
        this.team_size = team_size;
    }
}



