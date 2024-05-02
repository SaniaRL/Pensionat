package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.CustomerDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;

public class CustomerConverter {

    public static SimpleCustomerDTO customerToSimpleCustomerDTO(Customer customer) {
        return SimpleCustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }

    public static Customer simpleCustomerDTOtoCustomer(SimpleCustomerDTO customer) {
        return Customer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }

    public static Customer customerDtoToCustomer(CustomerDTO customerDTO) {
        return Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .build();
    }
}