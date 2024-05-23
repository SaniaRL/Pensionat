package com.example.pensionat.controllers;

import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.services.interfaces.RoleService;
import com.example.pensionat.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping("/{username}/edit")
    public String editUser(@PathVariable String username, Model model){
        SimpleUserDTO user = userService.getSimpleUserDtoByUsername(username);
        List<SimpleRoleDTO> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("selectableRoles", roles);
        return "updateUserAccount";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") SimpleUserDTO userDTO, Model model) {
        System.out.println("Vid UPDATE: " + userDTO.getRoles());
        userService.updateUser(userDTO);
        return handleUsers(model);
    }
}