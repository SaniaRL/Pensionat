package com.example.pensionat.controllers;

import com.example.pensionat.dtos.user.DetailedUserDTO;
import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.dtos.user.SimpleUserDTO;
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
@PreAuthorize("hasAuthority('Admin')")
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
        return "redirect:/user/";
    }

    @RequestMapping("/{username}/edit")
    public String editUser(@PathVariable String username, Model model){
        SimpleUserDTO user = userService.getSimpleUserDtoByUsername(username);
        model.addAttribute("originalUsername", username);
        List<SimpleRoleDTO> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("selectableRoles", roles);
        return "updateUserAccount";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") SimpleUserDTO userDTO,
                             @RequestParam("originalUsername") String originalUsername, Model model) {
        model.addAttribute("originalUsername", originalUsername);
        String status = userService.updateUser(userDTO, model);
        model.addAttribute("status", status);
        return editUser((String) model.getAttribute("originalUsername"), model);
    }

    @GetMapping("/create")
    public String showCreateUserAccountForm(Model model) {
        List<SimpleRoleDTO> roles = roleService.getAllRoles();
        model.addAttribute("selectableRoles", roles);
        return "createUserAccount";
    }

    @PostMapping("/add")
    public String addUser(DetailedUserDTO userDTO, Model model) {
        String status = userService.addUser(userDTO, model);
        model.addAttribute("status", status);
        return showCreateUserAccountForm(model);
    }

    @GetMapping(value = "/", params = "search")
    public String userSearch(@RequestParam String search, Model model) throws IOException {
        int currentPage = 1;
        userService.addToModelUserSearch(search, currentPage, model);
        return "handleUserAccounts";
    }

    @GetMapping(value = "/", params = {"search", "page"})
    public String userSearchByPage(@RequestParam String search, Model model, @RequestParam int page) throws IOException {
        userService.addToModelUserSearch(search, page, model);
        return "handleUserAccounts";
    }
}