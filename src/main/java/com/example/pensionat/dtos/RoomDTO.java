package com.example.pensionat.dtos;

import com.example.pensionat.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {

    private long id;
    private RoomType roomType;
    private double price;

    public RoomDTO(Long id, RoomType roomType) {
        this.id = id;
        this.roomType = roomType;
        this.price = generatePrice(roomType);
    }

    public RoomDTO(RoomType roomType) {
        this.roomType = roomType;
        this.price = generatePrice(roomType);
    }

    public RoomDTO(RoomType roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    private double generatePrice(RoomType rt) {
        return switch (rt) {
            case SINGLE -> 750.00;
            case DOUBLE -> 1000.00;
            case PREMIUM -> 500.00;
        };
    }

}
