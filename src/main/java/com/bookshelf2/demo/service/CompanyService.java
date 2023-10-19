package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.Company;
import com.bookshelf2.demo.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompanyService {

     List<Company> findAll();

    @Transactional
    Company save(Company company);

    Company findById(Long id);

    List<Company> findAllByRemoteExist(Boolean remote);
}
