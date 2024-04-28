package backEnd1.pensionat.controllers;

import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;

import backEnd1.pensionat.services.interfaces.BookingService;
import backEnd1.pensionat.services.convert.CustomerConverter;

import backEnd1.pensionat.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller // Ska väl ändras till @Controller om vi bara returnerar html-sidor.
@RequiredArgsConstructor
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService customerService;

    private final BookingService bookingService;

   /* @RequestMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    } */

    @RequestMapping("/add")
    public String addCustomer() {
        return "customerOrNot";
    }

    @PostMapping("/addCustomerObject")
    public Customer addCustomer(@RequestBody Customer c){
        return customerService.addCustomer(c);
    }

    @DeleteMapping("/{id}/remove")
    public String removeCustomerById(@PathVariable Long id) {
        return customerService.removeCustomerById(id);
    }

    //DeleteMapping ovan verkar ej fungera. Använder temp. Requestmapping.
    @RequestMapping("/{id}/removeHandler")
    public String removeCustomerByIdHandler(@PathVariable Long id, Model model) {

        if (!bookingService.getBookingByCustomerId(id)) {
            customerService.removeCustomerById(id);
        }
        return handleCustomers(model);
    }

    //Skapa senare upp en för ID ist för email vid behov.
    @RequestMapping("/{email}/update")
    public String updateCustomerHandler(@PathVariable String email, Model model){
        SimpleCustomerDTO c = customerService.getCustomerByEmailSimpleDTO(email); //Steg 1

        model.addAttribute("kund", c);
        return "updateCustomers";
    }

    //Temp också
    @PostMapping("/handle/update")
    public String handleCustomersUpdate(Model model, SimpleCustomerDTO customer){
        customerService.updateCustomer(customer);
        int currentPage = 1;
        Page<SimpleCustomerDTO> c = customerService.getAllCustomersPage(currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
        return "handleCustomers.html";
    }


    @RequestMapping("/customerOrNot")
    public String loadCustomerOrNot(){
        return "customerOrNot";
    }

    @GetMapping("/handle")
    public String handleCustomers(Model model){
        int currentPage = 1;
        Page<SimpleCustomerDTO> c = customerService.getAllCustomersPage(currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
        return "handleCustomers.html";
    }

    @GetMapping("/handle/{pageNumber}")
    public String handleByPage(Model model, @PathVariable("pageNumber") int currentPage){
        Page<SimpleCustomerDTO> c = customerService.getAllCustomersPage(currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
        return "handleCustomers.html";
    }

    //Temp metod nedan. Tas bort sen/justeras.
    @RequestMapping("/frontPage")
        public String loadFrontPageTest(){
            return "Index.html";
        }

    @GetMapping("/search")
    public String getCustomerByEmail(@RequestParam String email, Model model) {
        int currentPage = 1;
        Page<SimpleCustomerDTO> c = customerService.getCustomersByEmail(email, currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
        return "handleCustomers.html";
    }

    @GetMapping("/search/{pageNumber}")
    public String getCustomerByEmailByPage(@RequestParam String email, Model model, @PathVariable("pageNumber") int currentPage) {
        Page<SimpleCustomerDTO> c = customerService.getCustomersByEmail(email, currentPage);
        model.addAttribute("allCustomers", c.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", c.getTotalElements());
        model.addAttribute("totalPages", c.getTotalPages());
        return "handleCustomers.html";
    }
}