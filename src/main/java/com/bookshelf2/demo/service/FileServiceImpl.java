package com.bookshelf2.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {
    private final Path root = Paths.get("/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/files");
    @Override
    public void save(MultipartFile file) {
        MultipartFile file2;

        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        try {

            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
            System.out.println(file.getOriginalFilename()+"lalalalal");
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");

            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void save(MultipartFile file, Path path) {
        MultipartFile file2;

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        try {

            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
            System.out.println(file.getOriginalFilename()+"lalalalal");
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");

            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public void deleteAll(Path path) {
        FileSystemUtils.deleteRecursively(path.toFile());
    }

    @Override
    public boolean delete(String fileName) {
        try{
            Path file = root.resolve(fileName);
            return Files.deleteIfExists(file);
        }catch (IOException e){
            throw new RuntimeException("Error. "+e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll(){
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
    @Override
    public Stream<Path> loadAll(Path pathT){
        try {
            return Files.walk(pathT, 1).filter(path -> !path.equals(pathT)).map(pathT::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public boolean checkFolder() {
        return false;
    }

    @Override
    public boolean checkFile() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(root)) {
            boolean hasFiles = false;
            for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    hasFiles = true;
                    break;
                }
            }
           if(hasFiles){
               return true;
           }else{
               return false;
           }
        }catch (IOException e){
            return false;
        }

    }


}
