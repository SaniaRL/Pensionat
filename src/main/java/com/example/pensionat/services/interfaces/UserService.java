package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.user.DetailedUserDTO;
import com.example.pensionat.dtos.user.SimpleUserDTO;
import com.example.pensionat.models.User;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface UserService {
    void addToModel(int currentPage, Model model);
    void addToModelUserSearch(String search, int currentPage, Model model);
    Page<SimpleUserDTO> getAllUsersPage(int pageNum);
    SimpleUserDTO getSimpleUserDtoByUsername(String username);
    Page<SimpleUserDTO> getUsersBySearch(String search, int pageNum);
    void deleteUserByUsername(String username);
    String updateUser(SimpleUserDTO userDTO, Model model);
    String addUser(DetailedUserDTO userDTO, Model model);
    void updatePassword(String username, String newPassword);
    void createPasswordResetTokenForUser(String email, String token);
    User getUserByResetToken(String token);
    void removeResetToken(User user);
    }