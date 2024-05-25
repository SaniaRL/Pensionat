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

    @PostMapping("/update")
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

    @GetMapping("/")
    public String handleCustomers(Model model){
        int currentPage = 1;
        customerService.addToModel(currentPage, model);
        return "handleCustomers";
    }

    @GetMapping(value = "/", params = "page")
    public String handleByPage(Model model, @RequestParam int page){
        customerService.addToModel(page, model);
        return "handleCustomers";
    }

    @GetMapping(value = "/", params = "search")
    public String getCustomerByEmail(@RequestParam String search, Model model) {
        int currentPage = 1;
        customerService.addToModelEmail(search, currentPage, model);
        return "handleCustomers";
    }

    @GetMapping(value = "/", params = {"search", "page"})
    public String getCustomerByEmailByPage(@RequestParam String search, Model model, @RequestParam int page) {
        customerService.addToModelEmail(search, page, model);
        return "handleCustomers";
    }

    @GetMapping("/blacklistcheck/{email}")
    public String checkIfEmailBlacklisted(@PathVariable("email") String email, Model model) throws IOException, InterruptedException {
        if (!customerService.checkIfEmailBlacklisted(email)) {
            model.addAttribute("status", "Kunden med email " + email + " är SVARTLISTAD!");
            return "customerOrNot";
        }
        return "bookingConfirmation";
    }

    @GetMapping("/blacklist")
    public String handleBlacklist(Model model) throws IOException {
        int currentPage = 1;
        customerService.addToModelBlacklist(currentPage, model);
        return "handleBlacklist";
    }

    @GetMapping(value = "/blacklist/", params = "page")
    public String handleBlacklistByPage(Model model,
                                        @RequestParam int page) throws IOException {
        customerService.addToModelBlacklist(page, model);
        return "handleBlacklist";
    }

    @GetMapping(value = "/blacklist/", params = "search")
    public String handleBlacklistSearch(@RequestParam String search, Model model) throws IOException {
        int currentPage = 1;
        customerService.addToModelBlacklistSearch(search, currentPage, model);
        return "handleBlacklist";
    }

    @GetMapping(value = "/blacklist/", params = {"search", "page"})
    public String handleBlacklistByPageSearch(@RequestParam String search, Model model, @RequestParam int page) throws IOException {
        customerService.addToModelBlacklistSearch(search, page, model);
        return "handleBlacklist";
    }

    @RequestMapping("/blacklist/{email}/update")
    public String updateBlacklistCustomer(@PathVariable String email, Model model) throws IOException {
        SimpleBlacklistCustomerDTO c = customerService.getCustomerFromBlacklistByEmail(email);
        model.addAttribute("kund", c);
        return "updateBlacklistCustomers";
    }

    @PostMapping("/blacklist/update")
    public String handleBlacklistCustomerUpdate(Model model, SimpleBlacklistCustomerDTO c) throws IOException {
        customerService.updateOrAddToBlacklist(c, "update");
        int currentPage = 1;
        customerService.addToModelBlacklist(currentPage, model);
        return "handleBlacklist";
    }

    @PostMapping("/blacklist/form/add")
    public String addToBlacklist(@RequestParam("name") String name, @RequestParam("email") String email, Model model) {
        SimpleBlacklistCustomerDTO c = new SimpleBlacklistCustomerDTO();
        c.setName(name);
        c.setEmail(email);
        customerService.updateOrAddToBlacklist(c, "add");
        model.addAttribute("name", name);
        return "blacklistForm";
    }

    @GetMapping("/blacklist/form")
    public String showBlacklistForm() {
        return "blacklistForm";
    }
}