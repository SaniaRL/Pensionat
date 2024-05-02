package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.DetailedOrderLineDTO;
import com.example.pensionat.dtos.SimpleOrderLineDTO;
import com.example.pensionat.models.OrderLine;

import java.util.List;

public interface OrderLineService {

    List<SimpleOrderLineDTO> getAllOrderLines();
    String addOrderLine(OrderLine o);
    String addOrderLine(DetailedOrderLineDTO o);
    String removeOrderLineById(Long id);
    List<SimpleOrderLineDTO> getOrderLinesByBookingId(Long id);
    List<SimpleOrderLineDTO> findOrderLinesByBookingId(Long bookingId);

    }