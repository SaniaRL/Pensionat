package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.DTOs.DetailedCustomerDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;

import java.util.List;

public interface CustomerService {
//    public CustomerDto customerToCustomerDto(Customer c);

//    public Customer customerDtoToCustomer(CustomerDto customer);

    List<Customer> getAllCustomers();
    String addCustomer(Customer c);
    String removeCustomerById(Long id);
    DetailedCustomerDTO customerToDetailedCustomerDTO(Customer customer);
    SimpleCustomerDTO customerToSimpleCustomerDTO(Customer customer);
}
