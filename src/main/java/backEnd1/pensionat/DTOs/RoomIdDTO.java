package backEnd1.pensionat.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomIdDTO {
    private String roomId;
    private int extraBeds;

}
