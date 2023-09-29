package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.BookReport;
import com.bookshelf2.demo.model.FileInfo;
import com.bookshelf2.demo.service.FileService;
import com.bookshelf2.demo.service.UserService;
import com.bookshelf2.demo.util.RcloneCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileController {

    @Autowired
    private UserService userService;

    @Autowired
    private RcloneCommandExecutor commandExecutor;

    @Autowired
    private FileService fileService;

    private final Path root = Paths.get("/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/files");

    //Carica la pagina con i file
    @GetMapping("/loadFile")
    public String getLoadFile(Model model) throws IOException {
        if (fileService.checkFile()) {
            List<FileInfo> files = fileService.loadAll().map(path -> {
                String filename = path.getFileName().toString();
                LocalDateTime currentDateTime = LocalDateTime.now();

                // Define a date-time format (optional)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Format and print the current date and time
                String dateModified = currentDateTime.format(formatter);


                return new FileInfo(filename, dateModified);
            }).collect(Collectors.toList());

            model.addAttribute("files", files);
            return "loadFile";
        }else{
            return "loadFile";
        }
    }
    //Upload di un file
    @PostMapping("/loadFile")
    public String addFile(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        String nuovoNome = file.getOriginalFilename().trim().replaceAll(" ","_");
        Path filePath = Paths.get("/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/files", nuovoNome);
        // Salva il nuovo file con il nuovo nome
        Files.write(filePath, fileContent);

        //System.out.println(file);
        //fileService.save(file);
        //System.out.println(root.resolve(file.getOriginalFilename()));
        //String path = root.resolve(file.getOriginalFilename()).toString();

        String path = root.resolve(nuovoNome).toString();
        System.out.println("nuovo nome "+path);

        try {
            String command = "rclone copy " + path + " BookShelfRemote:FileBookShelf";
            String result = commandExecutor.getCommandOutput(command);
            System.out.println("COMMAND " + result);
            //fileService.deleteAll();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        return "redirect:/loadFile";
    }
    //delete di un file
    @GetMapping("deleteFile/{fileName}")
    public String deleteFile(@PathVariable(value = "fileName") String fileName){

        try {
            String command = "rclone delete BookShelfRemote:FileBookShelf/" + fileName;
            String result = commandExecutor.getCommandOutput(command);
            System.out.println("COMMAND " + result);
            fileService.delete(fileName);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return "redirect:/loadFile";
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
}










