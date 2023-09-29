package com.bookshelf2.demo.util;

import com.bookshelf2.demo.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnCreateAccountEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;

    public OnCreateAccountEvent(
            User user, Locale locale, String appUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }



    // standard getters and setters


    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
