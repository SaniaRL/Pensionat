package com.example.pensionat.dtos.orderline;

import com.example.pensionat.dtos.room.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleOrderLineDTO {

    private Long bookingId;
    private RoomDTO room;
    private int extraBeds;
}
