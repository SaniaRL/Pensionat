package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.BookingFormQueryDTO;
import com.example.pensionat.dtos.DetailedRoomDTO;
import com.example.pensionat.dtos.RoomDTO;
import com.example.pensionat.dtos.SimpleOrderLineDTO;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public interface RoomService {

    void addToModel(int currentPage, Model model);
    Page<DetailedRoomDTO> getAllRoomsPage(int pageNum);
    RoomDTO getRoomByID(Long Id);
    List<RoomDTO> findAvailableRooms(BookingFormQueryDTO query);

    List<SimpleOrderLineDTO> filterNotInChosenRooms(BookingFormQueryDTO query,
                                                    List<SimpleOrderLineDTO> chosenRooms);
    String enoughRooms(BookingFormQueryDTO query, List<RoomDTO> rooms);

}