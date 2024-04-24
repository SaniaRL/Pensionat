package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Repositories.CustomerRepo;
import backEnd1.pensionat.services.interfaces.CustomerService;
import org.springframework.stereotype.Service;

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
    public String removeCustomerById(Long id) {
        customerRepo.deleteById(id);
        return "Customer removed successfully";
    }
}
