package com.bookshelf2.demo.controller;


import com.bookshelf2.demo.repository.UserRepository;
import com.bookshelf2.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String home(Map<String,Object> model){

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println(authentication);
        Object principal = authentication.getPrincipal();

        System.out.println("principal"+principal);
        if(principal == "anonymousUser"){
            System.out.println("vuoto");
            model.put("role",authentication.getAuthorities().toString());
        }else {
            String username = authentication.getName();
            System.out.println(username);
            String nickname = userService.findByUsername(username);
            System.out.println(nickname);
            model.put("nickname",nickname);
            model.put("role",authentication.getAuthorities().toString());
        }

        return "index";
    }
}
