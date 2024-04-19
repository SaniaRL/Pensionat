package backEnd1.pensionat.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @NotEmpty
    @FutureOrPresent
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @NotEmpty
    @Future
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    public Booking(String name, String email, LocalDate startDate, LocalDate endDate) {
        this.customer = new Customer(name, email);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Booking(Customer customer, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
