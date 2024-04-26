package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.BookingFormQueryDTO;
import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.services.impl.RoomServicelmpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookRoomController {
    RoomServicelmpl roomService;

    public BookRoomController(RoomServicelmpl roomService){
        this.roomService = roomService;
    }

    //submit formulär -> /booking - se till att den hämtar lediga rum och har en lista så det kan målas upp
    @GetMapping("/booking")
    public String processBookingForm(@ModelAttribute BookingFormQueryDTO query, Model model){
        List<RoomDTO> availableRooms = new ArrayList<>();
        List<RoomDTO> chosenRooms = new ArrayList<>();
        if(query != null){
            availableRooms = roomService.findAvailableRooms(query);
        }
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("chosenRooms", chosenRooms);
        return "booking";
    }
}
