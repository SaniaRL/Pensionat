package backEnd1.pensionat.DTOs;

import backEnd1.pensionat.Enums.RoomType;
import lombok.Data;


@Data
public class DetailedBookingData {
    private String name;
    private String email;
    private String startDate;
    private String endDate;
    private Long roomId;
    private RoomType roomType;
    private int extraBeds;
}
