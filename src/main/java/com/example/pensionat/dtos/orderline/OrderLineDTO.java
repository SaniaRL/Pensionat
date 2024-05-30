package com.example.pensionat.dtos.orderline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineDTO {
    //Actually RoomId
    private int id;
    private String roomType;
    private int extraBeds;
}
