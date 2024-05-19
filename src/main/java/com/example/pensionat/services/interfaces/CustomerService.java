package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.SimpleBlacklistCustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

public interface CustomerService {

    List<SimpleCustomerDTO> getAllCustomers();
    SimpleCustomerDTO addCustomer(SimpleCustomerDTO c);
    String removeCustomerById(Long id);
    String updateCustomer(SimpleCustomerDTO c);
    Page<SimpleCustomerDTO> getCustomersByEmail(String email, int num);
    Page<SimpleCustomerDTO> getAllCustomersPage(int pageNum);
    SimpleCustomerDTO getCustomerByEmail(String email);
    void addToModel(int currentPage, Model model);
    void addToModelEmail(String email, int currentPage, Model model);
    boolean checkIfEmailBlacklisted(String email);
    void addToModelBlacklist(int currentPage, Model model) throws IOException;
    void addToModelBlacklistSearch(String search, int currentPage, Model model) throws IOException;
    Page<SimpleBlacklistCustomerDTO> getBlacklistBySearch(String search, int pageNum) throws IOException;
    String addToBlacklist(SimpleBlacklistCustomerDTO c);
    String updateBlacklistCustomer(SimpleBlacklistCustomerDTO c);
    Page<SimpleBlacklistCustomerDTO> getBlacklistPage(int pageNum) throws IOException;
    SimpleBlacklistCustomerDTO getCustomerFromBlacklistByEmail(String email) throws IOException;
    void httpRequest(HttpURLConnection con, String postData) throws IOException;
    }