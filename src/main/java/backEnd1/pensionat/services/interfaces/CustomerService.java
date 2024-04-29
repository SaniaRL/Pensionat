package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.DTOs.CustomerDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    List<SimpleCustomerDTO> getAllCustomers();
    SimpleCustomerDTO addCustomer(SimpleCustomerDTO c);
    public String addCustomerFromCustomerDTO(CustomerDTO customerDTO);
    String removeCustomerById(Long id);
    String updateCustomer(SimpleCustomerDTO c);
    Page<SimpleCustomerDTO> getCustomersByEmail(String email, int num);
    Page<SimpleCustomerDTO> getAllCustomersPage(int pageNum);
    SimpleCustomerDTO getCustomerByEmail(String email);
    SimpleCustomerDTO getCustomerByEmailSimpleDTO(String email); //Stämmer denna?
}