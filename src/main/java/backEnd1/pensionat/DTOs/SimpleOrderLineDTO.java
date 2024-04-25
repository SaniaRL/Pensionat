package backEnd1.pensionat.DTOs;

import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleOrderLineDTO {

    private Long bookingId;

    private RoomDTO room;

    private int extraBeds;
}
