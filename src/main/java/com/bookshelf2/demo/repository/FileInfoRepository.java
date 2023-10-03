package com.bookshelf2.demo.repository;

import com.bookshelf2.demo.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    public void deleteByName(String name);

    public FileInfo findByName(String filename);

    public List<FileInfo> findAll();
}
