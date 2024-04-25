package backEnd1.pensionat.DTOs;

import backEnd1.pensionat.Enums.RoomType;
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

    public String getRoomTypeName() {
        return roomType.getRoomType();
    }

    public int getDefaultNumberOfBeds() {
        return roomType.getDefaultNumberOfBeds();
    }

    public int getMaxNumberOfBeds() {
        return roomType.getMaxNumberOfBeds();
    }
}
