package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.Enums.RoomType;
import backEnd1.pensionat.Models.Room;
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
    }
}