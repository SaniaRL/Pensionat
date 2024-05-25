package com.example.pensionat.controllers;

import com.example.pensionat.dtos.DetailedUserDTO;
import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.services.interfaces.RoleService;
import com.example.pensionat.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('Admin')")
    public String handleUsers(Model model){
        int currentPage = 1;
        userService.addToModel(currentPage, model);
        return "handleUserAccounts";
    }

    @GetMapping(value = "/", params = "page")
    @PreAuthorize("hasAuthority('Admin')")
    public String handleByPage(Model model, @RequestParam int page){
        userService.addToModel(page, model);
        return "handleUserAccounts";
    }

    @RequestMapping("/{username}/remove")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteUserByUsername(@PathVariable String username, Model model) {
        userService.deleteUserByUsername(username);
        return "redirect:/user/";
    }

    @RequestMapping("/{username}/edit")
    @PreAuthorize("hasAuthority('Admin')")
    public String editUser(@PathVariable String username, Model model){
        SimpleUserDTO user = userService.getSimpleUserDtoByUsername(username);
        List<SimpleRoleDTO> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("selectableRoles", roles);
        return "updateUserAccount";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateUser(@ModelAttribute("user") SimpleUserDTO userDTO, Model model) {
        userService.updateUser(userDTO);
        return "redirect:/user/";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('Admin')")
    public String showCreateUserAccountForm(Model model) {
        List<SimpleRoleDTO> roles = roleService.getAllRoles();
        model.addAttribute("selectableRoles", roles);
        return "createUserAccount";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Admin')")
    public String addUser(DetailedUserDTO userDTO, Model model) {
        userService.addUser(userDTO);
        return "redirect:/user/";
    }

    @GetMapping(value = "/", params = "search")
    @PreAuthorize("hasAuthority('Admin')")
    public String userSearch(@RequestParam String search, Model model) throws IOException {
        System.out.println("SÖKORD: " + search);
        int currentPage = 1;
        userService.addToModelUserSearch(search, currentPage, model);
        return "handleUserAccounts";
    }

    @GetMapping(value = "/", params = {"search", "page"})
    @PreAuthorize("hasAuthority('Admin')")
    public String userSearchByPage(@RequestParam String search, Model model, @RequestParam int page) throws IOException {
        userService.addToModelUserSearch(search, page, model);
        return "handleUserAccounts";
    }
}