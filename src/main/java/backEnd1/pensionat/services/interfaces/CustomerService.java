package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.DTOs.DetailedCustomerDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
//    public CustomerDto customerToCustomerDto(Customer c);

//    public Customer customerDtoToCustomer(CustomerDto customer);

    List<SimpleCustomerDTO> getAllCustomers();
    String addCustomer(Customer c);
    String removeCustomerById(Long id);
    String updateCustomer(Customer c);
    Page<SimpleCustomerDTO> getCustomersByEmail(String email, Pageable pageable);
    Customer getCustomerByEmail(String email);
}
