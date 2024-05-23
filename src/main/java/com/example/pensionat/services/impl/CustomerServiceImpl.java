package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.dtos.DetailedBlacklistCustomerDTO;
import com.example.pensionat.dtos.SimpleBlacklistCustomerDTO;
import com.example.pensionat.services.interfaces.CustomerService;
import com.example.pensionat.models.Customer;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.services.convert.CustomerConverter;
import com.example.pensionat.services.providers.BlacklistUrlProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final BlacklistUrlProvider blacklistUrlProvider;

    public CustomerServiceImpl(CustomerRepo customerRepo, BlacklistUrlProvider blacklistUrlProvider) {
        this.customerRepo = customerRepo;
        this.blacklistUrlProvider = blacklistUrlProvider;
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
        customerRepo.save(cp);
        return "Customer updated successfully";
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
    public boolean checkIfEmailBlacklisted(String email) {
        String blacklistApiUrl= "https://javabl.systementor.se/api/bed&basse/blacklistcheck/";
        boolean notBlacklisted = false;
        ObjectMapper objectMapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                blacklistApiUrl + "/" + email,
                String.class
        );

        try {
            JsonNode node = objectMapper.readValue(responseEntity.getBody(), JsonNode.class);

            notBlacklisted = node.get("ok").asBoolean();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return notBlacklisted;
    }

    @Override
    public void addToModelBlacklist(int currentPage, Model model) throws IOException {
        Page<SimpleBlacklistCustomerDTO> c = getBlacklistPage(currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
        model.addAttribute("sort", "id");
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
    public String addToBlacklist(SimpleBlacklistCustomerDTO c) {
        try {
            String url = "https://javabl.systementor.se/api/bed&basse/blacklist";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");

            String postData = "{\"email\":\"" + c.getEmail() + "\",\"name\":\"" + c.getName() + "\",\"ok\":false}";

            httpRequest(con, postData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Blacklist updated successfully";
    }

    @Override
    public String updateBlacklistCustomer(SimpleBlacklistCustomerDTO c) {
        try {
            String url = "https://javabl.systementor.se/api/bed&basse/blacklist/" + c.getEmail();
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("PUT");

            con.setRequestProperty("Content-Type", "application/json");

            String postData = "{\"name\":\"" + c.getName() + "\",\"ok\":\"" + c.getOk() + "\"}";

            httpRequest(con, postData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Blacklist updated successfully";
    }

    public List<SimpleBlacklistCustomerDTO> getBlacklist() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        DetailedBlacklistCustomerDTO[] respone = objectMapper.readValue(new URL("https://javabl.systementor.se/api/bed&basse/blacklist")
                , DetailedBlacklistCustomerDTO[].class);

        return Arrays.stream(respone)
                .map(CustomerConverter::detailedBlacklistCustomerDTOToSimpleBlacklistCustomerDTO)
                .toList();
    }

    public Page<SimpleBlacklistCustomerDTO> getBlacklistPage(int pageNum) throws IOException {

        Pageable pageable = PageRequest.of(pageNum - 1, 5);

        List<SimpleBlacklistCustomerDTO> blacklist = getBlacklist();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), blacklist.size());
        Page<SimpleBlacklistCustomerDTO> page = new PageImpl<>(blacklist.subList(start, end), pageable, blacklist.size());

        return page;
    }

    @Override
    public SimpleBlacklistCustomerDTO getCustomerFromBlacklistByEmail(String email) throws IOException {
        List<SimpleBlacklistCustomerDTO> blacklist = getBlacklist();
        blacklist.forEach(objekt -> {
        });
        return blacklist.stream().filter(c -> c.getEmail() != null).filter(c -> c.getEmail()
                                 .equalsIgnoreCase(email))
                                 .toList()
                                 .get(0);
    }

    @Override
    public void httpRequest(HttpURLConnection con, String postData) throws IOException {
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Response : " + response.toString());
    }
}