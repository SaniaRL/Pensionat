package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.CustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.services.interfaces.CustomerService;
import com.example.pensionat.models.Customer;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.services.convert.CustomerConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
    public String addCustomerFromCustomerDTO(CustomerDTO customerDTO) {
        customerRepo.save(CustomerConverter.customerDtoToCustomer(customerDTO));
        return "Customer added successfully";
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
}