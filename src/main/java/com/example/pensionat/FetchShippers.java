package com.example.pensionat;

import com.example.pensionat.models.Shippers;
import com.example.pensionat.repositories.ShippersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@ComponentScan
public class FetchShippers implements CommandLineRunner {

    @Autowired
    private ShippersRepo shippersRepo;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());

        Shippers[] shippersArray = objMapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), Shippers[].class);
        List<Shippers> shippersList = Arrays.asList(shippersArray);
        shippersRepo.saveAll(shippersList);
    }
}
