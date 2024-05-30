package com.example.pensionat.dtos.orderline;

import com.example.pensionat.dtos.booking.DetailedBookingDTO;
import com.example.pensionat.dtos.room.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedOrderLineDTO {

    Long id;
    int extraBeds;
    DetailedBookingDTO booking;
    RoomDTO room;
    double discount;

    public DetailedOrderLineDTO(int extraBeds, DetailedBookingDTO booking, RoomDTO room) {
        this.extraBeds = extraBeds;
        this.booking = booking;
        this.room = room;
        this.discount = 0;
    }

    public DetailedOrderLineDTO(int extraBeds, DetailedBookingDTO booking, RoomDTO room, double discount) {
        this.extraBeds = extraBeds;
        this.booking = booking;
        this.room = room;
        this.discount = discount;
    }
}