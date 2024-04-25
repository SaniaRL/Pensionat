package backEnd1.pensionat.Controllers;

import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Models.OrderLine;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.services.interfaces.BookingService;
import backEnd1.pensionat.services.interfaces.CustomerService;
import backEnd1.pensionat.services.interfaces.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController // Ska väl ändras till @Controller om vi bara returnerar html-sidor.
@RequiredArgsConstructor
@RequestMapping(path = "/booking")
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final OrderLineService orderLineService;

    @RequestMapping("/all")
    public List<DetailedBookingDTO> getAllCustomers() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/add")
    public String addBooking(@RequestParam String name, @RequestParam String email, Model model) {
        Customer customer;
        customer= customerService.getCustomersByEmail(email);
        if (!customer) {
            customer = new Customer(name, email);
            customerService.addCustomer(customer);
        }
        Booking booking = new Booking(customer, model.getAttribute("startDate"), model.getAttribute("endDate"))
        bookingService.addBooking(booking);
        List<Room> rooms = model.getAttribute("rooms");
        List<Integer> extraBeds = model.getAttribute("extraBeds");
        for (int i = 0; i < rooms.size(); i++) {
            orderLineService.addOrderLine(new OrderLine(booking, rooms.get(i), extraBeds.get(i)));
        }
        return "BookingConfirmation";
    }

    @RequestMapping("/{id}/remove")
    public String removeBookingById(@PathVariable Long id) {
        return bookingService.removeBookingById(id);
    }
}