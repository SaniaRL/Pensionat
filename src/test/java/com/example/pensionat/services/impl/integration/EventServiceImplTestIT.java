package com.example.pensionat.services.impl.integration;

import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.interfaces.EventService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EventServiceImplTestIT {

    @Autowired
    EventService sutEvent;
    @Autowired
    EventRepo eventRepoTest;
    List<String> tempStoredMessages = new ArrayList<>();

    // Define DeliverCallback as an instance variable using a lambda expression
    private final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = sutEvent.extractMessage(delivery);
        System.out.println(" [x] Received '" + message + "'");
        tempStoredMessages.add(message);
        sutEvent.saveEventToDatabase(sutEvent.mapToEvent(message));
    };

    // Define setupConsumer as an instance variable using a lambda expression that executes setup code
    private final SetupConsumer setupConsumer = channel -> {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicConsume("a15b4de3-5b2d-4355-b21a-469593d26c86", true, deliverCallback, consumerTag -> {});
    };

    @FunctionalInterface
    interface SetupConsumer {
        void setup(Channel channel) throws Exception;
    }

    @Test
    void channelCreatedAndMessagesReceivedAndStoredInDB() throws Exception {
        Channel channel = sutEvent.createChannelFromConnection();
        setupConsumer.setup(channel); // Use the instance variable to setup the consumer

        Thread.sleep(1000); //Utan denna buggar testerna ibland?

        assertTrue(tempStoredMessages.stream().anyMatch(msg -> msg.contains("type") && msg.contains("TimeStamp") && msg.contains("RoomNo")));
        assertTrue(tempStoredMessages.get(0).contains("type"));
    }
}
