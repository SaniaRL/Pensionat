package com.example.pensionat.controllers;

import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.models.customers;
import com.example.pensionat.services.interfaces.BookingService;
import com.example.pensionat.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.thymeleaf.expression.Arrays;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final BookingService bookingService;

    @RequestMapping("/{id}/removeHandler")
    public String removeCustomerByIdHandler(@PathVariable Long id, Model model) {
        if (bookingService.getBookingByCustomerId(id)) {
            model.addAttribute("status", "En kund kan inte tas bort om det finns aktiva bokningar");
        }
        else {
            customerService.removeCustomerById(id);
        }
        return handleCustomers(model);
    }

    @RequestMapping("/{email}/update")
    public String updateCustomerHandler(@PathVariable String email, Model model){
        SimpleCustomerDTO c = customerService.getCustomerByEmail(email);
        model.addAttribute("kund", c);
        return "updateCustomers";
    }

    @PostMapping("/handle/update")
    public String handleCustomersUpdate(Model model, SimpleCustomerDTO customer){
        customerService.updateCustomer(customer);
        int currentPage = 1;
        customerService.addToModel(currentPage, model);
        return "handleCustomers";
    }


    @RequestMapping("/customerOrNot")
    public String loadCustomerOrNot(){
        return "customerOrNot";
    }

    @GetMapping("/handle")
    public String handleCustomers(Model model){
        int currentPage = 1;
        customerService.addToModel(currentPage, model);
        return "handleCustomers";
    }

    @GetMapping("/handle/{pageNumber}")
    public String handleByPage(Model model, @PathVariable("pageNumber") int currentPage){
        customerService.addToModel(currentPage, model);
        return "handleCustomers";
    }

    @GetMapping("/search")
    public String getCustomerByEmail(@RequestParam String email, Model model) {
        int currentPage = 1;
        customerService.addToModelEmail(email, currentPage, model);
        return "handleCustomers";
    }

    @GetMapping("/search/{pageNumber}")
    public String getCustomerByEmailByPage(@RequestParam String email, Model model, @PathVariable("pageNumber") int currentPage) {
        customerService.addToModelEmail(email, currentPage, model);
        return "handleCustomers";
    }

    @GetMapping("/contractCustomer")
    public String getContractCustomers(Model model) {
        //TODO Hämta ordentligt
        customers c1 = new customers(1L, "C", "B", "!", "!", "!", 1, "Ö", "!", "!");
        customers c2 = new customers(2L, "D", "E", "!", "!", "!", 1, "Ä", "!", "!");
        customers c3 = new customers(3L, "E", "A", "!", "!", "!", 1, "Å", "!", "!");
        customers c4 = new customers(4L, "A", "D", "!", "!", "!", 1, "Ö", "!", "!");
        customers c5 = new customers(5L, "B", "C", "!", "!", "!", 1, "K", "!", "!");

        List<customers> customers = Arrays.asList(c1, c2, c3, c4, c5);
        model.addAttribute("contractList", customers);


        return "contractCustomers";
    }

    @GetMapping("/contractCustomer/{id}")
    public String getContractCustomer(@RequestParam long id, Model model) {
        model.addAttribute("id", id);
        return "contractCustomer/{id}";
    }
}