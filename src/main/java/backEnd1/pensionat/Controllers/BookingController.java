package backEnd1.pensionat.Controllers;

import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.services.interfaces.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController // Ska väl ändras till @Controller om vi bara returnerar html-sidor.
@RequiredArgsConstructor
@RequestMapping(path = "/booking")
public class BookingController {

    private final BookingService bookingService;

    @RequestMapping("/all")
    public List<DetailedBookingDTO> getAllCustomers() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/add")
    public String addBooking(@RequestParam String name, @RequestParam String email,
                             @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return bookingService.addBooking(new Booking(name, email, startDate, endDate));
    }

    @RequestMapping("/{id}/remove")
    public String removeBookingById(@PathVariable Long id) {
        return bookingService.removeBookingById(id);
    }
}