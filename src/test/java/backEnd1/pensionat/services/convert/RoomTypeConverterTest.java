package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.Enums.RoomType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTypeConverterTest {
    /*
    public static RoomType convertFromInt(int value){
        return switch (value) {
            case 0 -> RoomType.SINGLE;
            case 1 -> RoomType.DOUBLE;
            case 2 -> RoomType.PREMIUM;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
     */

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
    }
}