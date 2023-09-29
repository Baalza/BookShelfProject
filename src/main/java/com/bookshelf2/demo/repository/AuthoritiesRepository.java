package com.bookshelf2.demo.repository;

import com.bookshelf2.demo.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
}
