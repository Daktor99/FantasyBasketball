package FantasyBasketball.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    // Data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userID;

    @Column(name = "username")
    private int username;

    @Column(name = "email")
    private String email;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_fantasyLeague",
            joinColumns = {@JoinColumn(name = "userID",
                    referencedColumnName = "userID",
                    nullable = false,
                    updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "leagueID",
                    referencedColumnName = "leagueID",
                    nullable = false,
                    updatable = false)})
    private Set<User> userLeagues = new HashSet<>();

    // class methods
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
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

    public Set<User> getUserLeagues() {
        return userLeagues;
    }

    public void setUserLeagues(Set<User> userLeagues) {
        this.userLeagues = userLeagues;
    }
}
