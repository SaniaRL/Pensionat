package com.example.pensionat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLine {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private Booking booking;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Room room;

    @NotNull
    private int extraBeds;

    public OrderLine(Booking booking, Room room, int extraBeds) {
        this.booking = booking;
        this.room = room;
        this.extraBeds = extraBeds;
    }
}
