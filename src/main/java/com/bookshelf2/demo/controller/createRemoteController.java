package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.Company;
import com.bookshelf2.demo.service.CompanyService;
import com.bookshelf2.demo.util.RcloneCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@RestController
public class createRemoteController {
    @Autowired
    private RcloneCommandExecutor commandExecutor;

    @Autowired
    private CompanyService companyService;

    @PostMapping("createRemote")
    public String createRemote(@RequestParam("name") String idStr){
        Company company = new Company();
        Long id = Long.valueOf(idStr);
        company = companyService.findById(id);
        String name = company.getName();
        System.out.println(name);
        String codice="";
        try {
            String command = "rclone config create "+ name + " drive config_is_local=false";
            String result = commandExecutor.getCommandOutput(command);
            System.out.println("COMMAND " + result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        /*try {
            String command = "rclone authorize drive";
            String result = commandExecutor.getCommandOutput(command);
            System.out.println("COMMAND " + result);
            String[] arrOfStr = result.split("---");
            result = arrOfStr[1];
            arrOfStr = result.split(">");
            result = arrOfStr[1];
            result = result.replaceAll("<","").trim();
            System.out.println("CODICE: "+result);
            codice = result;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/

        try {
            String command = "rclone config update "+name+" --continue --state *oauth-islocal,teamdrive,, --result true";
            String result = commandExecutor.getCommandOutput(command);
            System.out.println("COMMAND " + result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            String command = "rclone lsd "+ name + ":";
            String result = commandExecutor.getCommandOutput(command);
            System.out.println("COMMAND " + result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        company.setRemoteExist(true);
        companyService.save(company);
        return "pippo";
    }
}
