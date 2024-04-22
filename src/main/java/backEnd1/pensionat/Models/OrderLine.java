package backEnd1.pensionat.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private Booking booking;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Room room;

    @NotNull
    private int extraBeds;

    public OrderLine(Booking booking, Room room, int extraBeds) {
        this.booking = booking;
        this.room = room;
        this.extraBeds = extraBeds;
    }
}
