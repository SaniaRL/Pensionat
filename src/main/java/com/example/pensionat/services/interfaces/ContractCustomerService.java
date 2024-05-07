package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.models.customers;
import org.springframework.data.domain.Page;

public interface ContractCustomerService {
    Page<customers> getAllCustomersPage(int pageNum);
    Page<customers> getAllCustomersSortedPage(int pageNum, String sortBy, String order);
    customers getCustomerById(Long id);
}
