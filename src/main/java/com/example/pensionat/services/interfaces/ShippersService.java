package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.DetailedShippersDTO;
import com.example.pensionat.dtos.SimpleShippersDTO;

import java.io.IOException;
import java.net.MalformedURLException;

public interface ShippersService {
    SimpleShippersDTO getShippersById(Long id);
    String saveDownAllShippersToDB (DetailedShippersDTO[] shippersArray);
    DetailedShippersDTO[] getShippersToArray() throws IOException;
}
