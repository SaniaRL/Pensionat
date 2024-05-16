package com.example.pensionat.services.impl.unit;

import com.example.pensionat.repositories.EventRepo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceImplTest {

    @Mock
    private EventRepo eventRepo;

    @Test
    void addToModel() {
    }

    @Test
    void getEventsByRoomId() {
    }

    @Test
    void initializeObjectMapper() {

    }

    @Test
    void createChannel() {

    }

    @Test
    void createConnectionFactory() {

    }

    @Test
    void setupConsumer() {

    }

    @Test
    void processMessage() {

    }
}