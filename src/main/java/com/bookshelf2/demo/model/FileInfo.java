package com.bookshelf2.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="file")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String date;

    private String exstension;

    private String url;

    public FileInfo(String name, String date, String exstension) {
        this.name = name;
        this.date = date;
        this.exstension = exstension;
    }

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public FileInfo() {

    }

    public FileInfo(String filename, String dateModified) {
        this.date = dateModified;
        this.name = filename;
    }



    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExstension() {
        return exstension;
    }

    public void setExstension(String exstension) {
        this.exstension = exstension;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
