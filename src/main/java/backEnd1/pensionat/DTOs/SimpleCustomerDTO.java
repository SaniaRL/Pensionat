package backEnd1.pensionat.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleCustomerDTO {

    private Long id;
    private String name;
    private String email;

    public SimpleCustomerDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
