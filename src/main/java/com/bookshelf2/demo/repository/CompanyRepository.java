package com.bookshelf2.demo.repository;

import com.bookshelf2.demo.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    public List<Company> findAll();

    public Company findAllById(Long id);

    public List<Company> findAllByRemoteExist(Boolean remote);
}
