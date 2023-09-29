package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.Authorities;
import com.bookshelf2.demo.repository.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Override
    @Transactional
    public Authorities save(Authorities authorities){
        return authoritiesRepository.save(authorities);
    }
}
