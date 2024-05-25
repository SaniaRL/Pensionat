package com.example.pensionat.controllers;

import com.example.pensionat.dtos.*;
import com.example.pensionat.services.convert.OrderLineConverter;
import com.example.pensionat.services.convert.RoomConverter;
import com.example.pensionat.services.impl.RoomServicelmpl;
import com.example.pensionat.services.interfaces.BookingService;
import com.example.pensionat.services.interfaces.CustomerService;
import com.example.pensionat.services.interfaces.OrderLineService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"chosenRooms", "booking", "result"})
@RequestMapping(path = "/booking")
@PreAuthorize("isAuthenticated()")
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final OrderLineService orderLineService;
    private final RoomServicelmpl roomService;

    @GetMapping("/search")
    public String searchBooking() {
        return "bookingSearch";
    }

    @GetMapping("/remove")
    public String removeBookingById(HttpSession session, Model model) {
        DetailedBookingDTO booking = (DetailedBookingDTO) session.getAttribute("booking");
        if (booking != null) {
            bookingService.removeBookingById(booking.getId());
            String status = "BOKNING MED ID " + booking.getId() + " BORTTAGEN";
            model.addAttribute("status", status);
            session.removeAttribute("booking");
        }
        return "bookingRemoved";
    }

    @GetMapping("/update")
    public String updateBooking(@RequestParam Long id, Model model, HttpSession session){

        DetailedBookingDTO booking = bookingService.getBookingById(id);

        if(booking == null) {
            String result = "Bokningen hittades ej";
            model.addAttribute("result", result);
            return "bookingSearch";
        }

        List<SimpleOrderLineDTO> chosenRooms = orderLineService.getOrderLinesByBookingId(id);
        List<SimpleOrderLineDTO> availableRooms = new ArrayList<>();
        int rooms = bookingService.getNumberOfRoomsFromBooking(booking.getId());
        int beds = bookingService.getNumberOfBedsFromBooking(booking.getId());

        model.addAttribute("booking", booking);
        model.addAttribute("startDate", booking.getStartDate());
        model.addAttribute("rooms", rooms);
        model.addAttribute("beds", beds);
        model.addAttribute("endDate", booking.getEndDate());
        model.addAttribute("chosenRooms", chosenRooms);
        model.addAttribute("availableRooms", availableRooms);

        return "updateBooking";
    }

    @PostMapping("/updateConfirm")
    public String updateConfirm(@ModelAttribute BookingFormQueryDTO query,
                                Model model,
                                HttpSession session){
        List<SimpleOrderLineDTO> availableRooms;
        List<SimpleOrderLineDTO> chosenRooms = (List<SimpleOrderLineDTO>) session.getAttribute("chosenRooms");
        String status = "Error: Query is null";

        List<SimpleOrderLineDTO> availableRooms2 = new ArrayList<>();

        if (query != null) {
            availableRooms = roomService.filterNotInChosenRooms(query, chosenRooms);
            List<RoomDTO> availableRoomsAsRoomDTO = availableRooms.stream()
                    .map(RoomConverter::orderLineToRoomDTO).toList();


            availableRooms2 = roomService.findAvailableRooms(query, ((DetailedBookingDTO) session
                                         .getAttribute("booking")).getId()).stream()
                                         .map(OrderLineConverter::roomToSimpleOrderLineDTO).toList();

            status = roomService.enoughRooms(query, availableRoomsAsRoomDTO);
            model.addAttribute("startDate", query.getStartDate());
            model.addAttribute("endDate", query.getEndDate());
            model.addAttribute("rooms", query.getRooms());
            model.addAttribute("beds", query.getBeds());
        }

        if(status.isEmpty()){

            model.addAttribute("availableRooms", availableRooms2);
        }

        model.addAttribute("chosenRooms", chosenRooms);
        model.addAttribute("status", status);

        return "updateBooking";
    }
}