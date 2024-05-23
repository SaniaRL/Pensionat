package com.example.pensionat;

import com.example.pensionat.services.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.rabbitmq.client.Channel;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class FetchEvents implements CommandLineRunner {

    @Autowired
    private EventService eventService;

    @Override
    public void run(String... args) throws Exception {
        Channel channel = eventService.setupChannel();
        eventService.setupConsumer(channel);
    }
}