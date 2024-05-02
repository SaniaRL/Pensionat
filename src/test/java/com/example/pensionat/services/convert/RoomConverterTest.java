package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.RoomDTO;
import com.example.pensionat.enums.RoomType;
import com.example.pensionat.models.Room;
import com.example.pensionat.services.convert.RoomConverter;
import com.example.pensionat.services.convert.RoomTypeConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomConverterTest {
    Room room = new Room(301L, 2);
    RoomDTO roomDTO = new RoomDTO(401L, RoomType.DOUBLE);

    @Test
    void roomDtoToRoom() {
        Room actual = RoomConverter.roomDtoToRoom(roomDTO);

        assertEquals(actual.getId(), roomDTO.getId());
        assertEquals(actual.getTypeOfRoom(), RoomTypeConverter
                .convertToInt(roomDTO.getRoomType()));
    }

    @Test
    void roomToRoomDto() {
        RoomDTO actual = RoomConverter.roomToRoomDto(room);

        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getRoomType(), RoomTypeConverter
                .convertFromInt(room.getTypeOfRoom()));
    }
}