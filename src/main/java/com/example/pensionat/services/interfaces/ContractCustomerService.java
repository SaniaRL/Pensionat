package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.ContractCustomerDTO;
import com.example.pensionat.models.customers;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface ContractCustomerService {
    Page<ContractCustomerDTO> getAllCustomersPage(int pageNum);
    Page<ContractCustomerDTO> getAllCustomersSortedPage(int pageNum, String sortBy, String order);
    customers getCustomerById(Long id);
    void addToModel(int currentPage, Model model);
    void addToModelSorted(int currentPage, String sortBy, String order, Model model);
}
