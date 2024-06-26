package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.blacklist.BlacklistResponse;
import com.example.pensionat.dtos.blacklist.DetailedBlacklistCustomerDTO;
import com.example.pensionat.dtos.blacklist.SimpleBlacklistCustomerDTO;
import com.example.pensionat.dtos.customer.SimpleCustomerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.List;

public interface CustomerService {

    List<SimpleCustomerDTO> getAllCustomers();
    SimpleCustomerDTO addCustomer(SimpleCustomerDTO c);
    String removeCustomerById(Long id);
    String updateCustomer(SimpleCustomerDTO c);

    SimpleCustomerDTO getCustomerById(Long id);

    Page<SimpleCustomerDTO> getCustomersByEmail(String email, int num);
    Page<SimpleCustomerDTO> getAllCustomersPage(int pageNum);
    SimpleCustomerDTO getCustomerByEmail(String email);
    BlacklistResponse mapToBlacklistResponse(HttpResponse<String> response) throws JsonProcessingException;
    void addToModel(int currentPage, Model model);
    void addToModelEmail(String email, int currentPage, Model model);
    boolean checkIfEmailBlacklisted(String email) throws IOException, InterruptedException;
    void addToModelBlacklist(int currentPage, Model model) throws IOException;
    void addToModelBlacklistSearch(String search, int currentPage, Model model) throws IOException;
    Page<SimpleBlacklistCustomerDTO> getBlacklistBySearch(String search, int pageNum) throws IOException;
    String updateOrAddToBlacklist(SimpleBlacklistCustomerDTO c);
    List<SimpleBlacklistCustomerDTO> getBlacklist() throws IOException;
    DetailedBlacklistCustomerDTO[] mapToDetailedBlacklistCustomerDTOArray(InputStream stream) throws IOException;
    Page<SimpleBlacklistCustomerDTO> getBlacklistPage(int pageNum) throws IOException;
    SimpleBlacklistCustomerDTO getCustomerFromBlacklistByEmail(String email) throws IOException;
    int sendHttpRequest(String requestMethod, String urlString, String postData) throws IOException;
    }