package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.BookingData;
import backEnd1.pensionat.DTOs.BookingFormQueryDTO;
import backEnd1.pensionat.DTOs.OrderLineDTO;
import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Models.OrderLine;
import backEnd1.pensionat.services.impl.BookingServiceImpl;
import backEnd1.pensionat.services.impl.CustomerServiceImpl;
import backEnd1.pensionat.services.impl.OrderLineServicelmpl;
import backEnd1.pensionat.services.impl.RoomServicelmpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        }

        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("chosenRooms", chosenRooms);
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
        String name = bookingData.getName();
        String email = bookingData.getEmail();
        List<OrderLineDTO> orderLines = bookingData.getChosenRooms();
        //TODO kolla om detta funkar ens
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
        Customer customer = customerService.getCustomerByEmail(email);
        System.out.println("Get customer by email: " + customer);
        if(customer == null) {
            customer = new Customer(name, email);
            //Add customer to Repo
            customer = customerService.addCustomer(customer);
            System.out.println("New customer added: " + customer);
        }

        //Skapa bokning
        Booking booking = new Booking(customer, startDate, endDate);

        System.out.println("New booking: " + booking);

        //Lägg till bokning
        Booking addedBooking = bookingService.addBooking(booking);
        System.out.println("Added booking: " + addedBooking);

        //TODO Uppdatera Customer lägg till bokning

        //TODO Översätt totala rum till extra rum

        //Lägg till orderrader?
        orderLines.stream()
                .map(orderLine -> new OrderLine(booking, roomService.getRoomByID((long) orderLine.getId()), orderLine.getExtraBeds()))
                .forEach(orderLineService::addOrderLine);

        String confirmationMessage = "Bokning mottagen och bearbetad!";
        return "index.html";
    }
}