package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.shippers.DetailedShippersDTO;
import com.example.pensionat.dtos.shippers.SimpleShippersDTO;
import com.example.pensionat.models.Shippers;

public class ShippersConverter {

    public static SimpleShippersDTO shippersToSimpleShippersDTO(Shippers shipper) {
        return SimpleShippersDTO.builder()
                .id(shipper.getId())
                .companyName(shipper.getCompanyName())
                .phone(shipper.getPhone())
                .build();
    }

    public static Shippers detailedShippersDTOToShippers (DetailedShippersDTO shipper) {
        return Shippers.builder()
                .id(shipper.getId())
                .companyName(shipper.getCompanyName())
                .phone(shipper.getPhone())
                .build();
    }
}
