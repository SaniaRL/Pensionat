package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.EventDTO;
import com.example.pensionat.models.events.Event;
import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.convert.EventConverter;
import com.example.pensionat.services.interfaces.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;

    private String queueName = "a15b4de3-5b2d-4355-b21a-469593d26c86";

    private String host = "128.140.81.47";

    private String username = "djk47589hjkew789489hjf894";

    private String password = "sfdjkl54278frhj7";

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public void addToModel(String id, int currentPage, Model model){
        Page<EventDTO> eventList = getEventsByRoomId(id, currentPage);
        model.addAttribute("allRoomEvents", eventList.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", eventList.getTotalElements());
        model.addAttribute("totalPages", eventList.getTotalPages());
        model.addAttribute("roomNumber", id);
    }
    @Override
    public Page<EventDTO> getEventsByRoomId(String id, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 6, Sort.by("TimeStamp").descending());
        Page<Event> events = eventRepo.findByRoomNo(id, pageable);
        return events.map(EventConverter::eventToEventDTO);
    }

    @Override
    public ObjectMapper initializeObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Override
    public Channel createChannelFromConnection() throws Exception {
        ConnectionFactory factory = createConnectionFactory();
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

    @Override
    public ConnectionFactory createConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }

    @Override
    public void setupConsumer(Channel channel) throws Exception {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            saveEventToDatabase(mapToEvent(message));
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }

    @Override
    public Event mapToEvent(String message) {
        ObjectMapper mapper = initializeObjectMapper();
        try {
            return mapper.readValue(message, Event.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveEventToDatabase(Event event) {
        eventRepo.save(event);
    }
}