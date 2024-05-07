package com.example.pensionat;

import com.example.pensionat.models.allcustomers;
import com.example.pensionat.repositories.ContractCustomersRepo;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {

    @Autowired
    ContractCustomersRepo contractCustomersRepo;

    @Override
    public void run(String... args) throws Exception {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        allcustomers theCustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), allcustomers.class);

        contractCustomersRepo.saveAll(theCustomers.customers);
    }
}
