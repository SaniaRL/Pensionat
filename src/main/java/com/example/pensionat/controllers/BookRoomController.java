package com.example.pensionat.controllers;

import com.example.pensionat.dtos.*;
import com.example.pensionat.services.impl.CustomerServiceImpl;
import com.example.pensionat.services.impl.OrderLineServicelmpl;
import com.example.pensionat.services.impl.RoomServicelmpl;
import com.example.pensionat.services.impl.BookingServiceImpl;
import com.example.pensionat.services.interfaces.MailTemplateService;
import com.example.pensionat.services.providers.EmailConfigProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@SessionAttributes({"chosenOrderLines", "result"})
@PreAuthorize("isAuthenticated()")
public class BookRoomController {

    private final JavaMailSender emailSender;
    RoomServicelmpl roomService;
    BookingServiceImpl bookingService;
    CustomerServiceImpl customerService;
    OrderLineServicelmpl orderLineService;
    MailTemplateService mailTemplateService;
    EmailConfigProvider emailConfigProvider;

    @PostMapping("/bookingSubmit")
    public String processBookingForm(@RequestParam boolean emptyBooking, @ModelAttribute BookingFormQueryDTO query, Model model) {
        List<RoomDTO> availableRooms = new ArrayList<>();
        List<RoomDTO> chosenRooms = new ArrayList<>();
        String status = "Error: Query is null";

        if (emptyBooking) {
            model.addAttribute("emptyBooking", "Du har ej valt några rum.");
        }

        if (query != null) {
            availableRooms = roomService.findAvailableRooms(query);
            status = roomService.enoughRooms(query, availableRooms);
            model.addAttribute("startDate", query.getStartDate());
            model.addAttribute("endDate", query.getEndDate());
            model.addAttribute("rooms", query.getRooms());
            model.addAttribute("beds", query.getBeds());
        }

        if (status.isEmpty()) {
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

    @PostMapping("/booking")
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
        List<Integer> res = bookingService.submitBookingCustomer(bookingData);
        if (res.size() > 1) {
            model.addAttribute("booked", res);
            return "redirect:/booking/update?id=" + bookingData.getId();
        }
        System.out.println("bookingData: " + bookingData);
        double price = bookingService.generatePrice(bookingData);
        System.out.println("price after: " + price);
        model.addAttribute("price", price);

        MailTemplateDTO mailTemplate = mailTemplateService.getMailTemplateByName("Bokningsbekräftelse");
        String text = getTheRightText(mailTemplate.getBody(), bookingData, price);

        String subject = getTheRightText(mailTemplate.getName(), bookingData, price);

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(text, true);
            helper.setTo(bookingData.getEmail());
            helper.setSubject(subject);
            helper.setFrom(emailConfigProvider.getMailUsername());
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "bookingConfirmation";
    }

    private String getTheRightText(String text, BookingData bd, double sum) {
        return text.replace("!!!!Namn!!!!", bd.getName())
                .replace("!!!!E-post!!!!", bd.getEmail())
                .replace("!!!!Startdatum!!!!", bd.getStartDate())
                .replace("!!!!Slutdatum!!!!", bd.getEndDate())
                .replace("!!!!Totalsumma!!!!", String.valueOf(sum));
    }

    @GetMapping("/bookingConfirmation")
    public String showBookingConfirmation() {
        return "bookingConfirmation";
    }
}