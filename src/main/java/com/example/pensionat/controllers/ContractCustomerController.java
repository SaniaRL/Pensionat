package com.example.pensionat.controllers;

import com.example.pensionat.services.interfaces.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/contractCustomer")
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    @GetMapping("/handle/{pageNumber}")
    public String contractHandleByPageSortedBy(
            Model model,
            @PathVariable("pageNumber") int currentPage,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order){
        System.out.println(sort + " " + order);
        contractCustomerService.addToModelSorted(currentPage, sort, order, model);
        return "contractCustomers";
    }

/*
    @GetMapping("/contractCustomer")
    public String getContractCustomers(Model model) {
        int currentPage = 1;
        contractCustomerService.addToModel(currentPage, model);
        model.addAttribute("asc", false);
        return "contractCustomers";
    }

 */

    @GetMapping("/{id}")
    public String getContractCustomer(Model model, @PathVariable long id) {
        //TODO Hämta model eller DTO baserat på ID
        model.addAttribute("id", id);
        return "contractCustomer";
    }

    @GetMapping("/contractHandle")
    public String contractHandleCustomers(Model model){
        int currentPage = 1;
        contractCustomerService.addToModel(currentPage, model);
        model.addAttribute("order", "asc");
        return "contractCustomers";
    }
}
