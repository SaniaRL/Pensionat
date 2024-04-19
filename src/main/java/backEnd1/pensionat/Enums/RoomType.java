package backEnd1.pensionat.Enums;

import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE("Single"),
    DOUBLE("Double"),
    PREMIUM("Premium");

    @Override
    public String toString() {
        return super.toString();
    }

    public final String roomType;
    RoomType(String roomType) {
        this.roomType = roomType;
    }
}
