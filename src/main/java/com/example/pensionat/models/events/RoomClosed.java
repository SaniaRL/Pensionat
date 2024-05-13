package com.example.pensionat.models.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;

@Entity
public class RoomClosed extends Event {
    @JsonProperty("RoomNo")
    private String RoomNo;
}
