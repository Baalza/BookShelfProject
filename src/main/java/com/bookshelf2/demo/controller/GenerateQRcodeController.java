package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

@Controller
public class GenerateQRcodeController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;
    public static String QR_PREFIX =
            "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    @Value("${spring.application.name}")
    public String APP_NAME;


    public String generateQRUrl(User user) throws UnsupportedEncodingException {

        String SK = user.getSecret().toUpperCase().toString().trim();
        System.out.println(SK);
        return   QR_PREFIX +URLEncoder.encode(String.format(
                        "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                        APP_NAME, user.getUsername(), SK, APP_NAME),
                "UTF-8");
    }

    @GetMapping("qrcode")
    public String getQrcode(Map<String, Object> model) throws UnsupportedEncodingException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String username = authentication.getName();
        User user = userService.findUser(username);
        user.setUsing2FA(true);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);


        userService.save(user);
        String QRurl = generateQRUrl(user);
        model.put("qr",QRurl);
        return "qrcode";
    }
}
