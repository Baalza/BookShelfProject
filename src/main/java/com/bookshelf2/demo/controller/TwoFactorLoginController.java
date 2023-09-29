package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TwoFactorLoginController {

    @Autowired
    UserService userService;
    @GetMapping("/2fa-login")
    public String get2faLogin(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        User user = userService.findUser(username);
        if(!user.getUsing2FA()){
            return "redirect:/";
        }else{
            return "2fa-login";
        }
    }

    @PostMapping("/2fa-login")
    public String verify2FA(@RequestParam("otpCode") String otpCode) {
        System.out.println(otpCode);
       return "redirect:/";
    }
}
