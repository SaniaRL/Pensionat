package backEnd1.pensionat.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {

    private CustomerDTO customer;
    private LocalDate startDate;
    private LocalDate endDate;
}
