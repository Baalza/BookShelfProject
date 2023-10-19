package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ResUser {

    @Autowired
    UserService userService;

    @GetMapping("restUser")
    @CrossOrigin("http://localhost:4200")
    public String restUser() throws JsonProcessingException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(email);

        System.out.println(json);

        return json;
    }
    @GetMapping("restUser/not-admin")
    @CrossOrigin("http://localhost:4200")
    public List<User> restUserNotAdmin() {
        List<String> list = new ArrayList<>();
        List<User> lst = new ArrayList<>();
        list = userService.findAllNotAdmin();
        for(int i = 0; i<list.size(); i++){
            String username = list.get(i);
            lst.add(userService.findUser(username));
        }
        return lst;
    }
}
