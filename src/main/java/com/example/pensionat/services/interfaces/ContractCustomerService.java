package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.AllCustomersDTO;
import com.example.pensionat.dtos.ContractCustomerDTO;
import com.example.pensionat.dtos.DetailedContractCustomerDTO;
import com.example.pensionat.models.allcustomers;
import com.example.pensionat.models.customers;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface ContractCustomerService {
    Page<ContractCustomerDTO> getAllCustomersPage(int pageNum);
    Page<ContractCustomerDTO> getAllCustomersSortedPage(int pageNum, String sortBy, String order);
    customers getCustomerById(Long id);
    DetailedContractCustomerDTO getDetailedContractCustomerById(Long id);

    Page<ContractCustomerDTO> getCustomersBySearch(int pageNum, String search, String sortBy, String order);

    void addToModel(int currentPage, Model model);
    void addToModelSorted(int currentPage, String sortBy, String order, Model model);

    void addToModelSearch(int currentPage, String search, String sort, String order, Model model);
    void saveAll(List<DetailedContractCustomerDTO> customers);
    AllCustomersDTO fetchContractCustomers(String url) throws IOException;


}
