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
public class DetailedBookingDTO {

    private Long id;
    private SimpleCustomerDTO customer;
    private LocalDate startDate;
    private LocalDate endDate;

    public DetailedBookingDTO(SimpleCustomerDTO customer, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
