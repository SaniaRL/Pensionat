package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.SimpleShippersDTO;
import com.example.pensionat.models.Shippers;
import com.example.pensionat.models.customers;

public interface ShippersService {

    SimpleShippersDTO getShippersById(Long id);

}
