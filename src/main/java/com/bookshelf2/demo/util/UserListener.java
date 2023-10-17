package com.bookshelf2.demo.util;

import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.model.VerificationToken;
import com.bookshelf2.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class UserListener implements ApplicationListener<OnCreateAccountEvent> {
    private String serverUrl = "http://localhost:8080/";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private UserService userService;


    @Override
    public void onApplicationEvent(OnCreateAccountEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnCreateAccountEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        Date expiryDate;
        expiryDate = verificationToken.calculateExpiryDate(verificationToken.EXPIRATION);
        System.out.println("EXPIRATON "+expiryDate);



        service.createVerificationToken(user, user.getUsername(), token, expiryDate);

        String recipientAddress = user.getUsername();
        String subject = "Registration Confirmation BookShelf";
        String confirmationUrl
                = event.getAppUrl() + "/account-confirmation?token=" + token;
        String message = "Please confirm:";
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(recipientAddress);
        email.setSubject(subject);
        String htmlContent = message + "http://localhost:4200/" + confirmationUrl + "";
        email.setText(htmlContent);
        mailSender.send(email);
    }
}
