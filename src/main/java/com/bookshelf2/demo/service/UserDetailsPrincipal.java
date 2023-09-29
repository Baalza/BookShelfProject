package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsPrincipal {


    private UserService userService;

    public User loadByUsername(String username){
        User user = userService.findUser(username);
        return user;
    }
}
