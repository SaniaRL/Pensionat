package com.example.pensionat.services.impl.unit;

import com.example.pensionat.dtos.CustomerDTO;
import com.example.pensionat.dtos.DetailedShippersDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.models.Customer;
import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.services.impl.CustomerServiceImpl;
import com.example.pensionat.services.providers.BlacklistStreamAndUrlProvider;
import com.example.pensionat.services.providers.ShippersStreamProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private BlacklistStreamAndUrlProvider provider;

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
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        List<SimpleCustomerDTO> actual = service.getAllCustomers();
        assertEquals(1, actual.size());
        assertEquals(actual.get(0).getId(), customer.getId());
        assertEquals(actual.get(0).getName(), customer.getName());
        assertEquals(actual.get(0).getEmail(), customer.getEmail());
    }

    @Test
    void addCustomer() {
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        SimpleCustomerDTO actual = service.addCustomer(simpleCustomerDTO);
        assertEquals(actual.getId(), simpleCustomerDTO.getId());
        assertEquals(actual.getName(), simpleCustomerDTO.getName());
        assertEquals(actual.getEmail(), simpleCustomerDTO.getEmail());
    }

    @Test
    void removeCustomerById() {
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        String feedback = service.removeCustomerById(id);
        assertTrue(feedback.equalsIgnoreCase("Customer removed successfully"));
    }

    @Test
    void updateCustomer() {
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        String feedback = service.updateCustomer(simpleCustomerDTO);
        assertTrue(feedback.equalsIgnoreCase("Customer updated successfully"));
    }

    @Test
    void getCustomersByEmail() {
        when(customerRepo.findByEmailContains(email, pageable)).thenReturn(mockedPage);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        Page<SimpleCustomerDTO> actual = service.getCustomersByEmail(customer.getEmail(), pageNum);
        assertEquals(1, actual.getTotalElements());
        assertEquals(customer.getId(), actual.getContent().get(0).getId());
        assertEquals(customer.getName(), actual.getContent().get(0).getName());
        assertEquals(customer.getEmail(), actual.getContent().get(0).getEmail());
    }

    @Test
    void getAllCustomersPage() {
        when(customerRepo.findAll(pageable)).thenReturn(mockedPage);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        Page<SimpleCustomerDTO> actual = service.getAllCustomersPage(pageNum);
        assertEquals(1, actual.getTotalElements());
        assertEquals(customer.getId(), actual.getContent().get(0).getId());
        assertEquals(customer.getName(), actual.getContent().get(0).getName());
        assertEquals(customer.getEmail(), actual.getContent().get(0).getEmail());
    }

    @Test
    void getCustomerByEmail() {
        when(customerRepo.findByEmail(email)).thenReturn(customer);
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
        SimpleCustomerDTO actual = service.getCustomerByEmail(email);
        assertEquals(actual.getId(), simpleCustomerDTO.getId());
        assertEquals(actual.getName(), simpleCustomerDTO.getName());
        assertEquals(actual.getEmail(), simpleCustomerDTO.getEmail());
    }

    @Test
    void checkIfEmailBlacklisted() throws IOException { // prio
        when(provider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));
        CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, provider);
    }
    @Test
    void fetchAndSaveShippersShouldSaveToDatabase() throws IOException {
        ShippersStreamProvider shippersStreamProvider = mock(ShippersStreamProvider.class);
        when(shippersStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));

        shippersRepo.deleteAll();

        DetailedShippersDTO[] tempArray = sut.getShippersToArray();
        sut.saveDownAllShippersToDB(tempArray);

        assertEquals(8, shippersRepo.count());
    }

    @Test
    void addToBlacklist() {

    }

    @Test
    void updateBlacklist() {

    }

    @Test
    void getBlacklistPage() {

    }

    @Test
    void getBlacklist() { // prio

    }

    @Test
    void httpRequest() {

    }

    @Test
    void addToModelBlacklist() {

    }

    @Test
    void addToModelBlacklistSearch() {

    }

    @Test
    void getBlacklistBySearch() {

    }

    @Test
    void getCustomerFromBlacklistByEmail() {

    }
}