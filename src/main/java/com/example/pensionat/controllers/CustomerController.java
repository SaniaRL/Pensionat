package com.example.pensionat.controllers;

import com.example.pensionat.dtos.SimpleBlacklistCustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.services.interfaces.BookingService;
import com.example.pensionat.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @GetMapping("/blacklistcheck/{email}")
    public String checkIfEmailBlacklisted(@PathVariable("email") String email, Model model) {
        if (!customerService.checkIfEmailBlacklisted(email)) {
            model.addAttribute("status", "Kunden med email " + email + " är SVARTLISTAD!");
            return "customerOrNot";
        }
        return "bookingConfirmation";
    }

    @GetMapping("/blacklist/handle")
    public String handleBlacklist(Model model) throws IOException {
        int currentPage = 1;
        customerService.addToModelBlacklist(currentPage, model);
        return "handleBlacklist";
    }

    @GetMapping("/blacklist/handle/{pageNumber}")
    public String handleBlacklistByPage(Model model, @PathVariable("pageNumber") int currentPage) throws IOException {
        customerService.addToModelBlacklist(currentPage, model);
        return "handleBlacklist";
    }

    @RequestMapping("/blacklist/{email}/update")
    public String updateBlacklistCustomer(@PathVariable String email, Model model) throws IOException {
        SimpleBlacklistCustomerDTO c = customerService.getCustomerFromBlacklistByEmail(email);
        model.addAttribute("kund", c);
        return "updateBlacklistCustomers";
    }

    @PostMapping("/blacklist/handle/update")
    public String handleBlacklistCustomerUpdate(Model model, SimpleBlacklistCustomerDTO c) throws IOException {
        customerService.updateBlacklistCustomer(c);
        int currentPage = 1;
        customerService.addToModelBlacklist(currentPage, model);
        return "handleBlacklist";
    }

    @PostMapping("/blacklist/form/add")
    public String addToBlacklist(@RequestParam("name") String name, @RequestParam("email") String email) {
        SimpleBlacklistCustomerDTO c = new SimpleBlacklistCustomerDTO();
        c.setName(name);
        c.setEmail(email);
        customerService.addToBlacklist(c);
        return "redirect:/customer/blacklist/handle";
    }

    @GetMapping("/blacklist/form")
    public String showBlacklistForm() {
        return "blacklistForm";
    }
}