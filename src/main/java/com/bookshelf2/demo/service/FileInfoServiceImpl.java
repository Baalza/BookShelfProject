package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.FileInfo;
import com.bookshelf2.demo.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileInfoServiceImpl implements  FileInfoService {

    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Override
    public void save(FileInfo fileInfo) {
        fileInfoRepository.save(fileInfo);
    }

    @Override
    public void delete(String name) {
        fileInfoRepository.deleteByName(name);
    }

    @Override
    public FileInfo findFile(String filename) {
        return fileInfoRepository.findByName(filename);
    }

    @Override
    public List<FileInfo> findAllFile() {
        return fileInfoRepository.findAll();
    }
}
