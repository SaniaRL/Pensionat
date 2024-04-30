package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.Enums.RoomType;
import backEnd1.pensionat.Models.OrderLine;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.RoomRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class RoomServicelmplTest {
    @Mock
    private RoomRepo roomRepo;

    Long roomId1 = 301L;
    Long roomId2 = 401L;
    Room room = new Room(roomId1, 2);
    RoomDTO roomDTO = new RoomDTO(roomId2, RoomType.DOUBLE);

    @Test
    void getAllRooms() {
        when(roomRepo.findAll()).thenReturn(Arrays.asList(room));
        RoomServicelmpl service = new RoomServicelmpl(roomRepo);
        List<Room> actual = service.getAllRooms();
        assertEquals(1, actual.size());
    }

    @Test
    void addRoom() {
        RoomServicelmpl service = new RoomServicelmpl(roomRepo);
        String feedback = service.addRoom(room);
        assertTrue(feedback.equalsIgnoreCase("Room added"));
    }

    @Test
    void removeRoomById() {
        RoomServicelmpl service = new RoomServicelmpl(roomRepo);
        String feedback = service.removeRoomById(roomId1);
        assertTrue(feedback.equalsIgnoreCase("Room " + roomId1 + " removed"));
    }

    @Test
    void getRoomByID() {
    }

    @Test
    void findAvailableRooms() {
    }

    @Test
    void enoughRooms() {
    }
}