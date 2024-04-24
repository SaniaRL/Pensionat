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
    private RoomType typeOfRoom;

}
