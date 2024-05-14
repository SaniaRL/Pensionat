package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.EventDTO;
import com.example.pensionat.models.events.*;

import java.time.LocalDateTime;

public class EventConverter {

    public static EventDTO eventToEventDTO(Event e) {
        EventDTO eventDTO = null;
        if (e instanceof RoomCleaningStarted started) {
            eventDTO = EventDTO.builder()
                               .typeOfEvent("Städning påbörjad")
                               .timeStamp(localDateTimeFormat(e.getTimeStamp()))
                               .cleaningByUser("av " + started.getCleaningByUser())
                               .build();
        } else if (e instanceof RoomCleaningFinished finished) {
            eventDTO = EventDTO.builder()
                               .typeOfEvent("Städning avslutad")
                               .timeStamp(localDateTimeFormat(e.getTimeStamp()))
                               .cleaningByUser("av " + finished.getCleaningByUser())
                               .build();
        } else if (e instanceof RoomOpened) {
            eventDTO = EventDTO.builder()
                               .typeOfEvent("Dörren öppnad")
                               .timeStamp(localDateTimeFormat(e.getTimeStamp()))
                               .cleaningByUser("")
                               .build();
        } else if (e instanceof RoomClosed) {
            eventDTO = EventDTO.builder()
                               .typeOfEvent("Dörren stängd")
                               .timeStamp(localDateTimeFormat(e.getTimeStamp()))
                               .cleaningByUser("")
                               .build();
        }
        return eventDTO;
    }

    public static String localDateTimeFormat(LocalDateTime dateTime) {
        LocalDateTime roundedDownDateTime = dateTime.truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
        return roundedDownDateTime.toString().replace("T", " ");
    }
}
