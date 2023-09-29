package com.bookshelf2.demo.util;

import com.bookshelf2.demo.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TwoFactorAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private UserService userService;
    private String defaultUrl;
    public TwoFactorAuthenticationFilter(String defaultUrl, UserService userService) {

        super(new AntPathRequestMatcher(defaultUrl, "POST"));
        this.userService = userService;

    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // Acquisisci il codice OTP dalla richiesta HTTP
        String otpCode = request.getParameter("otpCode");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        System.out.println("AUTENTICAZIONEEE"+username);

        // Crea un'istanza di TwoFactorAuthenticationToken contenente il codice OTP

        TwoFactorAuthenticationToken authRequest = new TwoFactorAuthenticationToken(authentication.getPrincipal(),authentication.getCredentials(),otpCode, authentication.getAuthorities());
        System.out.println("AUTENTICAZIONEEE"+authRequest.getOtpCode());
        // Invia la richiesta al gestore delle autenticazioni
        AuthenticationProvider authenticationManager = new TwoFactorAuthenticationProvider(userService);
        //System.out.println("AUTENTICAZIONEEE"+authenticationManager);
        Authentication auth = authenticationManager.authenticate(authRequest);
        System.out.println("AUTENTICAZIONEEE"+auth);

        return auth;
    }
}

