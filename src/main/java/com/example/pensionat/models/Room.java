package com.example.pensionat.models;

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

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<OrderLine> orderLines = new ArrayList<>();

    public Room(Long id, int typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
        this.id = id;
    }

}
