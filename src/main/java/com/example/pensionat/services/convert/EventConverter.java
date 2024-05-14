package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.EventDTO;
import com.example.pensionat.models.events.*;

import java.time.LocalDateTime;

public class EventConverter {

    public static String eventToString(Event e) {
        String eventText = "";
        if (e instanceof RoomCleaningStarted started) {
            eventText += "Städning påbörjad av " + started.getCleaningByUser() + " "
                        + localDateTimeFormat(e.getTimeStamp());
        } else if (e instanceof RoomCleaningFinished finished) {
            eventText += "Städning avslutad av " + finished.getCleaningByUser() + " "
                    + localDateTimeFormat(e.getTimeStamp());
        } else if (e instanceof RoomOpened) {
            eventText += "Dörren öppnad " + localDateTimeFormat(e.getTimeStamp());
        } else if (e instanceof RoomClosed) {
            eventText += "Dörren stängd " + localDateTimeFormat(e.getTimeStamp());
        }
        return eventText;
    }

    public static String localDateTimeFormat(LocalDateTime dateTime) {
        LocalDateTime roundedDownDateTime = dateTime.truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
        return roundedDownDateTime.toString().replace("T", " ");
    }
}
