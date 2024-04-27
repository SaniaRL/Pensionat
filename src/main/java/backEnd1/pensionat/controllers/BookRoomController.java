package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.BookingData;
import backEnd1.pensionat.DTOs.BookingFormQueryDTO;
import backEnd1.pensionat.DTOs.OrderLineDTO;
import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.services.impl.RoomServicelmpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/bookingSubmit")
    public String processBookingForm(@ModelAttribute BookingFormQueryDTO query, Model model) {
        List<RoomDTO> availableRooms = new ArrayList<>();
        List<RoomDTO> chosenRooms = new ArrayList<>();
        if (query != null) {
            availableRooms = roomService.findAvailableRooms(query);
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
    public ResponseEntity<String> confirmBooking(@RequestBody BookingData bookingData) {
        // Hantera bokningsdatan här
        System.out.println("Startdatum: " + bookingData.getStartDate());
        System.out.println("Slutdatum: " + bookingData.getEndDate());
        System.out.println("Valda rum: ");
        for (OrderLineDTO room : bookingData.getChosenRooms()) {
            System.out.println("  - RumID: " + room.getId() + "  - Rumstyp: " + room.getRoomType() + ", Extra sängar: " + room.getExtraBeds());
        }

        // Utför bokningsprocessen och returnera lämpligt svar
        // Här returnerar jag bara en bekräftelsemeddelande som en sträng
        return new ResponseEntity<>("Bokningen har bekräftats!", HttpStatus.OK);
    }
}