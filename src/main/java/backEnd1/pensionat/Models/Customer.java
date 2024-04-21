package backEnd1.pensionat.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 30, message = "Min 3, Max 30")
    private String name;
    @Email
    private String email;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
