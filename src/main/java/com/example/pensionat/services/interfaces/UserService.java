package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.DetailedUserDTO;
import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.models.User;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface UserService {
    void addToModel(int currentPage, Model model);
    Page<SimpleUserDTO> getAllUsersPage(int pageNum);
    SimpleUserDTO getSimpleUserDtoByUsername(String username);
    User getUserByUsername(String username);
    void deleteUserByUsername(String username);
    void updateUser(SimpleUserDTO userDTO);
    void addUser(DetailedUserDTO userDTO);
}