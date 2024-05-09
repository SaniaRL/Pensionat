package com.example.pensionat;

import com.example.pensionat.dtos.DetailedShippersDTO;
import com.example.pensionat.models.Shippers;
import com.example.pensionat.repositories.ShippersRepo;
import com.example.pensionat.services.convert.ShippersConverter;
import com.example.pensionat.services.interfaces.ShippersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ComponentScan
public class FetchShippers implements CommandLineRunner {

    @Autowired
    ShippersService shippersService;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());

        DetailedShippersDTO[] shippersArray = objMapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), DetailedShippersDTO[].class);
        String respons = shippersService.saveDownAllShippersToDB(shippersArray);
        System.out.println(respons);
    }
}
