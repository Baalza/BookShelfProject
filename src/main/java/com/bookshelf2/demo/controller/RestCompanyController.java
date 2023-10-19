package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.Company;
import com.bookshelf2.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class RestCompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("restCompany")
    public List<Company> restCompany(){
        List<Company> list = new ArrayList<>();
        list = companyService.findAll();
        Collections.reverse(list);
        return list;

    }

    @GetMapping("restCompany/byRemote")
    public List<Company> restCompanyByRemote(){
        List<Company> list = new ArrayList<>();
        list = companyService.findAllByRemoteExist(true);
        //Collections.reverse(list);
        return list;

    }
}
