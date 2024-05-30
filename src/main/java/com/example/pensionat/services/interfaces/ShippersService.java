package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.shippers.DetailedShippersDTO;
import com.example.pensionat.dtos.shippers.SimpleShippersDTO;

import java.io.IOException;

public interface ShippersService {
    SimpleShippersDTO getShippersById(Long id);
    String saveDownAllShippersToDB (DetailedShippersDTO[] shippersArray);
    DetailedShippersDTO[] getShippersToArray() throws IOException;
}
