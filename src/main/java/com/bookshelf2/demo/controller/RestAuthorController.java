package com.bookshelf2.demo.controller;


import com.bookshelf2.demo.model.Author;
import com.bookshelf2.demo.model.BookReport;
import com.bookshelf2.demo.repository.AuthorRepository;
import com.bookshelf2.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestAuthorController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("restAuthors")
    public List<Author> getAuthors(){
        List<Author> authors = authorRepository.findAll();

        return authors;
    }
    @GetMapping("restBooks-reports")
    public List<BookReport> getBooks(){
        List<BookReport> bookReports = bookRepository.bookReport();

        return bookReports;
    }

    @GetMapping("restBooks-author/{id}")
    public List<BookReport> getAuthorBooks(@PathVariable(value = "id") Long id){
        List<BookReport> books = bookRepository.bookAuthor(id);

        return books;
    }

}
