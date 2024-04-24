package backEnd1.pensionat.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingFormQueryDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private int beds;
    private int rooms;


    //Möjliggöra att vi inte bryr oss om antal rum
    public BookingFormQueryDTO(LocalDate startDate, LocalDate endDate, int beds){
        this.startDate = startDate;
        this.endDate = endDate;
        this.beds = beds;
        this.rooms = -1;
    }
}
