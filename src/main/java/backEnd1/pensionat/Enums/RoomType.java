package backEnd1.pensionat.Enums;

import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE("Single", 1, 1),
    DOUBLE("Double",2, 3),
    PREMIUM("Premium",2, 4);

    public final String roomType;
    public final int defaultNumberOfBeds;
    public final int maxNumberOfBeds;

    public String toString(){
        return roomType;
    }

    RoomType(String roomType, int defaultNumberOfBeds, int maxNumberOfBeds) {
        this.roomType = roomType;
        this.defaultNumberOfBeds = defaultNumberOfBeds;
        this.maxNumberOfBeds = maxNumberOfBeds;
    }
}