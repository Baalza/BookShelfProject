package com.bookshelf2.demo.service;

import com.bookshelf2.demo.model.FileInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


public interface FileService {

    public void save(MultipartFile file);

    public void save(MultipartFile file, Path path);

    public void deleteAll();

    public void deleteAll(Path path);

    public boolean delete(String fileName);


    public Stream<Path> loadAll();

    public Stream<Path> loadAll(Path path);
    public boolean checkFolder();

    public boolean checkFile() throws IOException;



}
