package com.bookshelf2.demo.util;

import com.bookshelf2.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.Collection;

public class TwoFactorAuthenticationToken extends UsernamePasswordAuthenticationToken {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    UserService userService;
    private String secretKey;
    private final String otpCode;

    public TwoFactorAuthenticationToken(Object principal, Object credentials, String otpCode, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials,authorities);
        this.otpCode = otpCode;
    }

    public String getOtpCode() {
        return otpCode;
    }
}

