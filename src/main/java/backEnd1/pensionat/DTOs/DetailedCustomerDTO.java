package backEnd1.pensionat.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedCustomerDTO {

    private Long id;
    private String name;
    private String email;
    private List<SimpleBookingDTO> bookings;
}
