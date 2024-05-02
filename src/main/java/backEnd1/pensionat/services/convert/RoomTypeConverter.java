package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.Enums.RoomType;

public class RoomTypeConverter {
    public static RoomType convertFromInt(int value){
        return switch (value) {
            case 0 -> RoomType.SINGLE;
            case 1 -> RoomType.DOUBLE;
            case 2 -> RoomType.PREMIUM;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    public static int convertToInt(RoomType roomType){
        return switch (roomType) {
            case SINGLE -> 0;
            case DOUBLE -> 1;
            case PREMIUM -> 2;
        };
    }
}