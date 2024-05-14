package com.example.pensionat.controllers;

import com.example.pensionat.dtos.DetailedRoomDTO;
import com.example.pensionat.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping ("/allrooms")
    public String getAllRooms(Model model) {
        model.addAttribute("allCustomers", roomService.getAllRooms());
        return "Hello";
    }
}
