package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.FileInfo;
import com.bookshelf2.demo.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class restFileController {

    @Autowired
    private FileInfoService fileInfoService;

    @GetMapping("restFiles")
    public List<FileInfo> restFiles(){
        List<FileInfo> files = fileInfoService.findAllFile();
        return files;
    }
}
