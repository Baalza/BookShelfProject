package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.FileInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileInfoService {

    public void save(FileInfo fileInfo);
    @Modifying
    @Transactional
    public void delete(String name);

    public FileInfo findFile(String filename);

    public List<FileInfo> findAllFile();
}
