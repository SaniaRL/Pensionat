package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.BookingFormQueryDTO;
import com.example.pensionat.dtos.RoomDTO;
import com.example.pensionat.dtos.SimpleOrderLineDTO;
import com.example.pensionat.models.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();
    String removeRoomById(Long id);
    RoomDTO getRoomByID(Long Id);
    List<RoomDTO> findAvailableRooms(BookingFormQueryDTO query);

    List<SimpleOrderLineDTO> filterNotInChosenRooms(BookingFormQueryDTO query,
                                                    List<SimpleOrderLineDTO> chosenRooms);

    String enoughRooms(BookingFormQueryDTO query, List<RoomDTO> rooms);
}