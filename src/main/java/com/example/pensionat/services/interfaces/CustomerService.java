package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.SimpleBlacklistCustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
    void addToBlacklist(String email, String name);
    void updateBlacklist(String email, String name, String isOk);
    Page<SimpleBlacklistCustomerDTO> getBlacklist(int pageNum) throws IOException;
    void httpRequest(HttpURLConnection con, String postData) throws IOException;
    }