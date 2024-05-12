package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.CustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.dtos.SimpleBlacklistCustomerDTO;
import com.example.pensionat.dtos.DetailedBlacklistCustomerDTO;
import com.example.pensionat.models.Customer;

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

    public static SimpleBlacklistCustomerDTO detailedBlacklistCustomerDTOToSimpleBlacklistCustomerDTO
                                                (DetailedBlacklistCustomerDTO dbcDTO) {
        return SimpleBlacklistCustomerDTO.builder()
                .name(dbcDTO.getName())
                .email(dbcDTO.getEmail())
                .ok(dbcDTO.getOk())
                .build();
    }
}