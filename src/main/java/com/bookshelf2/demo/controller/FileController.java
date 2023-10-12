package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.BookReport;
import com.bookshelf2.demo.model.FileInfo;
import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.service.FileInfoService;
import com.bookshelf2.demo.service.FileService;
import com.bookshelf2.demo.service.UserService;
import com.bookshelf2.demo.util.RcloneCommandExecutor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class FileController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private RcloneCommandExecutor commandExecutor;

    @Autowired
    private FileService fileService;

    private final Path root = Paths.get("/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/files");
    private final Path path = Paths.get("/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/temp");

    //Carica la pagina con i file
    @GetMapping("/loadFile")
    public String getLoadFile(Model model, HttpServletRequest request) throws IOException {
        List<FileInfo> fileList;
        fileList = fileInfoService.findAllFile();



        model.addAttribute("files", fileList);
        return "loadFile";

    }

    //Upload di un file

    @PostMapping("/loadFile")
    @CrossOrigin(origins = "http://localhost:4200",methods = RequestMethod.POST)
    public String addFile(@RequestParam(value = "file",required = false) MultipartFile file, Map<String, Object> model) throws IOException {


        if (file.getOriginalFilename().contains(" ")) {
            String string = "file-manager-invalid";
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(string);
            return json;
        }
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        User user = userService.findUser(username);
        //System.out.println(file);
        String filename = file.getOriginalFilename().toString();
        String exstension = "";
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0) {
            exstension = filename.substring(lastDotIndex + 1);
        }
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateModified = currentDateTime.format(formatter);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(root.toString());
        fileInfo.setName(filename);
        fileInfo.setDate(dateModified);
        fileInfo.setExstension(exstension);
        fileInfo.setUser(user);

        try {
            fileService.save(file);
            fileInfoService.save(fileInfo);
        } catch (Exception e) {

            fileService.save(file, path);
            String string = "file-manager-ow";
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(string);
            return json;
        }

        String string = "file-manager";

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(string);

        System.out.println(json);
        return json;
    }

    //sovrascrivi il file
    @GetMapping("overwrite/{ow}")
    public String owFile(@PathVariable(value = "ow") boolean ow) throws IOException {
        System.out.println("overwrite");
        if (!ow) {
            fileService.deleteAll(path);
            String string = "file-manager";

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(string);

            System.out.println(json);
            return json;
        } else {

            File tempFile = new File("/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/temp");
            File[] files = tempFile.listFiles();
            String filename = files[0].getName();
            fileService.delete(filename);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateModified = currentDateTime.format(formatter);

            FileInfo fileInfo = fileInfoService.findFile(filename);
            fileInfo.setDate(dateModified);
            fileInfoService.save(fileInfo);
            Path sourceFilePath = Path.of("/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/temp", filename);

            try {
                // Sposta il file nella directory di destinazione
                Files.copy(sourceFilePath, root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                fileService.deleteAll(path);
                /*String command = "rclone copy " + "/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/files/" + filename + " BookShelfRemote:FileBookShelf";
                String result = commandExecutor.getCommandOutput(command);
                System.out.println("COMMAND " + result);*/
                System.out.println("File spostato con successo.");
            } catch (IOException e) {
                System.err.println("Errore durante lo spostamento del file: " + e.getMessage());
            }
            String string = "file-manager-ow-true";

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(string);

            System.out.println(json);
            return json;
        }


    }

    //delete di un file
    @GetMapping("deleteFile/{fileName}")
    public String deleteFile(@PathVariable(value = "fileName",required = false) String fileName) throws JsonProcessingException {
        //if(fileName != null) {
        System.out.println(fileName);
            fileInfoService.delete(fileName);
            fileService.delete(fileName);
        /*try {
            String command = "rclone delete BookShelfRemote:FileBookShelf/" + fileName;
            String result = commandExecutor.getCommandOutput(command);
            System.out.println("COMMAND " + result);
            fileService.delete(fileName);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/
        String string = "file-manager";

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(string);

        System.out.println(json);
        return json;
        //}else{
           // return "redirect:/loadFile?delete=true";
        //}


    }


    @GetMapping("/execute")
    public String executeCommand(Model model) {
        try {
            String command = "rclone lsd BookShelfRemote:";
            String result = commandExecutor.getCommandOutput(command);
            System.out.println("COMMAND " + result);
            model.addAttribute("output", result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            model.addAttribute("error", "Errore durante l'esecuzione del comando.");
        }

        return "index";
    }

    @GetMapping("bisync")
    public String doBisync(@RequestParam(name = "ow",required = false) boolean ow,@RequestParam(name = "add",required = false) boolean add,@RequestParam(name = "delete",required = false) boolean delete) throws JsonProcessingException {
        System.out.println("parametro "+ow+add+delete);
        if(!ow && !add && !delete){
            List<FileInfo> list = fileInfoService.findAllFile();
            if(!list.isEmpty()) {
                try {
                    String command = "rclone bisync " + root + " BookShelfRemote:FileBookShelf --delete-before --force --verbose";
                    String result = commandExecutor.getCommandOutput(command);
                    System.out.println("COMMAND " + result);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    String command = "rclone bisync " + root + " BookShelfRemote:FileBookShelf --resync --verbose";
                    String result = commandExecutor.getCommandOutput(command);
                    System.out.println("COMMAND " + result);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    String command = "rclone delete BookShelfRemote:FileBookShelf --verbose";
                    String result = commandExecutor.getCommandOutput(command);
                    System.out.println("COMMAND " + result);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else if(ow || add){
            try {
                String command = "rclone bisync /Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/files BookShelfRemote:FileBookShelf --resync --verbose";
                String result = commandExecutor.getCommandOutput(command);
                System.out.println("COMMAND " + result);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }else if(delete){
            List<FileInfo> list = fileInfoService.findAllFile();
            if(!list.isEmpty()) {
                try {
                    String command = "rclone bisync " + root + " BookShelfRemote:FileBookShelf --delete-before --force --verbose";
                    String result = commandExecutor.getCommandOutput(command);
                    System.out.println("COMMAND " + result);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    String command = "rclone delete BookShelfRemote:FileBookShelf --verbose";
                    String result = commandExecutor.getCommandOutput(command);
                    System.out.println("COMMAND " + result);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        String string = "file-manager";

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(string);

        System.out.println(json);
        return json;
    }
}












