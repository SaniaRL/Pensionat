package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.EventDTO;

import java.util.List;

public interface EventService {

    List<String> getEventsByRoomId(String id);
}
