package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.BookingDTO;
import backEnd1.pensionat.DTOs.CustomerDTO;
import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.DTOs.SimpleOrderLineDTO;
import backEnd1.pensionat.services.interfaces.BookingService;
import backEnd1.pensionat.services.interfaces.CustomerService;
import backEnd1.pensionat.services.interfaces.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/booking")
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final OrderLineService orderLineService;

    /*@RequestMapping("/all")
    //public List<DetailedBookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }*/

    @PostMapping("/add")
    public String addBooking(@ModelAttribute CustomerDTO customerDTO, Model model) {
        if (customerService.getCustomerByEmail(customerDTO.getEmail()) == null) {
            customerService.addCustomerFromCustomerDTO(customerDTO);
        }
        Long bookingId = bookingService.addBookingFromBookingDto(new BookingDTO(customerDTO,
                        (LocalDate) model.getAttribute("startDate"),
                        (LocalDate) model.getAttribute("endDate")));

        List<RoomDTO> rooms = (List<RoomDTO>) model.getAttribute("rooms");
        List<Integer> extraBeds = (List<Integer>) model.getAttribute("extraBeds");
        for (int i = 0; i < rooms.size(); i++) {
            orderLineService.addOrderLineFromSimpleOrderLineDto(new SimpleOrderLineDTO(bookingId,
                                                                    rooms.get(i), extraBeds.get(i)));
        }
        model.addAttribute("booking", bookingService.getBookingById(bookingId));
        model.addAttribute("orderLines", orderLineService.getOrderLinesByBookingId(bookingId));
        return "bookingConfirmation";
    }

    @RequestMapping("/{id}/remove")
    public String removeBookingById(@PathVariable Long id) {
        return bookingService.removeBookingById(id);
    }
}