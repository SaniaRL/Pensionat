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
@SessionAttributes("roomList")
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


    @PostMapping("/confirmBooking")
    public String confirmBooking() {
        return "redirect:/customer/customerOrNot";
    }

    @PostMapping("/submitBookingCustomer")
    public String submitBookingCustomer(@RequestBody BookingData bookingData) {
        //Status är väl onödigt och vi använder inte men void kändes farligt idk
        String statusMessage = bookingService.submitBookingCustomer(bookingData);
        return "redirect:/index.html";

/*        String name = bookingData.getName();
        String email = bookingData.getEmail();
        List<OrderLineDTO> orderLines = bookingData.getChosenRooms();
        LocalDate startDate = LocalDate.parse(bookingData.getStartDate());
        LocalDate endDate = LocalDate.parse(bookingData.getEndDate());

        System.out.println();
        System.out.println("Namn: " + name);
        System.out.println("Email: " + email);
        System.out.println("Startdatum: " + bookingData.getStartDate());
        System.out.println("Slutdatum: " + bookingData.getEndDate());
        System.out.println("Valda rum: ");
        for (OrderLineDTO room : orderLines) {
            System.out.println("  - RumID: " + room.getId() + "  - Rumstyp: " + room.getRoomType() + ", Extra sängar: " + room.getExtraBeds());
        }
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println();

        //Kolla om kunden finns - hämta kund eller skapa ny
        SimpleCustomerDTO customer = customerService.getCustomerByEmail(email);
        if(customer == null) {
            customer = new SimpleCustomerDTO(name, email);
            //Add customer to Repo
            customer = customerService.addCustomer(customer);
            System.out.println("New customer added: " + customer);
        }

        //Skapa bokning
        DetailedBookingDTO booking = new DetailedBookingDTO(customer, startDate, endDate);
        System.out.println("New booking: " + booking);

        //Lägg till bokning i DATABAS och spara om den
        booking = bookingService.addBooking(booking);
        System.out.println("Added booking: " + booking);

        //TODO Uppdatera Customer lägg till bokning

        //TODO Översätt totala rum till extra rum - rum???? menar säng

        //Lägg till orderrader?
        //TODO har inte ändrat dessa till DTO det bråkar inte med nåt:
        DetailedBookingDTO finalBooking = booking;
        orderLines.stream()
                .map(orderLine -> new DetailedOrderLineDTO(orderLine.getExtraBeds(),finalBooking, roomService.getRoomByID((long) orderLine.getId())))
                .forEach(orderLineService::addOrderLine);

 */
    }
}