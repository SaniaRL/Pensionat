package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.*;
import backEnd1.pensionat.services.convert.RoomConverter;
import backEnd1.pensionat.services.impl.RoomServicelmpl;
import backEnd1.pensionat.services.interfaces.BookingService;
import backEnd1.pensionat.services.interfaces.CustomerService;
import backEnd1.pensionat.services.interfaces.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/booking")
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final OrderLineService orderLineService;
    private final RoomServicelmpl roomService;

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
            //TODO idk:
            int xtrBeds = 0;
            if(extraBeds.get(i) != null){
                xtrBeds = extraBeds.get(i);
            }
            orderLineService.addOrderLineFromSimpleOrderLineDto(new SimpleOrderLineDTO(bookingId,
                                                                    rooms.get(i), xtrBeds));
        }
        model.addAttribute("booking", bookingService.getBookingById(bookingId));
        model.addAttribute("orderLines", orderLineService.getOrderLinesByBookingId(bookingId));
        return "bookingConfirmation";
    }

    @GetMapping("/search")
    public String searchBooking() {
        return "bookingSearch";
    }

    @RequestMapping("/{id}/remove")
    public String removeBookingById(@PathVariable Long id) {
        return bookingService.removeBookingById(id);
    }

    @GetMapping("/update")
    public String updateBooking(@RequestParam Long id, Model model){

        DetailedBookingDTO booking = bookingService.getBookingById(id);
        if(booking == null) {
            String result = "Bokningen hittades ej";
            model.addAttribute("result", result);
            return "bookingSearch";
        }

        List<SimpleOrderLineDTO> chosenRooms = orderLineService.getOrderLinesByBookingId(id);
        List<SimpleOrderLineDTO> availableRooms = new ArrayList<>();

        model.addAttribute("booking", booking);
        model.addAttribute("startDate", booking.getStartDate());
        model.addAttribute("endDate", booking.getEndDate());
        model.addAttribute("chosenRooms", chosenRooms);
        model.addAttribute("availableRooms", availableRooms);
        return "updateBooking";
    }

    @GetMapping("/updateConfirm")
    public String updateConfirm(@RequestParam DetailedBookingDTO booking,
                                @RequestParam List<SimpleOrderLineDTO> chosenRooms,
                                @RequestParam BookingFormQueryDTO query,
                                @RequestParam Model model){


        List<RoomDTO> availableRooms = roomService.findAvailableRooms(query);

        model.addAttribute("booking", booking);
        model.addAttribute("startDate", query.getStartDate());
        model.addAttribute("endDate", query.getEndDate());
        model.addAttribute("chosenRooms", chosenRooms);
        model.addAttribute("availableRooms", availableRooms);
        return "updateBooking";
    }
}