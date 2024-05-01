package backEnd1.pensionat.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedBookingFormQueryDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private int beds;
    private int rooms;
    private List<SimpleOrderLineDTO> chosenRooms;

    public BookingFormQueryDTO getBookingFormQueryDTO() {
        return new BookingFormQueryDTO(startDate, endDate, beds, rooms);
    }
}