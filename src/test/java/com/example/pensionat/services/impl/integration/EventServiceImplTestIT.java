package com.example.pensionat.services.impl.integration;

import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventServiceImplTestIT {

    EventServiceImpl sut;

    @Mock
    private EventRepo eventRepo;

    @BeforeEach()
    void setup() {
        sut = new EventServiceImpl();
    }

    @Test
    void

    /*
    public void processMessage(String message) {
        try {
            Event event = mapper.readValue(message, Event.class);
            eventRepo.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */
}
