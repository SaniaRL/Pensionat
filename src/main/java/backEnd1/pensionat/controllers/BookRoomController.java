package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.*;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.OrderLine;
import backEnd1.pensionat.services.convert.BookingConverter;
import backEnd1.pensionat.services.impl.BookingServiceImpl;
import backEnd1.pensionat.services.impl.CustomerServiceImpl;
import backEnd1.pensionat.services.impl.OrderLineServicelmpl;
import backEnd1.pensionat.services.impl.RoomServicelmpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@SessionAttributes("chosenOrderLines")
public class BookRoomController {
    RoomServicelmpl roomService;
    BookingServiceImpl bookingService;
    CustomerServiceImpl customerService;
    OrderLineServicelmpl orderLineService;

    //submit formulär -> /booking - se till att den hämtar lediga rum och har en lista så det kan målas upp
    @PostMapping("/bookingSubmit")
    public String processBookingForm(@ModelAttribute BookingFormQueryDTO query, Model model) {
        List<RoomDTO> availableRooms = new ArrayList<>();
        List<RoomDTO> chosenRooms = new ArrayList<>();
        String status = "Error: Query is null";

        if (query != null) {
            availableRooms = roomService.findAvailableRooms(query);
            //Kan ha if och kolla en annan men palla nu
            status = roomService.enoughRooms(query, availableRooms);
            //Kanske kunde skicka hela vår query men palla nu
            model.addAttribute("startDate", query.getStartDate());
            model.addAttribute("endDate", query.getEndDate());
            model.addAttribute("rooms", query.getRooms());
            model.addAttribute("beds", query.getBeds());

        }

        if(status.isEmpty()){
            model.addAttribute("availableRooms", availableRooms);
        }

        model.addAttribute("chosenRooms", chosenRooms);
        model.addAttribute("status", status);

        return "booking";
    }

    @GetMapping("/booking")
    public String booking(Model model) {
        List<RoomDTO> availableRooms = new ArrayList<>();
        List<RoomDTO> chosenRooms = new ArrayList<>();
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("chosenRooms", chosenRooms);
        return "booking";
    }

    @PostMapping("/booking/{id}")
    public String updateBooking(@RequestParam int id, Model model) {
        model.addAttribute("bookingId", id);
        return "booking";        
    }


    @PostMapping("/confirmBooking")
    public String confirmBooking() {
        return "redirect:/customer/customerOrNot";
    }

    @PostMapping("/submitBookingCustomer")
    public String submitBookingCustomer(@RequestBody BookingData bookingData, Model model) {
        //Status är väl onödigt och vi använder inte men void kändes farligt idk
        String statusMessage = bookingService.submitBookingCustomer(bookingData);
        model.addAttribute("startDate", bookingData.getStartDate());
        model.addAttribute("endDate", bookingData.getEndDate());
        model.addAttribute("orderLines", bookingData.getChosenRooms());
        model.addAttribute("name", bookingData.getName());
        model.addAttribute("email", bookingData.getEmail());
        return "redirect:/bookingConfirmation";
    }

    @GetMapping("/bookingConfirmation")
    public String showBookingConfirmation(Model model) {
        return "bookingConfirmation";
    }
}