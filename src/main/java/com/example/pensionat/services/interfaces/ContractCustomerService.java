package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.contractcustomer.AllCustomersDTO;
import com.example.pensionat.dtos.contractcustomer.ContractCustomerDTO;
import com.example.pensionat.dtos.contractcustomer.DetailedContractCustomerDTO;
import com.example.pensionat.models.customers;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

public interface ContractCustomerService {
    Page<ContractCustomerDTO> getAllCustomersPage(int pageNum, int pageSize);
    Page<ContractCustomerDTO> getAllCustomersSortedPage(int pageNum, String sortBy, String order, int pageSize);
    customers getCustomerById(Long id);
    DetailedContractCustomerDTO getDetailedContractCustomerById(Long id);

    Page<ContractCustomerDTO> getCustomersBySearch(int pageNum, String search, String sortBy, String order, int pageSize);

    void addToModel(int currentPage, Model model, int pageSize);
    void addToModelSorted(int currentPage, String sortBy, String order, Model model, int pageSize);

    void addToModelSearch(int currentPage, String search, String sort, String order, Model model, int pageSize);
    void saveAll(List<DetailedContractCustomerDTO> customers);
    AllCustomersDTO fetchContractCustomers() throws IOException;
}
