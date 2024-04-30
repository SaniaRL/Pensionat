package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.CustomerDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Repositories.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;

    Long id = 1L;
    String name = "Maria";
    String email = "maria@mail.com";


    Customer customer = new Customer(id, name, email);
    CustomerDTO customerDTO = new CustomerDTO(name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(id, name, email);
    int pageNum = 1;
    Pageable pageable = PageRequest.of(pageNum - 1, 5);
    Page<Customer> mockedPage = new PageImpl<>(List.of(customer));

    @Test
    void getAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        List<SimpleCustomerDTO> allKunder = service.getAllCustomers();
        assertEquals(1, allKunder.size());
    }

    @Test
    void addCustomer() {
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        SimpleCustomerDTO actual = service.addCustomer(simpleCustomerDTO);
        assertEquals(actual.getId(), simpleCustomerDTO.getId());
        assertEquals(actual.getName(), simpleCustomerDTO.getName());
        assertEquals(actual.getEmail(), simpleCustomerDTO.getEmail());
    }

    @Test
    void addCustomerFromCustomerDTO() {
        when(customerRepo.save(customer)).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        String feedback = service.addCustomerFromCustomerDTO(customerDTO);
        assertTrue(feedback.equalsIgnoreCase("Customer added successfully"));
    }

    @Test
    void removeCustomerById() {
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        String feedback = service.removeCustomerById(id);
        assertTrue(feedback.equalsIgnoreCase("Customer removed successfully"));
    }

    @Test
    void updateCustomer() {
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        String feedback = service.updateCustomer(simpleCustomerDTO);
        assertTrue(feedback.equalsIgnoreCase("Customer updated successfully"));
    }

    @Test
    void getCustomersByEmail() {
        when(customerRepo.findByEmailContains(email, pageable)).thenReturn(mockedPage);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        Page<SimpleCustomerDTO> actual = service.getCustomersByEmail(customer.getEmail(), pageNum);
        assertEquals(1, actual.getTotalElements());
        assertEquals(customer.getId(), actual.getContent().get(0).getId());
        assertEquals(customer.getName(), actual.getContent().get(0).getName());
        assertEquals(customer.getEmail(), actual.getContent().get(0).getEmail());
    }

    @Test
    void getAllCustomersPage() {
        when(customerRepo.findAll(pageable)).thenReturn(mockedPage);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        Page<SimpleCustomerDTO> actual = service.getAllCustomersPage(pageNum);
        assertEquals(1, actual.getTotalElements());
        assertEquals(customer.getId(), actual.getContent().get(0).getId());
        assertEquals(customer.getName(), actual.getContent().get(0).getName());
        assertEquals(customer.getEmail(), actual.getContent().get(0).getEmail());
    }

    @Test
    void getCustomerByEmail() {
        when(customerRepo.findByEmail(email)).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        SimpleCustomerDTO actual = service.getCustomerByEmail(email);
        assertEquals(actual.getId(), simpleCustomerDTO.getId());
        assertEquals(actual.getName(), simpleCustomerDTO.getName());
        assertEquals(actual.getEmail(), simpleCustomerDTO.getEmail());
    }

    @Test
    void getCustomerByEmailSimpleDTO() {
        when(customerRepo.findByEmail(email)).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        SimpleCustomerDTO actual = service.getCustomerByEmailSimpleDTO(email);
        assertEquals(actual.getId(), simpleCustomerDTO.getId());
        assertEquals(actual.getName(), simpleCustomerDTO.getName());
        assertEquals(actual.getEmail(), simpleCustomerDTO.getEmail());
    }
}