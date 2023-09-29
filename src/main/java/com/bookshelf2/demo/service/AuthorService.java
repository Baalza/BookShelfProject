package com.bookshelf2.demo.service;


import com.bookshelf2.demo.model.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);

    List<Author> findAll();


}
