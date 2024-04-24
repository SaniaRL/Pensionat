package backEnd1.pensionat.controllers;

import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Ska väl ändras till @Controller om vi bara returnerar html-sidor.
@RequiredArgsConstructor
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/add")
    public String addCustomer(@RequestParam String name, @RequestParam String email) {
        return customerService.addCustomer(new Customer(name, email));
    }

    @RequestMapping("/{id}/remove")
    public String removeCustomerById(@PathVariable Long id) {
        return customerService.removeCustomerById(id);
    }

    @GetMapping("/search")
    public Page<Customer> getCustomerByEmail(@RequestParam String email, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return customerService.getCustomersByEmail(email, pageable);
    }
}