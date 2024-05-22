package com.example.pensionat.controllers;

import com.example.pensionat.services.interfaces.UserService;
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

    private final UserService userService;

    @GetMapping("/")
    public String handleCustomers(Model model){
        int currentPage = 1;
        userService.addToModel(currentPage, model);
        return "handleUserAccounts";
    }

    @GetMapping(value = "/", params = "page")
    public String handleByPage(Model model, @RequestParam int page){
        userService.addToModel(page, model);
        return "handleUserAccounts";
    }
}
