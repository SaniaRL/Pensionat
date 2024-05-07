package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.SimpleShippersDTO;
import com.example.pensionat.models.Shippers;

public class ShippersConverter {

    public static SimpleShippersDTO shippersToSimpleShippersDTO(Shippers shipper) {
        return SimpleShippersDTO.builder()
                .id(shipper.getId())
                .companyName(shipper.getCompanyName())
                .phone(shipper.getPhone())
                .build();
    }
/* TempYolo
    public static Shippers simpleShippersDTOToShippers(SimpleShippersDTO shipper) {
        return Shippers.builder()
                .id(shipper.getId())
                .companyName(shipper.getCompanyName())
                .phone(shipper.getPhone())
                .build();
    } */
}
