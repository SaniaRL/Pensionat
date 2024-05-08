package com.example.pensionat.controllers;

import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.services.interfaces.BookingService;
import com.example.pensionat.services.interfaces.ContractCustomerService;
import com.example.pensionat.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final BookingService bookingService;
    private final ContractCustomerService contractCustomerService;

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

    @RequestMapping("/blacklist/add")
    public void addToBlacklist(@RequestParam String email, @RequestParam String name) {
        customerService.addToBlacklist(email, name);
    }

    @RequestMapping("/blacklist/update")
    public void updateBlacklist(@RequestParam String email, @RequestParam String name, @RequestParam String isOk) {
        customerService.updateBlacklist(email, name, isOk);
    }

    @GetMapping("/contractCustomer")
    public String getContractCustomers(Model model) {
        int currentPage = 1;
        contractCustomerService.addToModel(currentPage, model);
        return "contractCustomers";
    }

    @GetMapping("/contractCustomer/{id}")
    public String getContractCustomer(Model model, @PathVariable long id) {
        //TODO Hämta baserat på ID
        model.addAttribute("id", id);
        return "contractCustomer";
    }

    @GetMapping("/contractHandle")
    public String contractHandleCustomers(Model model){
        int currentPage = 1;
        contractCustomerService.addToModel(currentPage, model);
        return "contractCustomers";
    }

    @GetMapping("/contractHandle/{pageNumber}")
    public String contractHandleByPage(Model model, @PathVariable("pageNumber") int currentPage){
        contractCustomerService.addToModel(currentPage, model);
        return "contractCustomers";
    }
}