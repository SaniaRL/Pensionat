package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.BookingData;
import backEnd1.pensionat.DTOs.BookingFormQueryDTO;
import backEnd1.pensionat.DTOs.OrderLineDTO;
import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.services.impl.RoomServicelmpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("roomList")
public class BookRoomController {
    RoomServicelmpl roomService;

    public BookRoomController(RoomServicelmpl roomService) {
        this.roomService = roomService;
    }

    //submit formulär -> /booking - se till att den hämtar lediga rum och har en lista så det kan målas upp
    @PostMapping("/bookingSubmit")
    public String processBookingForm(@ModelAttribute BookingFormQueryDTO query, Model model) {
        List<RoomDTO> availableRooms = new ArrayList<>();
        List<RoomDTO> chosenRooms = new ArrayList<>();
        if (query != null) {
            System.out.println("QueryDTO: " + query);
            availableRooms = roomService.findAvailableRooms(query);
            model.addAttribute("startDate", query.getStartDate());
            System.out.println("startDate: "+ query.getStartDate());
            model.addAttribute("endDate", query.getEndDate());
            System.out.println("endDate: " + query.getEndDate());
            model.addAttribute("rooms", query.getRooms());
            System.out.println("rooms: " + query.getRooms());
            model.addAttribute("beds", query.getBeds());
            System.out.println("beds: " + query.getBeds());
            System.out.println();
            System.out.println();
        }

        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("chosenRooms", chosenRooms);
        return "booking";
    }

    /*
    @GetMapping("/update")
    public String processBookingForm(@ModelAttribute List<RoomDTO> availableRooms, @ModelAttribute List<RoomDTO> chosenRooms, Model model){
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("chosenRooms", chosenRooms);
        return "booking";
    }



    @PostMapping("/confirmBooking")
    public String bookingConfirmation(@RequestBody String jsonForm,
                                      @RequestBody String jsonString,
                                      Model model) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<OrderLineDTO> orderLines = new ArrayList<>();
        orderLines = Arrays.asList(objectMapper.readValue(jsonString, OrderLineDTO[].class));

        //Add orderLines
        model.addAttribute("orderLines", orderLines);
        return "bookingConfirmation";
    }

     */

    @GetMapping("/booking")
    public String booking(Model model) {
        List<RoomDTO> availableRooms = new ArrayList<>();
        List<RoomDTO> chosenRooms = new ArrayList<>();
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("chosenRooms", chosenRooms);
        return "booking";
    }


    @PostMapping("/confirmBooking")
    public String confirmBooking() {
        return "redirect:/customer/customerOrNot";
    }



    @PostMapping("/submitBookingCustomer")
    public String submitBookingCustomer(@RequestBody BookingData bookingData) {
        System.out.println("Namn: " + bookingData.getName());
        System.out.println("Email: " + bookingData.getEmail());
        System.out.println("Startdatum: " + bookingData.getStartDate());
        System.out.println("Slutdatum: " + bookingData.getEndDate());
        System.out.println("Valda rum: ");
        for (OrderLineDTO room : bookingData.getChosenRooms()) {
            System.out.println("  - RumID: " + room.getId() + "  - Rumstyp: " + room.getRoomType() + ", Extra sängar: " + room.getExtraBeds());
        }

        // Utför bokningsprocessen och returnera lämpligt svar
        // Här returnerar jag bara en bekräftelsemeddelande som en sträng
        return "redirect:index.html";
    }
}