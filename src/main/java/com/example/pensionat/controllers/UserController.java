package com.example.pensionat.controllers;

import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/")
    public String handleUsers(Model model){
        int currentPage = 1;
        userService.addToModel(currentPage, model);
        return "handleUserAccounts";
    }

    @GetMapping(value = "/", params = "page")
    public String handleByPage(Model model, @RequestParam int page){
        userService.addToModel(page, model);
        return "handleUserAccounts";
    }

    @RequestMapping("/{username}/remove")
    public String deleteUserByUsername(@PathVariable String username, Model model) {
        userService.deleteUserByUsername(username);
        return handleUsers(model);
    }

    @RequestMapping("/{username}/update")
    public String editUser(@PathVariable String username, Model model){
        SimpleUserDTO u = userService.getSimpleUserDtoByUsername(username);
        List<Role> roles =
        model.addAttribute("user", u);
        return "updateUserAccount";
    }
}