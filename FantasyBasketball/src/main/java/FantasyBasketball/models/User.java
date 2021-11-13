package FantasyBasketball.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    // Data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "client_id")
    @JsonProperty("client_id")
    private Integer clientID = 1;

    @Column(name = "username")
    @JsonProperty("username")
    private String username;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinTable(name = "user_fantasyLeague",
//            joinColumns = {@JoinColumn(name = "userID",
//                    referencedColumnName = "userID",
//                    nullable = false,
//                    updatable = false)},
//            inverseJoinColumns = {@JoinColumn(name = "leagueID",
//                    referencedColumnName = "leagueID",
//                    nullable = false,
//                    updatable = false)})
//    private Set<User> userLeagues = new HashSet<>();

    // class methods
    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

//    public Set<User> getUserLeagues() {
//        return userLeagues;
//    }
//
//    public void setUserLeagues(Set<User> userLeagues) {
//        this.userLeagues = userLeagues;
//    }


    @Override
    public String toString() {
        return "\nUser { " +
                "\n\t userID=" + userID +
                "\n\t clientID=" + clientID +
                ",\n\t username='" + username + '\'' +
                ",\n\t email='" + email + '\'' +
                ",\n\t firstName='" + firstName + '\'' +
                ",\n\t lastName='" + lastName + '\'' +
                '}';
    }

}
