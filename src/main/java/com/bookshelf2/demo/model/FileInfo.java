package com.bookshelf2.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;


public class FileInfo {

    private String name;
    private String date;

    public FileInfo(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public FileInfo() {

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


}
