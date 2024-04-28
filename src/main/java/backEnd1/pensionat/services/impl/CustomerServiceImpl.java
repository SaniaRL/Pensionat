package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.CustomerDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Repositories.CustomerRepo;
import backEnd1.pensionat.services.convert.CustomerConverter;
import backEnd1.pensionat.services.interfaces.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import static backEnd1.pensionat.services.convert.CustomerConverter.SimpleCustomerDTOtoCustomer;

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


    //TODO idk - sorry
    @Override
    public SimpleCustomerDTO addCustomer(SimpleCustomerDTO c) {
        return CustomerConverter.customerToSimpleCustomerDTO(customerRepo.save(CustomerConverter.SimpleCustomerDTOtoCustomer(c)));
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
    public String updateCustomer(SimpleCustomerDTO c) {
        Customer cp = SimpleCustomerDTOtoCustomer(c);
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
    public Customer customerDtoToCustomer(CustomerDTO customerDTO) {
        return Customer.builder().name(customerDTO.getName()).email(customerDTO.getEmail()).build();
    }

    @Override
    public SimpleCustomerDTO customerToSimpleCustomerDto(Customer c) {
        return SimpleCustomerDTO.builder().id(c.getId()).name(c.getName()).email(c.getEmail()).build();
    }

    @Override
    public SimpleCustomerDTO getCustomerByEmailSimpleDTO(String email){
        Customer c = customerRepo.findByEmail(email);
        return customerToSimpleCustomerDto(c);
    }
}
