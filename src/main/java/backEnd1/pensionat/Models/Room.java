package backEnd1.pensionat.Models;

import backEnd1.pensionat.Enums.RoomType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    private Long id;

    @NotBlank (message = "Type of room is mandatory")
    private RoomType typeOfRoom;

    public Room(RoomType typeOfRoom){
        this.typeOfRoom = typeOfRoom;
    }
}
