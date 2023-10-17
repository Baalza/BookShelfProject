package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.Authorities;
import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.service.*;
import com.bookshelf2.demo.util.OnCreateAccountEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
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
    public String user(){
        return "registrazione";
    }


    @PostMapping("registration" )
    public String addUser(@Valid @RequestPart("username") String username,
                          @Valid @RequestPart("password") String password,
                          @Valid @RequestPart("rePassword") String rePassword,
                          BindingResult result, Map<String,Object> model,
                          HttpServletRequest request) throws Exception {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setMatchingPassword(rePassword);
        String pass = user.getPassword();
        String mPass = user.getMatchingPassword();


        if(result.hasErrors()){
            String string = "no-blank";

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(string);

            System.out.println(json);
            return json;
        }
        if(pass.length() < 8 && !pass.matches(".*[A-Z].*")) {
            //model.put("invalidEmail", "La password deve essere lunga almeno 8 caratteri e contenere una lettera maiuscola");
            String string = "invalid-password";

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(string);

            System.out.println(json);
            return json;
        }
        else if(!pass.equals(mPass)) {
            //model.put("invalidEmail","Password mismatch");
            String string = "password-mismatch";

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(string);

            System.out.println(json);
            return json;
        }else{
            try{
                InternetAddress email = new InternetAddress(username);
                email.validate();
            }catch(AddressException e){
                //model.put("invalidEmail","Format email not correct");
                String string = "invalid-email";

                ObjectMapper objectMapper = new ObjectMapper();

                String json = objectMapper.writeValueAsString(string);

                System.out.println(json);
                return json;
            }
            Authorities authorities = new Authorities(username, "ROLE_USER");
            //user.getAuthorities().add(authorities); //unidirectional con joincolumn in user
            user.addAuthorities(authorities); //bidirectional
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            try {

                userService.save(user);
            }catch (DataIntegrityViolationException e){
                System.out.println(e.getMessage());
                if(e.getMostSpecificCause().getMessage().contains("@")){
                    //model.put("duplicateMail","Email already exist");
                    String string = "duplicate-email";

                    ObjectMapper objectMapper = new ObjectMapper();

                    String json = objectMapper.writeValueAsString(string);

                    System.out.println(json);
                    return json;
                }

            }
            //Authorities authorities = new Authorities(username,"ROLE_USER",user);//soluzione base ma non corretta
            //authoritiesService.save(authorities);
        }
        applicationEventPublisher.publishEvent(new OnCreateAccountEvent(user,request.getLocale(),"demo"));

        String string = "registered";

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(string);

        System.out.println(json);
        return json;
    }
}
