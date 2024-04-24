package backEnd1.pensionat.Controllers;

import backEnd1.pensionat.Enums.RoomType;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Ska väl ändras till @Controller om vi bara returnerar html-sidor.
@RequiredArgsConstructor
@RequestMapping(path = "/room")
public class RoomController {

    private final RoomService roomService;

    @RequestMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/add")
    public String addRoom(@RequestParam Long id, @RequestParam RoomType typeOfRoom) {
        return roomService.addRoom(new Room(id, typeOfRoom));
    }

    @RequestMapping("/{id}/remove")
    public String removeRoomById(@PathVariable Long id) {
        return roomService.removeRoomById(id);
    }
}