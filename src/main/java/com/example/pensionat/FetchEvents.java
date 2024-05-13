package com.example.pensionat;

import com.example.pensionat.models.events.Event;
import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.interfaces.ShippersService;
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
    EventRepo eventRepo;

    String queueName = "a15b4de3-5b2d-4355-b21a-469593d26c86"; //Bed & Basse
    List <String> eventList = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("128.140.81.47");
        factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                Event event = mapper.readValue(message, Event.class); // Deserialize JSON to Event
                eventRepo.save(event); // Save the event immediately
            } catch (Exception e) {
                e.printStackTrace(); // Log any deserialization or database errors
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        //eventRepo.saveAll(eventList);
    }
}
