package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.CustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public interface CustomerService {

    List<SimpleCustomerDTO> getAllCustomers();
    SimpleCustomerDTO addCustomer(SimpleCustomerDTO c);
    String addCustomerFromCustomerDTO(CustomerDTO customerDTO);
    String removeCustomerById(Long id);
    String updateCustomer(SimpleCustomerDTO c);
    Page<SimpleCustomerDTO> getCustomersByEmail(String email, int num);
    Page<SimpleCustomerDTO> getAllCustomersPage(int pageNum);
    SimpleCustomerDTO getCustomerByEmail(String email);
    void addToModel(int currentPage, Model model);
    void addToModelEmail(String email, int currentPage, Model model);

    }