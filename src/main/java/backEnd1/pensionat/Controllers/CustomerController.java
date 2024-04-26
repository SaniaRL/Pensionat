package backEnd1.pensionat.Controllers;

import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;
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

   /* @RequestMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    } */

    @PostMapping("/add")
    public String addCustomer(@RequestParam String name, @RequestParam String email) {
        return customerService.addCustomer(new Customer(name, email));
    }

    @PostMapping("/addCustomerObject")
    public String addCustomerObject(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @DeleteMapping("/{id}/remove")
    public String removeCustomerById(@PathVariable Long id) {
        return customerService.removeCustomerById(id);
    }

    //DeleteMapping verkar ej fungera? Testar Requestmapping
    @RequestMapping("/{id}/removeHandler")
    public String removeCustomerByIdHandler(@PathVariable Long id, Model model) {
        customerService.removeCustomerById(id);
        return handleCustomers(model);
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

    //Temp metod nedan. Tas bort sen.
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