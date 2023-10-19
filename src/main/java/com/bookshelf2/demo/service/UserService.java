package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.User;
//import com.bookshelf2.demo.model.Verificationtoken;
import com.bookshelf2.demo.model.VerificationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface UserService {
    @Transactional
    User save(User user);

    List<String> findAllNotAdmin();

    String findByUsername(String username);

    void createVerificationToken(User user,String username, String token, Date expiryDate);

    VerificationToken getVerificationToken(String VerificationToken);

    User findUser(String username);

    void deleteUserToken(String token);

    @Modifying
    @Transactional
    void deleteAut (long id);





}
