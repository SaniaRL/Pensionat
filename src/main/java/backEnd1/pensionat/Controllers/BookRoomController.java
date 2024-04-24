package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.BookingFormQueryDTO;
import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.Repositories.RoomRepo;
import backEnd1.pensionat.services.impl.RoomServicelmpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookRoomController {
    RoomServicelmpl roomService;

    //index.html -> /booking
    @RequestMapping("/booking")
    public String bookingForm(){
        return "booking";
    }

    //submit formulär -> /booking
    public String processBookingForm(@ModelAttribute BookingFormQueryDTO query, Model model){
        List<RoomDTO> availableRooms = roomService.findAvailableRooms(query);
        return "redirect:/booking";
    }

}
