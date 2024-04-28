package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.DTOs.CustomerDTO;
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
    SimpleCustomerDTO addCustomer(SimpleCustomerDTO c);
    public String addCustomerFromCustomerDTO(CustomerDTO customerDTO);
    String removeCustomerById(Long id);
    String updateCustomer(SimpleCustomerDTO c);
    Page<SimpleCustomerDTO> getCustomersByEmail(String email, int num);
    Page<SimpleCustomerDTO> getAllCustomersPage(int pageNum);
    SimpleCustomerDTO getCustomerByEmail(String email);
    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    SimpleCustomerDTO customerToSimpleCustomerDto(Customer c);

    SimpleCustomerDTO getCustomerByEmailSimpleDTO(String email); //Stämmer denna?

}
