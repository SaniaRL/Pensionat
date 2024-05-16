package com.example.pensionat.dtos;

import com.example.pensionat.enums.RoomType;
import com.example.pensionat.services.convert.RoomTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineDTO {
    private int id;
    private String roomType;
    private int extraBeds;
}
