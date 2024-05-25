package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.BlacklistResponse;
import com.example.pensionat.dtos.DetailedBlacklistCustomerDTO;
import com.example.pensionat.dtos.SimpleBlacklistCustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import java.util.List;

public interface CustomerService {

    List<SimpleCustomerDTO> getAllCustomers();
    SimpleCustomerDTO addCustomer(SimpleCustomerDTO c);
    String removeCustomerById(Long id);
    String updateCustomer(SimpleCustomerDTO c);
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
    String updateOrAddToBlacklist(SimpleBlacklistCustomerDTO c, String option);
    List<SimpleBlacklistCustomerDTO> getBlacklist() throws IOException;
    DetailedBlacklistCustomerDTO[] mapToDetailedBlacklistCustomerDTOArray(InputStream stream) throws IOException;
    Page<SimpleBlacklistCustomerDTO> getBlacklistPage(int pageNum) throws IOException;
    SimpleBlacklistCustomerDTO getCustomerFromBlacklistByEmail(String email) throws IOException;
    String makeHttpRequest(HttpURLConnection con, String postData) throws IOException;
    }