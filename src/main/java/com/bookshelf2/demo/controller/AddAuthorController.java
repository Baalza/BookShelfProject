package com.bookshelf2.demo.controller;


import com.bookshelf2.demo.model.Author;
import com.bookshelf2.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class AddAuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("addAuthors")
    public String authors(@ModelAttribute("addAuthor") Author author){
        return "addAuthors";
    }


    @PostMapping("addAuthors" )
    public String addauthors(@ModelAttribute ("addAuthor") Author author){
        String name = author.getName();

        System.out.println(name);

        authorService.save(author);
        return "redirect:addAuthors";
    }


}
