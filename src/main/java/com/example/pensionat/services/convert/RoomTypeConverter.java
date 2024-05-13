package com.example.pensionat.services.convert;

import com.example.pensionat.enums.RoomType;

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

    public static RoomType convertFromString(String value){
        return switch (value) {
            case "Single" -> RoomType.SINGLE;
            case "Double" -> RoomType.DOUBLE;
            case "Premium" -> RoomType.PREMIUM;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

}