package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.User;
//import com.bookshelf2.demo.model.Verificationtoken;
import com.bookshelf2.demo.model.VerificationToken;
import com.bookshelf2.demo.repository.UserRepository;
import com.bookshelf2.demo.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;


    @Autowired
    private DataSource dataSource;



    @Override
    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public List<String> findAllNotAdmin() {
        return userRepository.findAllNotAdmin();
    }

    @Override
    public String findByUsername(String username) {
        return userRepository.userReport(username);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public User findUser(String username) {
        System.out.println("sono nello user service "+username);
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUserToken(String token) {
        //tokenRepository.deleteByToken(token);
    }

    @Override
    public void deleteAut(long id) {
        userRepository.deleteAut(id);
    }

    @Override
    public void createVerificationToken(User user,String username, String token, Date expiryDate) {
        VerificationToken myToken = new VerificationToken(user, user.getUsername(), token, expiryDate);
        tokenRepository.save(myToken);
    }






}
