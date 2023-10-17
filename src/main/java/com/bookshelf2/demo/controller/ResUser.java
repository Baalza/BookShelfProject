package com.bookshelf2.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResUser {

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
}
