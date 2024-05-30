package com.example.pensionat;

import com.example.pensionat.dtos.shippers.DetailedShippersDTO;
import com.example.pensionat.services.interfaces.ShippersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class FetchShippers implements CommandLineRunner {

    @Autowired
    ShippersService shippersService;

    @Override
    public void run(String... args) throws Exception {
        DetailedShippersDTO[] shippersArray = shippersService.getShippersToArray();
        String respons = shippersService.saveDownAllShippersToDB(shippersArray);
        System.out.println(respons);
    }
}
