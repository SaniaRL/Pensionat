package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.ContractCustomerDTO;
import com.example.pensionat.dtos.DetailedContractCustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.dtos.SimpleShippersDTO;
import com.example.pensionat.models.Customer;
import com.example.pensionat.models.Shippers;
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


    public static DetailedContractCustomerDTO contractCustomerToDetailedContractCustomer(customers cCustomer) {
        return DetailedContractCustomerDTO.builder()
                .id(cCustomer.getId())
                .companyName(cCustomer.getCompanyName())
                .contactName(cCustomer.getContactName())
                .contactTitle(cCustomer.getContactTitle())
                .streetAddress(cCustomer.getStreetAddress())
                .city(cCustomer.getCity())
                .postalCode(cCustomer.getPostalCode())
                .country(cCustomer.getCountry())
                .phone(cCustomer.getPhone())
                .fax(cCustomer.getFax())
                .build();
    }
}
