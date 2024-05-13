package com.example.pensionat.models.events;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class RoomCleaningFinished extends Event {
    private String CleaningByUser;
}
