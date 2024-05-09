package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.DetailedShippersDTO;
import com.example.pensionat.dtos.SimpleShippersDTO;

public interface ShippersService {
    SimpleShippersDTO getShippersById(Long id);
    String saveDownAllShippersToDB (DetailedShippersDTO[] shippersArray);

}
