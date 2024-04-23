package backEnd1.pensionat.Enums;

import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE(1, 1),
    DOUBLE(2, 3),
    PREMIUM(2, 4);

    public final int defaultNumberOfBeds;
    public final int maxNumberOfBeds;
    RoomType(int defaultNumberOfBeds, int maxNumberOfBeds) {
        this.defaultNumberOfBeds = defaultNumberOfBeds;
        this.maxNumberOfBeds = maxNumberOfBeds;
    }
}
