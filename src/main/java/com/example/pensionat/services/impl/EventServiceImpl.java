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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;

    private static final String QUEUE_NAME = "a15b4de3-5b2d-4355-b21a-469593d26c86";
    private static final String HOST = "128.140.81.47";
    private static final String USERNAME = "djk47589hjkew789489hjf894";
    private static final String PASSWORD = "sfdjkl54278frhj7";

    private ObjectMapper mapper;

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
        initializeObjectMapper();
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
    public void initializeObjectMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void startListeningForEvents() throws Exception {
        ConnectionFactory factory = createConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            setupConsumer(channel);
        }
    }

    @Override
    public ConnectionFactory createConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        return factory;
    }

    @Override
    public void setupConsumer(Channel channel) throws Exception {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            processMessage(message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }

    @Override
    public void processMessage(String message) {
        try {
            Event event = mapper.readValue(message, Event.class);
            eventRepo.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}