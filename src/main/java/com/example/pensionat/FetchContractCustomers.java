package com.example.pensionat;

import com.example.pensionat.services.interfaces.ContractCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {

    @Autowired
    ContractCustomerService contractCustomersService;

    @Override
    public void run(String... args) throws Exception {
        contractCustomersService.saveAll(contractCustomersService.fetchContractCustomers().getContractCustomerList());
    }
}
