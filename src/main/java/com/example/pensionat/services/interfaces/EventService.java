package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.EventDTO;
import com.example.pensionat.models.events.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public interface EventService {

    void addToModel(String id, int currentPage, Model model);
    Page<EventDTO> getEventsByRoomId(String id, int pageNum);
    ObjectMapper initializeObjectMapper();
    Channel setupChannel() throws Exception;
    ConnectionFactory createConnectionFactory();
    List<String> setupConsumer(Channel channel) throws Exception;
    Event mapToEvent(String message);
    void saveEventToDatabase(Event event);
}
