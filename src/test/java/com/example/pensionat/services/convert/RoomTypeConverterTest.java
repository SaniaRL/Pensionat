package com.example.pensionat.services.convert;

import com.example.pensionat.enums.RoomType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTypeConverterTest {

    @Test
    void convertFromInt() {
        RoomType roomType0 = RoomTypeConverter.convertFromInt(0);
        RoomType roomType1 = RoomTypeConverter.convertFromInt(1);
        RoomType roomType2 = RoomTypeConverter.convertFromInt(2);

        assertEquals(roomType0, RoomType.SINGLE);
        assertEquals(roomType1, RoomType.DOUBLE);
        assertEquals(roomType2, RoomType.PREMIUM);

        assertNotEquals(roomType0, RoomType.PREMIUM);
    }

    @Test
    void convertToInt() {
        int type0 = RoomTypeConverter.convertToInt(RoomType.SINGLE);
        int type1= RoomTypeConverter.convertToInt(RoomType.DOUBLE);
        int type2 = RoomTypeConverter.convertToInt(RoomType.PREMIUM);

        assertEquals(type0, 0);
        assertEquals(type1, 1);
        assertEquals(type2, 2);

        assertNotEquals(type0, 1);
    }
}