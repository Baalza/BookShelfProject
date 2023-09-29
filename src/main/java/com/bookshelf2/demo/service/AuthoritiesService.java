package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.Authorities;
import org.springframework.transaction.annotation.Transactional;

public interface AuthoritiesService {
    @Transactional
    Authorities save(Authorities authorities);
}
