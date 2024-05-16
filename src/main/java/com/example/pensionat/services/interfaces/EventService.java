package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.EventDTO;
import com.example.pensionat.models.events.Event;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface EventService {

    void addToModel(String id, int currentPage, Model model);
    Page<EventDTO> getEventsByRoomId(String id, int pageNum);
    void initializeObjectMapper();
    Channel createChannel() throws Exception;
    ConnectionFactory createConnectionFactory();
    void setupConsumer(Channel channel) throws Exception;
    Event mapToEvent(String message);
    void saveEventToDatabase(Event event);
}
