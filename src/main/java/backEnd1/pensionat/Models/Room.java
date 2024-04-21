package backEnd1.pensionat.Models;

import backEnd1.pensionat.Enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Room {

    @Id
    private Long id;

    @NotBlank(message = "Type of room is mandatory")
    private RoomType typeOfRoom;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<OrderLine> orderLines = new ArrayList<>();

    public Room(RoomType typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
    }
}
