package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.shippers.DetailedShippersDTO;
import com.example.pensionat.dtos.shippers.SimpleShippersDTO;
import com.example.pensionat.models.Shippers;
import com.example.pensionat.repositories.ShippersRepo;
import com.example.pensionat.services.convert.ShippersConverter;
import com.example.pensionat.services.interfaces.ShippersService;
import com.example.pensionat.services.providers.ShippersStreamProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;


@Service
public class ShippersServiceImpl implements ShippersService {

    private final ShippersRepo shippersRepo;
    private final ShippersStreamProvider shippersStreamProvider;

    public ShippersServiceImpl(ShippersRepo shippersRepo, ShippersStreamProvider shippersStreamProvider) {
        this.shippersRepo = shippersRepo;
        this.shippersStreamProvider  = shippersStreamProvider;
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

    @Override
    public DetailedShippersDTO[] getShippersToArray() throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());
        InputStream stream = shippersStreamProvider.getDataStream();

        DetailedShippersDTO[] shippersArray = objMapper.readValue(stream, DetailedShippersDTO[].class);
        return shippersArray;
    }
}
