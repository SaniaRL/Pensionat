package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.DetailedShippersDTO;
import com.example.pensionat.dtos.SimpleShippersDTO;
import com.example.pensionat.models.Shippers;
import com.example.pensionat.repositories.ShippersRepo;
import com.example.pensionat.services.convert.ShippersConverter;
import com.example.pensionat.services.interfaces.ShippersService;
import org.springframework.stereotype.Service;


@Service
public class ShippersServiceImpl implements ShippersService {

    private final ShippersRepo shippersRepo;

    ShippersServiceImpl(ShippersRepo shippersRepo) {
        this.shippersRepo = shippersRepo;
    }

    @Override
    public SimpleShippersDTO getShippersById(Long id) {
        Shippers shipper = shippersRepo.findById(id).orElse(null);
        return ShippersConverter.shippersToSimpleShippersDTO(shipper);
    }

    @Override
    public String saveDownAllShippersToDB(DetailedShippersDTO[] shippersArray) {
        DetailedShippersDTO[] detailedShippersList = shippersArray;
        for (DetailedShippersDTO tempShipper : detailedShippersList) {
            Shippers convertedShipper = ShippersConverter.detailedShippersDTOToShippers(tempShipper);
            shippersRepo.save(convertedShipper);
        }
        return "Alla fraktföretag sparades ner";
    }
}
