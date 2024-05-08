package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.services.interfaces.CustomerService;
import com.example.pensionat.models.Customer;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.services.convert.CustomerConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
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
    public void addToBlacklist(String email, String name) {
        System.out.println("Service anropad!");
        try {
            String url = "https://javabl.systementor.se/api/bed&basse/blacklist";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");

            String postData = "{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"ok\":false}";

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}