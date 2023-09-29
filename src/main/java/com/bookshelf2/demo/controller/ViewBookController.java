package com.bookshelf2.demo.controller;


import com.bookshelf2.demo.model.BookReport;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewBookController {

    @GetMapping("viewBooks")
    public String getBook(Model model){

        List<BookReport> bookReports = new ArrayList<>();
        String grid = WebClient.create()
                .get()
                .uri("http://localhost:8080/demo/restBooks-reports")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonArray data = new Gson().fromJson(grid.trim(), JsonArray.class);
        for (int i = 0; i<data.size(); i++){
            JsonObject dataObj = data.get(i).getAsJsonObject();
            Long id = dataObj.get("id").getAsLong();
            String title = dataObj.get("title").getAsString();
            String descrizione = dataObj.get("descrizione").getAsString();
            String autore = dataObj.get("autore").getAsString();
            Long authorId = dataObj.get("authorId").getAsLong();
            BookReport bookReport = new BookReport(id, title, descrizione, autore, authorId);
            bookReports.add(bookReport);
            //System.out.println("Id: "+author.getId()+" name: "+author.getName());

        }

        model.addAttribute("bookReports", bookReports);

        return "viewBooks";
    }
}
