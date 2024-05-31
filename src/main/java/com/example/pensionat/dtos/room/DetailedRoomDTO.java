package com.example.pensionat.dtos.room;

import com.example.pensionat.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedRoomDTO {

    private long id;
    private RoomType roomType;
    private double price;
}
