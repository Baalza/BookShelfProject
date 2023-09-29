package com.bookshelf2.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


public interface FileService {

    public void save(MultipartFile file);

    public void deleteAll();

    public boolean delete(String fileName);

    public Stream<Path> loadAll();

    public boolean checkFolder();

    public boolean checkFile() throws IOException;



}
