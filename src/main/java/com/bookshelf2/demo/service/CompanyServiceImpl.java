package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.Company;
import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    CompanyRepository companyRepository;
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findAllById(id);
    }

    @Override
    public List<Company> findAllByRemoteExist(Boolean remote) {
        return companyRepository.findAllByRemoteExist(remote);
    }
}
