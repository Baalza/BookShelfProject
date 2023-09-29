package com.bookshelf2.demo.service;


import com.bookshelf2.demo.model.Book;
import com.bookshelf2.demo.model.BookReport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {
    @Transactional
    Book save(Book book);

    List<Book> findAll();

    List<BookReport> findAllBookAuthor();

    List<BookReport> findAllBookAuthorId(Long id);
}
