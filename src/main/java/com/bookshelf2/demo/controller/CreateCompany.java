package com.bookshelf2.demo.controller;

import com.bookshelf2.demo.model.Company;
import com.bookshelf2.demo.model.User;
import com.bookshelf2.demo.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
public class CreateCompany {


    @Autowired
    CompanyService companyService;

    @PostMapping("/createCompany")
    public String addCompany(@Valid @RequestPart("name") String name,
                             @RequestPart("piva") String piva,
                             @RequestPart("telefono") String telefono,
                             @RequestPart("email") String email,
                             @RequestPart("url") String url,
                             BindingResult result, Map<String, Object> model,
                             HttpServletRequest request) throws Exception {

        Company company = new Company();
        company.setName(name);
        company.setpIva(piva);
        company.setTel(telefono);
        company.setEmail(email);
        company.setUrl(url);
        if (name.equals("null")) {
            String string = "no-blank";

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(string);

            System.out.println(json);
            return json;
        }
        if (!piva.isEmpty() && (piva.length() != 11)) {
            //model.put("invalidEmail", "La password deve essere lunga almeno 8 caratteri e contenere una lettera maiuscola");
            String string = "invalid-piva";

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(string);

            System.out.println(json);
            return json;
        }

        if(!email.equals("null")){
            System.out.println(email.length());
            try{
                InternetAddress vemail = new InternetAddress(email);
                vemail.validate();
            }catch(AddressException e){
                //model.put("invalidEmail","Format email not correct");
                String string = "invalid-email";

                ObjectMapper objectMapper = new ObjectMapper();

                String json = objectMapper.writeValueAsString(string);

                System.out.println(json);
                return json;
            }
        }
        try {
            companyService.save(company);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());

                String string = "duplicate-piva";

                ObjectMapper objectMapper = new ObjectMapper();

                String json = objectMapper.writeValueAsString(string);

                System.out.println(json);
                return json;
            }
        String string = "add-company";

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(string);

        System.out.println(json);
        return json;
        }
    }