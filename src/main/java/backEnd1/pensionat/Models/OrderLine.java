package backEnd1.pensionat.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrderLine {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn
    private Booking booking;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Room room;

    @NotEmpty
    private int extraBeds;

    public OrderLine(Booking booking, Room room, int extraBeds) {
        this.booking = booking;
        this.room = room;
        this.extraBeds = extraBeds;
    }
}
