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

    @GetMapping ("/all")
    public String getAllRooms(Model model) {
        int currentPage = 1;
        roomService.addToModel(currentPage, model);
        return "allRooms";
    }

    @GetMapping("/all/{pageNumber}")
    public String roomsByPage(Model model, @PathVariable("pageNumber") int currentPage){
        roomService.addToModel(currentPage, model);
        return "allRooms";
    }

    @GetMapping ("/eventlist/{id}")
    public String getEventList(@PathVariable Long id, Model model) {
        return "allRooms";
    }
}