package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.EventDTO;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public interface EventService {

    void addToModel(String id, int currentPage, Model model);
    Page<EventDTO> getEventsByRoomId(String id, int pageNum);
}
