package com.example.pensionat.models.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
public class RoomOpened extends Event {
    @JsonProperty("RoomNo")
    private String RoomNo;
}
