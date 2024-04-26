package backEnd1.pensionat.Controllers;

import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.services.interfaces.CustomerService;
import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller // Ska väl ändras till @Controller om vi bara returnerar html-sidor.
@RequiredArgsConstructor
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping("/add")
    public String addCustomer() {
        return "customerOrNot";
    }

    @DeleteMapping("/{id}/remove")
    public String removeCustomerById(@PathVariable Long id) {
        return customerService.removeCustomerById(id);
    }

    @PutMapping("/{id}/update")
    public String updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customerService.updateCustomer(customer);
        return "index";
    }

    @RequestMapping("/customerOrNot")
    public String loadCustomerOrNot(){
        return "customerOrNot.html";
    }

    //Temp metod nedan. Tas bort sen.
    @RequestMapping("/frontPage")
        public String loadFrontPageTest(){
            return "Index.html";
        }

    @GetMapping("/search")
    public Page<Customer> getCustomerByEmail(@RequestParam String email, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return customerService.getCustomersByEmail(email, pageable);
    }
}