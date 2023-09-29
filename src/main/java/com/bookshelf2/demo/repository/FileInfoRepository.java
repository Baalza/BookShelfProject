package com.bookshelf2.demo.repository;

import com.bookshelf2.demo.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    public void deleteByName(String name);
}
