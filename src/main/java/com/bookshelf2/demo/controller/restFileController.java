package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.FileInfo;
import com.bookshelf2.demo.service.FileInfoService;
import com.bookshelf2.demo.util.RcloneCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class restFileController {

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private RcloneCommandExecutor commandExecutor;

    @GetMapping("restFiles")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<FileInfo> restFiles() {
        List<FileInfo> files = fileInfoService.findAllFile();
        Collections.reverse(files);
        return files;
    }

    @GetMapping("restFiles-drive")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<FileInfo> restFilesDrive() throws IOException, InterruptedException {
        List <FileInfo> fileList = new ArrayList<>();
        String command = "rclone lsjson BookShelfRemote:FileBookShelf";
        String result = commandExecutor.getCommandOutput(command);
        //String command =  "rclone lsl BookShelfRemote:FileBookShelf";
        //String result = commandExecutor.getCommandOutput(command);
        JsonArray jsonArray = new Gson().fromJson(result, JsonArray.class);

        for (int i = 0; i < jsonArray.size(); i++) {

            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            String name = jsonObject.get("Name").getAsString();
            String modTime = jsonObject.get("ModTime").getAsString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String cleanedDateString = modTime.substring(0, modTime.indexOf('.'));
            cleanedDateString = cleanedDateString.replace("T"," ");
            LocalDateTime localDateTime = LocalDateTime.parse(cleanedDateString, formatter);
            Duration duration = Duration.ofHours(2);
            LocalDateTime newDateTime = localDateTime.plus(duration);
            String dateModified = newDateTime.format(formatter);
            FileInfo file = new FileInfo(name,dateModified);

            fileList.add(file);
            System.out.println("Name: " + name);
            System.out.println("ModTime: " + dateModified);
            System.out.println("Formatted Date: "+dateModified);
        }
        return fileList;
    }
}
