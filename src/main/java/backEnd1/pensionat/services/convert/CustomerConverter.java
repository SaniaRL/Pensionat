package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;

public class CustomerConverter {
    public Customer SimpleCustomerDTOtoCustomer(SimpleCustomerDTO customer) {
        return Customer.builder().id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail()).build();
    }
}
