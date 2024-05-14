package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.DetailedRoomDTO;
import com.example.pensionat.dtos.EventDTO;
import com.example.pensionat.models.Room;
import com.example.pensionat.models.events.Event;
import com.example.pensionat.models.events.RoomCleaningStarted;
import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.convert.EventConverter;
import com.example.pensionat.services.interfaces.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }


    @Override
    public void addToModel(String id, int currentPage, Model model){
        Page<String> eventList = getEventsByRoomId(id, currentPage);
        model.addAttribute("allRoomEvents", eventList.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", eventList.getTotalElements());
        model.addAttribute("totalPages", eventList.getTotalPages());
        model.addAttribute("roomNumber", id);
    }
    @Override
    public Page<String> getEventsByRoomId(String id, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 6);
        Page<Event> events = eventRepo.findByRoomNo(id, pageable);
        return events.map(EventConverter::eventToString);
    }
}
