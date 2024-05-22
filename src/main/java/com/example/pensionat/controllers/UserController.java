package com.example.pensionat.controllers;

import com.example.pensionat.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserRepo userRepo;

    @GetMapping("/")
    public String handleCustomers(Model model){
        int currentPage = 1;
        customerService.addToModel(currentPage, model);
        return "handleCustomers";
    }

    @GetMapping(value = "/", params = "page")
    public String handleByPage(Model model, @RequestParam int page){
        customerService.addToModel(page, model);
        return "handleCustomers";
    }
}
