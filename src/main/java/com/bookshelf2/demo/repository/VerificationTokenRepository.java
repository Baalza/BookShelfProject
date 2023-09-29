package com.bookshelf2.demo.repository;

import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository  extends JpaRepository<VerificationToken, Long> {

        VerificationToken findByToken(String token);






}
