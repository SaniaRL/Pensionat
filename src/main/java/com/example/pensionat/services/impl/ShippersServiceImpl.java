package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.RoomDTO;
import com.example.pensionat.dtos.SimpleShippersDTO;
import com.example.pensionat.models.Room;
import com.example.pensionat.models.Shippers;
import com.example.pensionat.repositories.ShippersRepo;
import com.example.pensionat.services.convert.RoomTypeConverter;
import com.example.pensionat.services.convert.ShippersConverter;
import com.example.pensionat.services.interfaces.ShippersService;
import org.springframework.stereotype.Service;

@Service
public class ShippersServiceImpl implements ShippersService {

    ShippersRepo shippersRepo;

    @Override
    public SimpleShippersDTO getShippersById(Long id) {
        Shippers shipper = shippersRepo.findById(id).orElse(null);
        return ShippersConverter.shippersToSimpleShippersDTO(shipper);
    }
}
