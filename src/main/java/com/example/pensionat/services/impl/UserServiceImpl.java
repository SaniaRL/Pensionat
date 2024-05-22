package com.example.pensionat.services.impl;

import com.example.pensionat.models.User;
import com.example.pensionat.repositories.UserRepo;
import com.example.pensionat.services.convert.CustomerConverter;
import com.example.pensionat.services.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void addToModel(int currentPage, Model model){
        Page<SimpleUserDTO> c = getAllUsersPage(currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
    }

    @Override
    public Page<SimpleUserDTO> getAllUsersPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5);
        Page<User> page = userRepo.findAll(pageable);
        return page.map(UserConverter::userToSimpleUserDTO);
    }
}
