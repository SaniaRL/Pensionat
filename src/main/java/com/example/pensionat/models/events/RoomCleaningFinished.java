package com.example.pensionat.models.events;

import jakarta.persistence.Entity;

@Entity
public class RoomCleaningFinished extends Event {
    private String CleaningByUser;
}
