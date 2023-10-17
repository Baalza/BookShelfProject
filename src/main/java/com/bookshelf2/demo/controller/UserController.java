package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.model.VerificationToken;
import com.bookshelf2.demo.repository.UserRepository;
import com.bookshelf2.demo.repository.VerificationTokenRepository;
import com.bookshelf2.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("accountConfirmation")
    public String confirmAccount(@RequestParam("token") String token, Map<String,Object> model, Map<String,Object> type) throws JsonProcessingException {


        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {

            String string = "invalid-token";

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(string);

            System.out.println(json);
            return json;
        }

        User user = verificationToken.getUser();
        System.out.println(user.getId()+"dfvnibvns");
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {

            long userId = user.getId();
            List<Long> ids = new ArrayList<>();
            ids.add(verificationToken.getId());
            verificationTokenRepository.deleteAllByIdInBatch(ids);

            //userService.deleteAut(user.getId());
            userRepository.deleteAut2(userId);

            List<Long> ids2 = new ArrayList<>();
            ids2.add(userId);
            userRepository.deleteAllByIdInBatch(ids2);

            String string = "expiry-token";

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(string);

            System.out.println(json);
            return json;
        }

        user.setEnabled(true);
        userService.save(user);
        //userService.deleteUserToken(token);
        List<Long> ids = new ArrayList<>();
        ids.add(verificationToken.getId());
        verificationTokenRepository.deleteAllByIdInBatch(ids);

        String string = "confirm";

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(string);

        System.out.println(json);
        return json;
    }
}
