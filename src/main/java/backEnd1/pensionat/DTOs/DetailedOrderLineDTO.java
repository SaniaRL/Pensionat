package backEnd1.pensionat.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedOrderLineDTO {

    int id;
    int extraBeds;
    DetailedBookingDTO booking;
    RoomDTO room;

    public DetailedOrderLineDTO(int extraBeds, DetailedBookingDTO booking, RoomDTO room) {
        this.extraBeds = extraBeds;
        this.booking = booking;
        this.room = room;

    }

}
