package com.example.pensionat.controllers;

import com.example.pensionat.models.Room;
import com.example.pensionat.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/room")
public class RoomController {

    private final RoomService roomService;

    @RequestMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @RequestMapping("/{id}/remove")
    public String removeRoomById(@PathVariable Long id) {
        return roomService.removeRoomById(id);
    }
}