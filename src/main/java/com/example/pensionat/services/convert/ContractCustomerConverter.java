package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.ContractCustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.models.Customer;
import com.example.pensionat.models.customers;

public class ContractCustomerConverter {
    public static ContractCustomerDTO customersToContractCustomerDto(customers customer) {
        return ContractCustomerDTO.builder()
                .id(customer.getId())
                .companyName(customer.getCompanyName())
                .contactName(customer.getContactName())
                .country(customer.getCountry())
                .build();
    }
}
