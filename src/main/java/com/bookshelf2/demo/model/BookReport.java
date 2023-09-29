package com.bookshelf2.demo.model;

public class BookReport {

    private Long id;
    private String title;
    private String descrizione;
    private String autore;
    private Long authorId;

    public BookReport(Long id, String title, String descrizione, String autore, Long authorId) {
        this.id = id;
        this.title = title;
        this.descrizione = descrizione;
        this.autore = autore;
        this.authorId = authorId;
    }
    public BookReport(Long id, String title, String descrizione, String autore) {
        this.id = id;
        this.title = title;
        this.descrizione = descrizione;
        this.autore = autore;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
