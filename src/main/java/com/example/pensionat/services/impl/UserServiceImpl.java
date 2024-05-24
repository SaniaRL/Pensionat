package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.DetailedUserDTO;
import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.models.Role;
import com.example.pensionat.models.User;
import com.example.pensionat.repositories.RoleRepo;
import com.example.pensionat.repositories.UserRepo;
import com.example.pensionat.services.convert.UserConverter;
import com.example.pensionat.services.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo rolerepo) {
        this.userRepo = userRepo;
        this.roleRepo = rolerepo;
    }

    @Override
    public void addToModel(int currentPage, Model model){
        Page<SimpleUserDTO> u = getAllUsersPage(currentPage);
        model.addAttribute("allUsers", u.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", u.getTotalElements());
        model.addAttribute("totalPages", u.getTotalPages());
    }

    @Override
    public void addToModelUserSearch(String search, int currentPage, Model model) {
        Page<SimpleUserDTO> u = getUsersBySearch(search, currentPage);
        model.addAttribute("allUsers", u.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", u.getTotalElements());
        model.addAttribute("totalPages", u.getTotalPages());
        model.addAttribute("search", search);
    }

    @Override
    public Page<SimpleUserDTO> getAllUsersPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5);
        Page<User> page = userRepo.findAll(pageable);
        return page.map(UserConverter::userToSimpleUserDTO);
    }

    @Override
    public SimpleUserDTO getSimpleUserDtoByUsername(String username) {
        User user = userRepo.findByUsername(username);
        return UserConverter.userToSimpleUserDTO(user);
    }

    @Override
    public Page<SimpleUserDTO> getUsersBySearch(String search, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5);
        Page<User> page = userRepo.findByUsernameContainsOrRolesNameContains(search, search, pageable);
        System.out.println("SÖKORD2: " + search);
        System.out.println("PAGE: " + page.getTotalElements());
        return page.map(UserConverter::userToSimpleUserDTO);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepo.deleteByUsername(username);
    }

    @Override
    public void updateUser(SimpleUserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId());
        List<Role> roles = new ArrayList<>();
        for (SimpleRoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepo.findByName(roleDTO.getName());
            roles.add(role);
        }
        userRepo.save(UserConverter.simpleUserDtoToUser(userDTO, user, roles));
    }

    @Override
    public void addUser(DetailedUserDTO userDTO) {
        List<Role> roles = new ArrayList<>();
        for (SimpleRoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepo.findByName(roleDTO.getName());
            roles.add(role);
        }
        userRepo.save(UserConverter.detailedUserDtoToUser(userDTO, roles));
    }
}