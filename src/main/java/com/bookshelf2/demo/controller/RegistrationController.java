package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.Authorities;
import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.service.*;
import com.bookshelf2.demo.util.OnCreateAccountEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("registration")
    public String user(@ModelAttribute("addUser") User user){
        return "registrazione";
    }


    @PostMapping("registration" )
    public String addUser(@Valid @ModelAttribute ("addUser") User user, BindingResult result, Map<String,Object> model,
                          HttpServletRequest request, @RequestParam(value="using2FA",required = false) Boolean using2FA) throws Exception {
        String pass = user.getPassword();
        String mPass = user.getMatchingPassword();


        if(result.hasErrors()){
            System.out.println("Errore");
            //redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addUser", result);
            //redirectAttributes.addFlashAttribute("addUser", user);
            return "registrazione";
        }
        else if(!pass.equals(mPass)) {
            model.put("invalidEmail","Password mismatch");
            return "registrazione";
        }else{
            String username = user.getUsername();
            try{
                InternetAddress email = new InternetAddress(username);
                email.validate();
            }catch(AddressException e){
                model.put("invalidEmail","Format email not correct");
                return "registrazione";
            }
            Authorities authorities = new Authorities(username, "ROLE_USER");
            //user.getAuthorities().add(authorities); //unidirectional con joincolumn in user
            user.addAuthorities(authorities); //bidirectional
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if(using2FA != null && using2FA){
                user.setUsing2FA(true);
            }
            try {

                userService.save(user);
            }catch (DataIntegrityViolationException e){
                System.out.println(e.getMessage());
                if(e.getMostSpecificCause().getMessage().contains("@"))
                    model.put("duplicateMail","Email already exist");
                else{
                    model.put("duplicateMail","Nickname already exist");
                }
                return "registrazione";
            }
            //Authorities authorities = new Authorities(username,"ROLE_USER",user);//soluzione base ma non corretta
            //authoritiesService.save(authorities);
        }
        applicationEventPublisher.publishEvent(new OnCreateAccountEvent(user,request.getLocale(),"demo"));

        return "registrazione";
    }
}
