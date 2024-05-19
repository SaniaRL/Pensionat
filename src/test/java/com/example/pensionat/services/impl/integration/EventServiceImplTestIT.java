package com.example.pensionat.services.impl.integration;

import com.example.pensionat.models.events.Event;
import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.interfaces.EventService;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class EventServiceImplTestIT {

    @Autowired
    private EventService sutEvent;
    @Autowired
    private EventRepo eventRepo;

    //BRYT UT SÅ ATT JAG TESTAR LISTAN I EN ANNAN METOD SEN DB I EN ANNAN!?

    @Test
    void setupChannelCorrectAndSetupConsumerFetchesAndStoresDataCorrectly() throws Exception {
        eventRepo.deleteAll();
        List<Event> events;
        Channel channel3 = sutEvent.setupChannel();

        sutEvent.setupConsumer(channel3);
        events = eventRepo.findAll();
        List <String> tempStoredMes = sutEvent.getTempStoredMessages();

        Thread.sleep(10000);

        assertTrue(tempStoredMes.get(0).contains("type"));
        assertTrue(tempStoredMes.get(0).contains("RoomNo"));
        assertTrue(tempStoredMes.stream().anyMatch(msg -> msg.contains("type") && msg.contains("TimeStamp") && msg.contains("RoomNo")));

        for (int i = 0; i < tempStoredMes.size(); i++) {
            if (tempStoredMes.get(i).contains("RoomCleaningStarted") || tempStoredMes.get(0).contains("RoomCleaningFinished")) {
                assertTrue(tempStoredMes.get(i).contains("CleaningByUser"));
            }
        }
        assertTrue(eventRepo.count() > 0);
        for (Event event : events) {
            assertTrue(event.getTimeStamp() != null);
            assertTrue(event.getRoomNo() != null);
            assertTrue(event.getId() != null);
        }
    }

 /*   public DeliverCallback createDeliverCallbackTest() {
        return (consumerTag, delivery) -> {
            String message = sutEvent.extractMessage(delivery);
            System.out.println(" [x] Received '" + message + "'"); //Kan tas bort sen
            tempStoredMessages.add(message);
        };
    }

    @Test
    void channelWillBeCreatedAndEventMessagesWillBeFetched() throws Exception {
        eventRepo.deleteAll();
        Channel channel = sutEvent.createChannelFromConnection();

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C"); //Ta bort sen
        DeliverCallback deliverCallback = createDeliverCallbackTest();
        channel.basicConsume("a15b4de3-5b2d-4355-b21a-469593d26c86", true, deliverCallback, consumerTag -> {
        });

        Thread.sleep(10000); //Utan denna buggar testerna ibland. Assert görs innan allt klart.

        assertTrue(tempStoredMessages.stream().anyMatch(msg -> msg.contains("type") && msg.contains("TimeStamp") && msg.contains("RoomNo")));
        assertTrue(tempStoredMessages.get(0).contains("type"));
        assertTrue(tempStoredMessages.get(0).contains("RoomNo"));

        for (int i = 0; i < tempStoredMessages.size(); i++) {
            if (tempStoredMessages.get(i).contains("RoomCleaningStarted") || tempStoredMessages.get(0).contains("RoomCleaningFinished")) {
                assertTrue(tempStoredMessages.get(i).contains("CleaningByUser"));
            }
        }
    }
    @Test
    void eventMessagesShouldBeStoredInDatabase() throws Exception {
        eventRepo.deleteAll();
        List<Event> events;
        Channel channel2 = sutEvent.createChannelFromConnection();

        sutEvent.setupConsumer(channel2);
        events = eventRepo.findAll();

        Thread.sleep(15000);

        for (Event event : events) {
            assertTrue(event.getTimeStamp() != null);
            assertTrue(event.getRoomNo() != null);
            assertTrue(event.getId() != null);
        }
        assertTrue(eventRepo.count() > 0);
    }
    */
}
