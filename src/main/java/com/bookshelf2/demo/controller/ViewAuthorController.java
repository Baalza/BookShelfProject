package com.bookshelf2.demo.controller;


import com.bookshelf2.demo.model.Author;
import com.bookshelf2.demo.model.BookReport;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewAuthorController {

    @GetMapping("viewAuthors")
    public String getAuthor(Model model){

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

        return "viewAuthors";
    }

    @GetMapping("author/{id}")
    public @ResponseBody ModelAndView getBooksAuthor(@PathVariable(value = "id") String id){

        List<BookReport> books = new ArrayList<>();
        String grid = WebClient.create()
                .get()
                .uri("http://localhost:8080/demo/restBooks-author/"+ id)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonArray data = new Gson().fromJson(grid.trim(), JsonArray.class);
        for (int i = 0; i<data.size(); i++) {
            JsonObject dataObj = data.get(i).getAsJsonObject();
            Long bookId = dataObj.get("id").getAsLong();
            String title = dataObj.get("title").getAsString();
            String descrizione = dataObj.get("descrizione").getAsString();
            String autore = dataObj.get("autore").getAsString();
            BookReport bookReport = new BookReport(bookId, title, descrizione, autore);
            //Book book = new Book(bookId, title, descrizione);
            books.add(bookReport);


        }
        ModelAndView mav = new ModelAndView("viewAuthorSpec.html");
        mav.addObject("books", books);
        return mav;
    }
}
