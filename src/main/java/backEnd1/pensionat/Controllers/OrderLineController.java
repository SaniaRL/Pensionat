package backEnd1.pensionat.Controllers;

import backEnd1.pensionat.Enums.RoomType;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.OrderLine;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.services.interfaces.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController // Ska väl ändras till @Controller om vi bara returnerar html-sidor.
@RequiredArgsConstructor
@RequestMapping(path = "/orderline")
public class OrderLineController {

    private final OrderLineService orderLineService;

    @RequestMapping("/all")
    public List<OrderLine> getAllOrderLines() {
        return orderLineService.getAllOrderLines();
    }

    @PostMapping("/add")
    public String addOrderLine(@RequestParam String name, @RequestParam String email,
                               @RequestParam LocalDate startDate, @RequestParam LocalDate endDate,
                               @RequestParam Long roomId, @RequestParam RoomType typeOfRoom,
                               @RequestParam int extraBeds) {

        return orderLineService.addOrderLine(new OrderLine(new Booking(name, email, startDate, endDate),
                                                new Room(roomId, typeOfRoom), extraBeds));
    }

    @RequestMapping("/{id}/remove")
    public String removeOrderLineById(@PathVariable Long id) {
        return orderLineService.removeOrderLineById(id);
    }
}