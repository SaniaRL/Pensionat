package com.example.pensionat.controllers;

import com.example.pensionat.models.Booking;
import com.example.pensionat.models.OrderLine;
import com.example.pensionat.models.Room;
import com.example.pensionat.services.interfaces.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orderline")
public class OrderLineController {

    private final OrderLineService orderLineService;

    @RequestMapping("/all")
    public List<OrderLine> getAllOrderLines() {
        return orderLineService.getAllOrderLines();
    }

    @PostMapping("/add")
    public String addOrderLine(@RequestParam String name, @RequestParam String email,
                               @RequestParam LocalDate startDate, @RequestParam LocalDate endDate,
                               @RequestParam Long roomId, @RequestParam int typeOfRoom,
                               @RequestParam int extraBeds) {

        return orderLineService.addOrderLine(new OrderLine(new Booking(name, email, startDate, endDate),
                                                new Room(roomId, typeOfRoom), extraBeds));
    }

    @RequestMapping("/{id}/remove")
    public String removeOrderLineById(@PathVariable Long id) {
        return orderLineService.removeOrderLineById(id);
    }
}