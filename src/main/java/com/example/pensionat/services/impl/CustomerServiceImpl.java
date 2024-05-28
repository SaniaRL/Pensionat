package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.BlacklistResponse;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.dtos.DetailedBlacklistCustomerDTO;
import com.example.pensionat.dtos.SimpleBlacklistCustomerDTO;
import com.example.pensionat.services.interfaces.CustomerService;
import com.example.pensionat.models.Customer;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.services.convert.CustomerConverter;
import com.example.pensionat.services.providers.BlacklistStreamAndUrlProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final BlacklistStreamAndUrlProvider blacklistStreamAndUrlProvider;

    public CustomerServiceImpl(CustomerRepo customerRepo, BlacklistStreamAndUrlProvider blacklistStreamAndUrlProvider) {
        this.customerRepo = customerRepo;
        this.blacklistStreamAndUrlProvider = blacklistStreamAndUrlProvider;
    }

    @Override
    public List<SimpleCustomerDTO> getAllCustomers() {
        return customerRepo.findAll().stream().map(CustomerConverter::customerToSimpleCustomerDTO).toList();
    }

    @Override
    public SimpleCustomerDTO addCustomer(SimpleCustomerDTO c) {
        return CustomerConverter.customerToSimpleCustomerDTO(customerRepo.save(CustomerConverter.simpleCustomerDTOtoCustomer(c)));
    }

    @Override
    public void addToModel(int currentPage, Model model){
        Page<SimpleCustomerDTO> c = getAllCustomersPage(currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
    }

    @Override
    public void addToModelEmail(String email, int currentPage, Model model){
        Page<SimpleCustomerDTO> c = getCustomersByEmail(email, currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
    }

    @Override
    public String removeCustomerById(Long id) {
        customerRepo.deleteById(id);
        return "Customer removed successfully";
    }

    @Override
    public String updateCustomer(SimpleCustomerDTO c) {
        Customer cp = CustomerConverter.simpleCustomerDTOtoCustomer(c);
        try {
            customerRepo.save(cp);
            return "OK";
        }
        catch (Exception e){
             return "Email already in use";
        }
    }

    @Override
    public SimpleCustomerDTO getCustomerById(Long id) {
        return customerRepo.findById(id).map(CustomerConverter::customerToSimpleCustomerDTO).orElse(null);
    }

    @Override
    public Page<SimpleCustomerDTO> getCustomersByEmail(String email, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5);
        Page<Customer> page = customerRepo.findByEmailContains(email, pageable);
        return page.map(CustomerConverter::customerToSimpleCustomerDTO);
    }

    @Override
    public Page<SimpleCustomerDTO> getAllCustomersPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5);
        Page<Customer> page = customerRepo.findAll(pageable);
        return page.map(CustomerConverter::customerToSimpleCustomerDTO);
    }

    @Override
    public SimpleCustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepo.findByEmail(email);
        if(customer!= null){
            return CustomerConverter.customerToSimpleCustomerDTO(customer);
        }
        return null;
    }

    @Override
    public boolean checkIfEmailBlacklisted(String email) throws IOException, InterruptedException {
        HttpResponse<String> response = blacklistStreamAndUrlProvider.getHttpResponse(email);
        BlacklistResponse blacklistResponse = mapToBlacklistResponse(response);
        return blacklistResponse.getOk();
    }

    @Override
    public BlacklistResponse mapToBlacklistResponse(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), BlacklistResponse.class);
    }

    @Override
    public void addToModelBlacklist(int currentPage, Model model) throws IOException {
        System.out.println("addToModelBlacklist");
        Page<SimpleBlacklistCustomerDTO> c = getBlacklistPage(currentPage);
        System.out.println("Blacklist-page returned");
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
        model.addAttribute("sort", "id");
        System.out.println("all attributes added");
    }

    @Override
    public void addToModelBlacklistSearch(String search, int currentPage, Model model) throws IOException {
        Page<SimpleBlacklistCustomerDTO> c = getBlacklistBySearch(search, currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
        model.addAttribute("search", search);
    }

    @Override
    public Page<SimpleBlacklistCustomerDTO> getBlacklistBySearch(String search, int pageNum) throws IOException {
        int pageSize = 5;
        int skip = (pageNum - 1) * pageSize;

        List<SimpleBlacklistCustomerDTO> blacklist = getBlacklist();

        String lowercaseSearch = search.toLowerCase();

        List<SimpleBlacklistCustomerDTO> filteredBlacklist = blacklist.stream()
                .filter(customer -> (customer.getEmail() != null && customer.getEmail().toLowerCase().contains(lowercaseSearch)) ||
                        (customer.getName() != null && customer.getName().toLowerCase().contains(lowercaseSearch)))
                .skip(skip)
                .limit(pageSize)
                .toList();

        long total = blacklist.stream()
                .filter(customer -> (customer.getEmail() != null && customer.getEmail().toLowerCase().contains(lowercaseSearch)) ||
                        (customer.getName() != null && customer.getName().toLowerCase().contains(lowercaseSearch)))
                .count();

        return new PageImpl<>(filteredBlacklist, PageRequest.of(pageNum - 1, pageSize), total);
    }

    @Override
    public String updateOrAddToBlacklist(SimpleBlacklistCustomerDTO c) {
        c.setEmail(c.getEmail().trim());
        try {
            String blacklistUrl = blacklistStreamAndUrlProvider.getBlacklistUrl();
            String postData = "{\"email\":\"" + c.getEmail() + "\",\"name\":\"" + c.getName() + "\",\"ok\":false}";

            int response = sendHttpRequest("POST", blacklistUrl, postData);

            if (response == 200) {
                return c.getEmail() + " är nu svartlistad!";
            } else if (response == 400) {
                String updateUrl = blacklistUrl + "/" + c.getEmail();
                postData = "{\"name\":\"" + c.getName() + "\",\"ok\":" + c.getOk() + "}";
                response = sendHttpRequest("PUT", updateUrl, postData);
                if (response == 204) {
                    return c.getEmail() + " är redan svartlistad. Uppgifter uppdaterade.";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Blacklist blev ej uppdaterad. kontakta support.";
    }

    @Override
    public List<SimpleBlacklistCustomerDTO> getBlacklist() throws IOException {
        InputStream stream = blacklistStreamAndUrlProvider.getDataStream();
        DetailedBlacklistCustomerDTO[] respone = mapToDetailedBlacklistCustomerDTOArray(stream);

        return Arrays.stream(respone)
                .map(CustomerConverter::detailedBlacklistCustomerDTOToSimpleBlacklistCustomerDTO)
                .toList();
    }

    @Override
    public DetailedBlacklistCustomerDTO[] mapToDetailedBlacklistCustomerDTOArray(InputStream stream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(stream, DetailedBlacklistCustomerDTO[].class);
    }

    @Override
    public Page<SimpleBlacklistCustomerDTO> getBlacklistPage(int pageNum) throws IOException {
        System.out.println("getBlacklistPage");

        Pageable pageable = PageRequest.of(pageNum - 1, 5);

        List<SimpleBlacklistCustomerDTO> blacklist = getBlacklist();
        System.out.println("List blacklist");

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), blacklist.size());
        System.out.println("before page");

        Page<SimpleBlacklistCustomerDTO> page = new PageImpl<>(blacklist.subList(start, end), pageable, blacklist.size());

        return page;
    }

    @Override
    public SimpleBlacklistCustomerDTO getCustomerFromBlacklistByEmail(String email) throws IOException {
        List<SimpleBlacklistCustomerDTO> blacklist = getBlacklist();
        return blacklist.stream()
                        .filter(c -> c.getEmail() != null)
                        .filter(c -> c.getEmail().equalsIgnoreCase(email))
                        .findFirst()
                        .orElse(null);
    }

    @Override
    public int sendHttpRequest(String requestMethod, String urlString, String postData) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(requestMethod);
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = postData.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        System.out.println("Response Code : " + con.getResponseCode());

        return con.getResponseCode();
    }
}