package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.room.DetailedRoomDTO;
import com.example.pensionat.dtos.room.RoomDTO;
import com.example.pensionat.dtos.orderline.SimpleOrderLineDTO;
import com.example.pensionat.models.Room;

public class RoomConverter {
    public static Room roomDtoToRoom(RoomDTO room) {
        return Room.builder().id(room.getId())
                             .typeOfRoom(RoomTypeConverter.convertToInt(room.getRoomType()))
                             .build();
    }
    public static RoomDTO roomToRoomDto(Room room) {
        return RoomDTO.builder().id(room.getId())
                                .roomType(RoomTypeConverter.convertFromInt(room.getTypeOfRoom()))
                                .build();
    }

    public static DetailedRoomDTO roomToDetailedRoomDto(Room room) {
        return DetailedRoomDTO.builder().id(room.getId())
                .roomType(RoomTypeConverter.convertFromInt(room.getTypeOfRoom()))
                .price(room.getPrice())
                .build();
    }

    public static RoomDTO orderLineToRoomDTO(SimpleOrderLineDTO orderLine) {
        return RoomDTO.builder()
                .id(orderLine.getRoom().getId())
                .roomType(orderLine.getRoom().getRoomType())
                .build();
    }
}