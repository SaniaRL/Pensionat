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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void getAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        List<SimpleCustomerDTO> allKunder = service.getAllCustomers();
        assertEquals(1, allKunder.size());
    }

    @Test //Testet funkar ej, customer är null när det skickas till converter-metoden.
    void addCustomer() {
        when(customerRepo.save(customer)).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        SimpleCustomerDTO actual = service.addCustomer(simpleCustomerDTO);
        assertEquals(actual, simpleCustomerDTO);
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
    }

    @Test
    void getCustomersByEmail() {
    }

    @Test
    void getAllCustomersPage() {
    }

    @Test
    void getCustomerByEmail() {
    }

    @Test
    void getCustomerByEmailSimpleDTO() {
    }
}