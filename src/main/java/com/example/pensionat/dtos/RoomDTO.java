package com.example.pensionat.dtos;

import com.example.pensionat.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {

    private long id;
    private RoomType roomType;
}
