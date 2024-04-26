package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.CustomerDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Repositories.CustomerRepo;
import backEnd1.pensionat.services.interfaces.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public String addCustomer(Customer c) {
        customerRepo.save(c);
        return "Customer added successfully";
    }

    @Override
    public String addCustomerFromCustomerDTO(CustomerDTO customerDTO) {
        customerRepo.save(customerDtoToCustomer(customerDTO));
        return "Customer added successfully";
    }

    @Override
    public String removeCustomerById(Long id) {
        customerRepo.deleteById(id);
        return "Customer removed successfully";
    }

    @Override
    public String updateCustomer(Customer c) {
        customerRepo.save(c);
        return "Customer updated successfully";
    }

    @Override
    public Page<Customer> getCustomersByEmail(String email, Pageable pageable) {
        return customerRepo.findByEmailContains(email, pageable);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    @Override
    public Customer customerDtoToCustomer(CustomerDTO customerDTO) {
        return Customer.builder().name(customerDTO.getName()).email(customerDTO.getEmail()).build();
    }

    @Override
    public SimpleCustomerDTO customerToSimpleCustomerDto(Customer c) {
        return SimpleCustomerDTO.builder().id(c.getId()).name(c.getName()).email(c.getEmail()).build();
    }
}
