package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.RoomDTO;
import com.example.pensionat.dtos.SimpleOrderLineDTO;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.OrderLine;

public class OrderLineConverter {

    public static OrderLine simpleOrderLineDtoToOrderLine(SimpleOrderLineDTO orderLine, Booking b) {
        return OrderLine.builder().booking(b).room(RoomConverter.roomDtoToRoom(orderLine.getRoom()))
                .extraBeds(orderLine.getExtraBeds()).build();
    }
    public static SimpleOrderLineDTO orderLineTosimpleOrderLineDto(OrderLine orderLine) {
        return SimpleOrderLineDTO.builder().bookingId(orderLine.getBooking().getId())
                .room(RoomConverter.roomToRoomDto(orderLine.getRoom()))
                .extraBeds(orderLine.getExtraBeds()).build();
    }

    public static SimpleOrderLineDTO roomToSimpleOrderLineDTO(RoomDTO roomDTO) {
        int extraBeds = roomDTO.getRoomType().defaultNumberOfBeds;
        return SimpleOrderLineDTO.builder()
                .room(roomDTO)
                .extraBeds(extraBeds).build();
    }
}