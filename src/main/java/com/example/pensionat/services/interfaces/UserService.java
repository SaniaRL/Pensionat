package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.SimpleCustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface UserService {
    void addToModel(int currentPage, Model model);
    Page<SimpleUserDTO> getAllUsersPage(int pageNum);
}
