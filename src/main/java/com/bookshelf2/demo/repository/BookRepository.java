package com.bookshelf2.demo.repository;


import com.bookshelf2.demo.model.Book;
import com.bookshelf2.demo.model.BookReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    List<BookReport> bookReport();

    List<BookReport> bookAuthor(Long id);

}
