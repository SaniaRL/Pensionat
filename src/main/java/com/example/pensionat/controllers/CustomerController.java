package com.example.pensionat.controllers;

import com.example.pensionat.dtos.ContractCustomerDTO;
import com.example.pensionat.dtos.SimpleCustomerDTO;
import com.example.pensionat.models.customers;
import com.example.pensionat.services.interfaces.BookingService;
import com.example.pensionat.services.interfaces.ContractCustomerService;
import com.example.pensionat.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        ContractCustomerDTO c1 = new ContractCustomerDTO(1L, "C", "B","Ö");
        ContractCustomerDTO c2 = new ContractCustomerDTO(2L, "D", "E", "Ä");
        ContractCustomerDTO c3 = new ContractCustomerDTO(3L, "E", "F", "Å");
        ContractCustomerDTO c4 = new ContractCustomerDTO(4L, "A", "D", "Ö");
        ContractCustomerDTO c5 = new ContractCustomerDTO(5L, "F", "C", "Ä");
        ContractCustomerDTO c6 = new ContractCustomerDTO(6L, "B", "A", "Å");

        List<ContractCustomerDTO> customers = Arrays.asList(c1, c2, c3, c4, c5, c6);
        model.addAttribute("contractList", customers);

        return "contractCustomers";
    }

    @GetMapping("/contractCustomer/{id}")
    public String getContractCustomer(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        return "contractCustomer";
    }
}