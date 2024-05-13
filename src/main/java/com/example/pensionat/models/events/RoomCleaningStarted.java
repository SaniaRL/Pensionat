package com.example.pensionat.models.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
public class RoomCleaningStarted extends Event {
    @JsonProperty("RoomNo")
    private String RoomNo;
    @JsonProperty("CleaningByUser")
    private String CleaningByUser;
}
