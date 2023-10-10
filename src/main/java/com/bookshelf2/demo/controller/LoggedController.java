package com.bookshelf2.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggedController {

    @GetMapping("/isLoggedIn")
    public String isLoggedIn() throws JsonProcessingException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        System.out.println(authentication.isAuthenticated());
        System.out.println(context);
        System.out.println(principal);
        String string = "file-manager-ow";
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(string);
        return json;
    }
}
