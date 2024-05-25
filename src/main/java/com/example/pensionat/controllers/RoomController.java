package com.example.pensionat.controllers;

import com.example.pensionat.services.interfaces.EventService;
import com.example.pensionat.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/room")
@PreAuthorize("isAuthenticated()")
public class RoomController {

    private final RoomService roomService;
    private final EventService eventService;

    @GetMapping ("/all/")
    public String getAllRooms(Model model) {
        int currentPage = 1;
        roomService.addToModel(currentPage, model);
        return "allRooms";
    }

    @GetMapping(value = "/all/", params = "page")
    public String roomsByPage(Model model, @RequestParam int page){
        roomService.addToModel(page, model);
        return "allRooms";
    }

    @GetMapping ("/eventlist/{id}")
    public String getEventList(@PathVariable Long id, Model model) {
        int currentPage = 1;
        eventService.addToModel(id.toString(), currentPage, model);
        return "eventListRoom";
    }

    @GetMapping(value = "/eventlist/{id}", params = "page")
    public String eventListByPage(@PathVariable Long id, Model model, @RequestParam int page){
        eventService.addToModel(id.toString(), page, model);
        return "eventListRoom";
    }
}