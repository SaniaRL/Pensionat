package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.RoomDTO;
import com.example.pensionat.enums.RoomType;
import com.example.pensionat.models.Room;
import com.example.pensionat.repositories.RoomRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class RoomServicelmplTest {
    @Mock
    private RoomRepo roomRepo;

    Long roomId = 301L;
    Room room = new Room(roomId, 2);
    RoomDTO roomDTO = new RoomDTO(roomId, RoomType.DOUBLE);

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
        String feedback = service.removeRoomById(roomId);
        assertTrue(feedback.equalsIgnoreCase("Room " + roomId + " removed"));
    }

    @Test
    void getRoomByID() {
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));
        RoomServicelmpl service = new RoomServicelmpl(roomRepo);
        RoomDTO actual = service.getRoomByID(roomId);
        assertEquals(actual.getId(), roomId);
    }

    @Test
    void findAvailableRooms() {
        //TODO
    }


    @Test
    void enoughRooms() {
        //TODO
    }
}