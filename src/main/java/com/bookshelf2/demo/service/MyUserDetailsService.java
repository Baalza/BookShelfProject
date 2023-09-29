package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.Authorities;
import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    public MyUserDetailsService() {
        super();
    }

    // API
    private Collection<GrantedAuthority> authorities = new  ArrayList<>();


    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {


        try {
            final User user = userRepository.findByUsername(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }
            System.out.println("AAAAAAA"+user.getUsername()+ getAuthorities());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
    // UTIL


}
