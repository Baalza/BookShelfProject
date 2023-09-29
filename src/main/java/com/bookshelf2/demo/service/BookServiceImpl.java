package com.bookshelf2.demo.service;


import com.bookshelf2.demo.model.Book;
import com.bookshelf2.demo.model.BookReport;
import com.bookshelf2.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    @Transactional
    public Book save(Book book){
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookReport> findAllBookAuthor() {
        return bookRepository.bookReport();
    }

    @Override
    public List<BookReport> findAllBookAuthorId(Long id) {
        return bookRepository.bookAuthor(id);
    }

}
