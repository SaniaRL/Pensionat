package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.DTOs.BookingFormQueryDTO;
import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.DTOs.SimpleOrderLineDTO;
import backEnd1.pensionat.Models.Room;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();
    String addRoom(Room r);
    String removeRoomById(Long id);
    RoomDTO getRoomByID(Long Id);
    List<RoomDTO> findAvailableRooms(BookingFormQueryDTO query);

    List<SimpleOrderLineDTO> filterNotInChosenRooms(BookingFormQueryDTO query,
                                                    List<SimpleOrderLineDTO> chosenRooms);

    String enoughRooms(BookingFormQueryDTO query, List<RoomDTO> rooms);
}