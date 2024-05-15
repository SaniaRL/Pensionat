package com.example.pensionat;

import com.example.pensionat.models.events.Event;
import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.interfaces.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@ComponentScan
public class FetchEvents implements CommandLineRunner {

    @Autowired
    private EventService eventService;

    @Override
    public void run(String... args) throws Exception {
        eventService.startListeningForEvents();
    }
}
