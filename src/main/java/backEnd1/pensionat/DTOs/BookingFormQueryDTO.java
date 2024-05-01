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

}