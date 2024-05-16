package com.example.pensionat;

import com.example.pensionat.dtos.AllCustomersDTO;
import com.example.pensionat.dtos.DetailedContractCustomerDTO;
import com.example.pensionat.models.allcustomers;
import com.example.pensionat.services.convert.ContractCustomerConverter;
import com.example.pensionat.services.interfaces.ContractCustomerService;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;
import java.util.List;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {

    @Autowired
    ContractCustomerService contractCustomersService;

    @Value("${contractCustomers.url}")
    private String contractCustomersUrl;

    @Override
    public void run(String... args) throws Exception {
        contractCustomersService.saveAll(contractCustomersService.fetchContractCustomers(contractCustomersUrl).getContractCustomerList());
    }
}
