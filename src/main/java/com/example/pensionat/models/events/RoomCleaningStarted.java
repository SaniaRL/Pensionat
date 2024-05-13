package com.example.pensionat.models.events;

import jakarta.persistence.Entity;

@Entity
public class RoomCleaningStarted extends Event {
    private String CleaningByUser;
}
