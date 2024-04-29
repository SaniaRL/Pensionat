package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.CustomerDTO;
import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerConverterTest {

    String name = "Maria";
    String email = "maria@mail.com";

    Customer customer = new Customer(name, email);
    CustomerDTO customerDTO = new CustomerDTO(name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(name, email);

    @Test
    void customerToSimpleCustomerDTO() {
        SimpleCustomerDTO actual = CustomerConverter.customerToSimpleCustomerDTO(customer);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getName(), customer.getName());
        assertEquals(actual.getEmail(), customer.getEmail());
    }

    @Test
    void simpleCustomerDTOtoCustomer() {
        Customer actual = CustomerConverter.simpleCustomerDTOtoCustomer(simpleCustomerDTO);

        assertEquals(actual.getId(), simpleCustomerDTO.getId());
        assertEquals(actual.getName(), simpleCustomerDTO.getName());
        assertEquals(actual.getEmail(), simpleCustomerDTO.getEmail());
    }

    @Test
    void customerDtoToCustomer() {
        Customer actual = CustomerConverter.customerDtoToCustomer(customerDTO);

        assertEquals(actual.getName(), customerDTO.getName());
        assertEquals(actual.getEmail(), customerDTO.getEmail());
    }
}