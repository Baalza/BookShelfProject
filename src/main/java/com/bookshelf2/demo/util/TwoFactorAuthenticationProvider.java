package com.bookshelf2.demo.util;

import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TwoFactorAuthenticationProvider implements AuthenticationProvider {





private UserService userService;


    public TwoFactorAuthenticationProvider(UserService userService) {
        this.userService=userService;

    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //String otpCode = (String) authentication.getCredentials();


        if (!(authentication instanceof TwoFactorAuthenticationToken)) {
            System.out.println("non è istanza");
            return null;
        }


        TwoFactorAuthenticationToken twoFactorAuthentication = (TwoFactorAuthenticationToken) authentication;
        String otpCode = twoFactorAuthentication.getOtpCode();


        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        String username = auth.getName();
        //System.out.println("provider"+userDetailsPrincipal.loadByUsername(username).getUsername());
        System.out.println("TOTP "+otpCode);
        // Implementa la logica per verificare il codice OTP
//        System.out.println(userService.findByUsername(username));
        User user = userService.findUser(username);
        String SK = user.getSecret().toString().toUpperCase().trim();
        boolean isOtpValid = validateOtpCode(otpCode);
        Totp totp = new Totp(SK);
        System.out.println("SK: " + SK + " " + totp.verify(otpCode));
        if (isOtpValid && totp.verify(otpCode)) {
            System.out.println("Codice valido");
            // Restituisci un'istanza di Authentication riuscita
            return authentication;
        } else {
            System.out.println("Codice non valido");
            authentication.setAuthenticated(false);
            return authentication;

        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TwoFactorAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean validateOtpCode(String otpCode) {
        try {
            Long.parseLong(otpCode);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

