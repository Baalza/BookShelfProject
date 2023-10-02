package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.BookReport;
import com.bookshelf2.demo.model.FileInfo;
import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.service.FileInfoService;
import com.bookshelf2.demo.service.FileService;
import com.bookshelf2.demo.service.UserService;
import com.bookshelf2.demo.util.RcloneCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
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

        if (fileService.checkFile()) {
            List<FileInfo> files = fileService.loadAll().map(path -> {
                String filename = path.getFileName().toString();
                LocalDateTime currentDateTime = LocalDateTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String dateModified = currentDateTime.format(formatter);

                String exstension = "";
                int lastDotIndex = filename.lastIndexOf('.');
                if (lastDotIndex > 0) {
                    exstension = filename.substring(lastDotIndex + 1);
                }
                if(exstension.equals("txt"))
                    exstension="plain";
                if(exstension.equals("css"))
                    exstension="plain";
                if(exstension.equals("js"))
                    exstension="text/javascript";
                if(exstension.equals("html"))
                    exstension="text/html";
                if(exstension.equals("xlsx"))
                    exstension="vnd.openxmlformats-officedocument.spreadsheetml.sheet";


                return new FileInfo(filename, dateModified, exstension);
            }).collect(Collectors.toList());

            model.addAttribute("files", files);
            return "loadFile";
        }else{
            return "loadFile";
        }
    }
    //Upload di un file
    @PostMapping("/loadFile")
    public String addFile(@RequestParam("file") MultipartFile file, Map<String,Object> model) throws IOException {


        if(file.getOriginalFilename().contains(" ")){

            return "redirect:/loadFile?error=invalidFilename";
        }
        //byte[] fileContent = file.getBytes();
        //String nuovoNome = file.getOriginalFilename().trim().replaceAll(" ","_");
        //Path filePath = Paths.get("/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/files", nuovoNome);
        // Salva il nuovo file con il nuovo nome
        //Files.write(filePath, fileContent);
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
        fileService.save(file);
        try {

            fileInfoService.save(fileInfo);
        }catch(Exception e){

            fileService.save(file,path);
            return "redirect:/loadFile?file=exist";
        }
        //System.out.println(root.resolve(file.getOriginalFilename()));
        String path = root.resolve(file.getOriginalFilename()).toString();

        //String path = root.resolve(nuovoNome).toString();
        //System.out.println("nuovo nome "+path);

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
    //sovrascrivi il file
    @GetMapping("overwrite/{ow}")
    public String owFile(@PathVariable(value = "ow") boolean ow) throws IOException {
        System.out.println("overwrite");
        if(!ow){
            fileService.deleteAll(path);
            return "redirect:/loadFile";
        }else{

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
                Files.move(sourceFilePath, root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                fileService.deleteAll(path);
                String command = "rclone copy " + "/Users/baalza/Desktop/demoBookShelf/src/main/webapp/WEB-INF/files/" +filename + " BookShelfRemote:FileBookShelf";
                String result = commandExecutor.getCommandOutput(command);
                System.out.println("COMMAND " + result);
                System.out.println("File spostato con successo.");
            } catch (IOException e) {
                System.err.println("Errore durante lo spostamento del file: " + e.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
         return "redirect:/loadFile";
        }




    }
    //delete di un file
    @GetMapping("deleteFile/{fileName}")
    public String deleteFile(@PathVariable(value = "fileName") String fileName){
            fileInfoService.delete(fileName);
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










