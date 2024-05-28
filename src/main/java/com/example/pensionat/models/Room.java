package com.example.pensionat.models;

import com.example.pensionat.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    private Long id;

    @NotNull(message = "Type of room is mandatory")
    private int typeOfRoom;

    @NotNull(message = "Price of room is mandatory")
    private double price;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<OrderLine> orderLines;

    public Room(Long id, int typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
        this.id = id;
        this.price = generatePrice(typeOfRoom);
        this.orderLines = new ArrayList<>();
    }

    private double generatePrice(int rt) {
        return switch (rt) {
            case 1 -> 750.00;
            case 2 -> 1000.00;
            default -> 500.00;
        };
    }

}
