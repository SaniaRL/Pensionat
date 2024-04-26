package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.Enums.RoomType;
import backEnd1.pensionat.Models.Room;

public class RoomTypeConverter {
    public static RoomType convertFromInt(int value){
        return switch (value) {
            case 0 -> RoomType.SINGLE;
            case 1 -> RoomType.DOUBLE;
            case 2 -> RoomType.PREMIUM;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}