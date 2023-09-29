package com.bookshelf2.demo.controller;


import com.bookshelf2.demo.model.Author;
import com.bookshelf2.demo.model.Book;
import com.bookshelf2.demo.service.BookService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AddBookController {

    @Autowired
    BookService bookService;

    @GetMapping("addBooks")
    public String books(@ModelAttribute("addBook") Book book, Model model){

        List<Author> authors = new ArrayList<>();
        String grid = WebClient.create()
                .get()
                .uri("http://localhost:8080/demo/restAuthors")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonArray data = new Gson().fromJson(grid.trim(), JsonArray.class);
        for (int i = 0; i<data.size(); i++){
            JsonObject dataObj = data.get(i).getAsJsonObject();
            String name = dataObj.get("name").getAsString();
            Long id = dataObj.get("id").getAsLong();
            Author author = new Author(id,name);
            authors.add(author);
            //System.out.println("Id: "+author.getId()+" name: "+author.getName());

        }

        model.addAttribute("authors", authors);


        return "addBooks";
    }
    @PostMapping("addBooks")
    public String addBooks(@ModelAttribute ("addBook") Book book){

        System.out.println(book.getAuthorId());
        System.out.println(book.getTitle());
        System.out.println(book.getDescrizione());

        bookService.save(book);
        return "redirect:addBooks";
    }
}
