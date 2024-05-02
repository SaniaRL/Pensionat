package com.example.pensionat.controllers;

import com.example.pensionat.dtos.RoomDTO;
import com.example.pensionat.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/room")
public class RoomController {

    private final RoomService roomService;

//    @RequestMapping("/all")
//    public List<RoomDTO> getAllRooms() {
//        return roomService.getAllRooms();
//    }

    @RequestMapping("/{id}/remove")
    public String removeRoomById(@PathVariable Long id) {
        return roomService.removeRoomById(id);
    }
}