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

    @Column(name = "three_p_weight")
    @JsonProperty("three_p_weight")
    private double three_p_weight;

    @Column(name = "two_p_weight")
    @JsonProperty("two_p_weight")
    private double two_p_weight;

    @Column(name = "ft_weight")
    @JsonProperty("ft_weight")
    private double ft_weight;

    @Column(name = "rebound_weight")
    @JsonProperty("rebound_weight")
    private double rebound_weight;

    @Column(name = "assist_weight")
    @JsonProperty("assist_weight")
    private double assist_weight;

    @Column(name = "block_weight")
    @JsonProperty("block_weight")
    private double block_weight;

    @Column(name = "turnover_weight")
    @JsonProperty("turnover_weight")
    private double turnover_weight;

    @Column(name = "min_league_dur")
    @JsonProperty("min_league_dur")
    private Integer min_league_dur;

    @Column(name = "max_team_size")
    @JsonProperty("max_team_size")
    private Integer max_team_size;

    @Column(name = "min_league_size")
    @JsonProperty("min_league_size")
    private Integer min_league_size;

    @Column(name = "google_id")
    @JsonProperty("google_id")
    private String google_id;

    @Column(name = "steal_weight")
    @JsonProperty("steal_weight")
    private Integer steal_weight;

    public Client() {
        this.clientID = null;
        this.email = null;
        this.company_name = null;
        this.client_name = null;
        this.three_p_weight = 1.0;
        this.two_p_weight = 1.0;
        this.ft_weight = 1.0;
        this.rebound_weight = 1.0;
        this.assist_weight = 1.0;
        this.block_weight = 1.0;
        this.turnover_weight = 1.0;
        this.min_league_dur = 1;
        this.max_team_size = 30;
        this.min_league_size = 2;
        this.google_id = null;
    }

    public Client(Integer clientID,
                  String email,
                  String company_name,
                  String client_name,
                  Integer three_p_weight,
                  Integer two_p_weight,
                  Integer ft_weight,
                  Integer rebound_weight,
                  Integer assist_weight,
                  Integer block_weight,
                  Integer turnover_weight,
                  Integer min_league_dur,
                  Integer max_team_size,
                  Integer min_league_size,
                  String google_id) {
        this.clientID = clientID;
        this.email = email;
        this.company_name = company_name;
        this.client_name = client_name;
        this.three_p_weight = three_p_weight;
        this.two_p_weight = two_p_weight;
        this.ft_weight = ft_weight;
        this.rebound_weight = rebound_weight;
        this.assist_weight = assist_weight;
        this.block_weight = block_weight;
        this.turnover_weight = turnover_weight;
        this.min_league_dur = min_league_dur;
        this.max_team_size = max_team_size;
        this.min_league_size = min_league_size;
        this.google_id = google_id;
    }


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

    public double getThree_p_weight() {
        return three_p_weight;
    }

    public void setThree_p_weight(double three_p_weight) {
        this.three_p_weight = three_p_weight;
    }

    public double getTwo_p_weight() {
        return two_p_weight;
    }

    public void setTwo_p_weight(double two_p_weight) {
        this.two_p_weight = two_p_weight;
    }

    public double getFt_weight() {
        return ft_weight;
    }

    public void setFt_weight(double ft_weight) {
        this.ft_weight = ft_weight;
    }

    public double getRebound_weight() {
        return rebound_weight;
    }

    public void setRebound_weight(double rebound_weight) {
        this.rebound_weight = rebound_weight;
    }

    public double getAssist_weight() {
        return assist_weight;
    }

    public void setAssist_weight(double assist_weight) {
        this.assist_weight = assist_weight;
    }

    public double getBlock_weight() {
        return block_weight;
    }

    public void setBlock_weight(double block_weight) {
        this.block_weight = block_weight;
    }

    public double getTurnover_weight() {
        return turnover_weight;
    }

    public void setTurnover_weight(double turnover_weight) {
        this.turnover_weight = turnover_weight;
    }

    public Integer getMin_league_dur() {
        return min_league_dur;
    }

    public void setMin_league_dur(Integer min_league_dur) {
        this.min_league_dur = min_league_dur;
    }

    public Integer getMax_team_size() {
        return max_team_size;
    }

    public void setMax_team_size(Integer max_team_size) {
        this.max_team_size = max_team_size;
    }

    public Integer getMin_league_size() {
        return min_league_size;
    }

    public void setMin_league_size(Integer min_league_size) {
        this.min_league_size = min_league_size;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    @Override
    public String toString() {
        return "\nUser { " +
                "\n\t clientID=" + clientID +
                ",\n\t email=" + email +
                ",\n\t company_name='" + company_name + '\'' +
                ",\n\t client_name='" + client_name + '\'' +
                '}';
    }
}



