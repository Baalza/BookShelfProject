package com.bookshelf2.demo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {

    public Company(Long id, String name, String tel, String email, String pIva, String url) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.pIva = pIva;
        this.url = url;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "name cannot be empty.")
    @Column(name = "name")
    private String name;

    @Column(unique = true,name = "pIva")
    private String pIva;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "url")
    private String url;

    private Boolean remoteExist = false;

    public Company() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpIva() {
        return pIva;
    }

    public void setpIva(String pIva) {
        this.pIva = pIva;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getRemoteExist() {
        return remoteExist;
    }

    public void setRemoteExist(Boolean remoteExist) {
        this.remoteExist = remoteExist;
    }
}
