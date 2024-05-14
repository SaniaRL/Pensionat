package com.example.pensionat.controllers;

import com.example.pensionat.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping ("/allrooms")
    public String getAllRooms(Model model) {
        model.addAttribute("allRooms", roomService.getAllRooms());
        return "allRooms";
    }

    @GetMapping ("/eventlist/{id}")
    public String getEventList(@PathVariable Long id, Model model) {
        return "allRooms";
    }
}
