package com.example.pensionat;

import com.example.pensionat.models.allcustomers;
import com.example.pensionat.services.interfaces.ContractCustomerService;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {

    @Autowired
    ContractCustomerService contractCustomersService;

    @Override
    public void run(String... args) throws Exception {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        allcustomers theCustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), allcustomers.class);

        //TODO contractCustomerService
        contractCustomersService.saveAll(theCustomers.customers);
    }
}
