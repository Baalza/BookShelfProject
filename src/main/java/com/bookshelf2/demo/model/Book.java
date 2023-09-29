package com.bookshelf2.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "book")
@NamedQueries({
        @NamedQuery(name = Book.BOOK_REPORT, query = Book.BOOK_REPORT_JPQL),
        @NamedQuery(name = Book.BOOK_AUTHOR, query = Book.BOOK_AUTHOR_JPQL)
})
public class Book {

    public static final String BOOK_REPORT = "Book.bookReport";

    public static final String BOOK_REPORT_JPQL = "Select new com.bookshelf2.demo.model.BookReport" +
            "(b.id, b.title, b.descrizione, a.name, a.id) " +
            "from Book b, Author a " +
            "where b.authorId.id = a.id";

    public static final String BOOK_AUTHOR = "Book.bookAuthor";

    public static final String BOOK_AUTHOR_JPQL = "Select new com.bookshelf2.demo.model.BookReport" +
            "(b.id, b.title, b.descrizione, a.name)" +
            "from Book b " +
            "join b.authorId  a " +
            "where b.authorId.id = ?1";

    public Book(Long id, String title, String descrizione) {
        this.id = id;
        this.title = title;
        this.descrizione = descrizione;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "descrizione")
    private String descrizione;

    @JsonBackReference
    @ManyToOne
    private Author authorId;

    public Book() {

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

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author author) {
        this.authorId = author;
    }
}
