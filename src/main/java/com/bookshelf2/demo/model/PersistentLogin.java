package com.bookshelf2.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unboundid.util.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "persistent_logins")
public class PersistentLogin {

    @Id
    @Column(name = "series")
    private String series;

    @Column(name = "username")
    @NotNull
    private String username;

    @NotNull
    @Column(name = "token")
    private String token ;

    @NotNull
    @Column(name = "last_used")
    private Date lastUsed;



    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    private User userToken;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    /*public User getUser() {
        return userToken;
    }

    public void setUser(User user) {
        this.userToken = user;
    }*/
}
