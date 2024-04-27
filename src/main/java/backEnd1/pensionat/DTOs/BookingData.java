package backEnd1.pensionat.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class BookingData {

    private String name;
    private String email;
    private String startDate;
    private String endDate;
    private List<OrderLineDTO> chosenRooms;
}
