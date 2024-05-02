package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.DetailedOrderLineDTO;
import com.example.pensionat.dtos.SimpleOrderLineDTO;
import com.example.pensionat.models.OrderLine;

import java.util.List;

public interface OrderLineService {

    List<OrderLine> getAllOrderLines();
    String addOrderLine(OrderLine o);
    String addOrderLine(DetailedOrderLineDTO o);
    String removeOrderLineById(Long id);
    String addOrderLineFromSimpleOrderLineDto(SimpleOrderLineDTO orderLine);
    List<SimpleOrderLineDTO> getOrderLinesByBookingId(Long id);

    List<DetailedOrderLineDTO> getDetailedOrderLinesByBookingId(Long id);

    List<SimpleOrderLineDTO> findOrderLinesByBookingId(Long bookingId);


    }