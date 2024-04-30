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

    String name = "Maria";
    String email = "maria@mail.com";

    Customer customer = new Customer(1L, name, email);
    CustomerDTO customerDTO = new CustomerDTO(name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(1L, name, email);

    @Test
    void getAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);

        List<SimpleCustomerDTO> allKunder = service.getAllCustomers();
        assertEquals(1, allKunder.size());
    }

    @Test //Testet funkar ej, customer är null när det skickas till converter-metoden.
    void addCustomer() {
        System.out.println("ID: " + customer.getId());
        when(customerRepo.save(customer)).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo);
        SimpleCustomerDTO actual = service.addCustomer(simpleCustomerDTO);
        assertEquals(actual, simpleCustomerDTO);
    }

    @Test
    void addCustomerFromCustomerDTO() {
    }

    @Test
    void removeCustomerById() {
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