package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.EventDTO;
import com.example.pensionat.models.events.Event;
import com.example.pensionat.models.events.RoomCleaningStarted;
import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.convert.EventConverter;
import com.example.pensionat.services.interfaces.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public List<String> getEventsByRoomId(String id) {
        List<Event> list = eventRepo.findAll().stream().filter(e -> e.getRoomNo().equals(id)).toList();
        for (Event event : list) {
            System.out.println(event);
            System.out.println("Typ: " + event.getClass().getSimpleName());
        }
        return list.stream().map(EventConverter::eventToString).toList();
    }
}
