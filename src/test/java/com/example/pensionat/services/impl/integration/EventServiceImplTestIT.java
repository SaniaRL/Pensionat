package com.example.pensionat.services.impl.integration;

import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.interfaces.EventService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EventServiceImplTestIT {

    @Autowired
    EventService sutEvent;
    List<String> tempStoredMessages = new ArrayList<>();

    public DeliverCallback createDeliverCallbackTest() {
        return (consumerTag, delivery) -> {
            String message = sutEvent.extractMessage(delivery);
            System.out.println(" [x] Received '" + message + "'"); //Kan tas bort sen
            tempStoredMessages.add(message);
        };
    }

    @Test
    void channelWillBeCreatedAndMessagesWillBeFetched() throws Exception {
        Channel channel = sutEvent.createChannelFromConnection();

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = createDeliverCallbackTest();
        channel.basicConsume("a15b4de3-5b2d-4355-b21a-469593d26c86", true, deliverCallback, consumerTag -> {
        });

        Thread.sleep(10000); //Utan denna buggar testerna ibland? Assert görs innan allt klart.

        //Ta bort if-sats så det failar om man ej har en kö?
      //  if (!tempStoredMessages.isEmpty()){
        assertTrue(tempStoredMessages.stream().anyMatch(msg -> msg.contains("type") && msg.contains("TimeStamp") && msg.contains("RoomNo")));
        assertTrue(tempStoredMessages.get(0).contains("type"));
        assertTrue(tempStoredMessages.get(0).contains("RoomNo"));
     //   }
        for (int i = 0; i < tempStoredMessages.size(); i++) {
            if (tempStoredMessages.get(i).contains("RoomCleaningStarted") || tempStoredMessages.get(0).contains("RoomCleaningFinished")) {
                assertTrue(tempStoredMessages.get(i).contains("CleaningByUser"));
            }
        }
    }
}
