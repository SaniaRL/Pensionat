package com.example.pensionat.services.impl.integration;

import com.example.pensionat.repositories.EventRepo;
import com.example.pensionat.services.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventServiceImplTestIT {

    @Autowired
    private EventService sutEvent;
    @Autowired
    private EventRepo eventRepo;
    @MockBean
    private JavaMailSender emailSender;

    //Nedan test fungerar BARA när det finns meddelande i message-queue för EVENTS på bed&basse.
    //Går ej riktigt att testa på rätt sätt.
    /*
    @Test
    void setupChannelCorrectAndSetupConsumerFetchesAndStoresDataCorrectly() throws Exception {
        eventRepo.deleteAll();
        Channel channel = sutEvent.setupChannel();

        List<String> tempStoredMes = sutEvent.setupConsumer(channel);
        List<Event> events = eventRepo.findAll();

        Thread.sleep(10000); //Ibland börjar asserts köra innan arrange och act är klara om man hämtar väldigt många meddelanden.
        //Alt. till detta verkar vara countDownLatch men avvaktar med ev. Implementation.

        assertTrue(tempStoredMes.stream().anyMatch(eventMessage -> eventMessage.contains("type") && eventMessage.contains("TimeStamp") && eventMessage.contains("RoomNo")));
        for (int i = 0; i < tempStoredMes.size(); i++) {
            if (tempStoredMes.get(i).contains("RoomCleaningStarted") || tempStoredMes.get(i).contains("RoomCleaningFinished")) {
                assertTrue(tempStoredMes.get(i).contains("CleaningByUser"));
            }
        }
        assertTrue(eventRepo.count() > 0);
        for (Event event : events) {
            assertNotNull(event.getTimeStamp());
            assertNotNull(event.getRoomNo());
            assertNotNull(event.getId());
        }
        assertEquals(eventRepo.count(), tempStoredMes.size());
    }
     */
}
