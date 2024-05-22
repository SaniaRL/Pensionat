package com.example.pensionat.controllers;

import com.example.pensionat.dtos.DetailedContractCustomerDTO;
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

    @GetMapping("/")
    public String contractHandleByPageSortedBy(
            Model model,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order){
        int currentPage = 1;
        //TODO Kolla om det ens är rätt shit
        contractCustomerService.addToModelSorted(currentPage, sort, order, model);
        return "contractCustomers";
    }

    @GetMapping(value = "/", params = "page")
    public String contractHandleByPageSortedBy(
            Model model,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam int page){
        //TODO Kolla om det ens är rätt shit
        contractCustomerService.addToModelSorted(page, sort, order, model);
        return "contractCustomers";
    }
//    @GetMapping("/contractHandle")
//    public String contractHandleCustomers(Model model){
//        int currentPage = 1;
//        contractCustomerService.addToModel(currentPage, model);
//        return "contractCustomers";
//    }

    @GetMapping(value = "/", params = "search")
    public String contractSearch(Model model,
                                 @RequestParam String search,
                                 @RequestParam(defaultValue = "id") String sort,
                                 @RequestParam(defaultValue = "asc") String order){
        contractCustomerService.addToModelSearch(1, search, sort, order, model);
        return "contractCustomers";
    }


    @GetMapping(value = "/", params = {"search", "page"})
    public String contractSearch(Model model,
                                 @RequestParam String search,
                                 @RequestParam(defaultValue = "id") String sort,
                                 @RequestParam(defaultValue = "asc") String order,
                                 @RequestParam int page){
        contractCustomerService.addToModelSearch(page, search, sort, order, model);
        return "contractCustomers";
    }

    @GetMapping(value = "/", params = {"search", "sort", "order", "page"})
    public String contractSearchSorted(Model model,
                                 @RequestParam String search,
                                 @RequestParam(defaultValue = "id") String sort,
                                 @RequestParam(defaultValue = "asc") String order,
                                       @RequestParam int page){
        contractCustomerService.addToModelSearch(page, search, sort, order, model);
        return "contractCustomers";
    }

    @GetMapping("/contractCustomer/{id}")
    public String getContractCustomer(Model model, @PathVariable long id) {
        DetailedContractCustomerDTO cc = contractCustomerService.getDetailedContractCustomerById(id);
        model.addAttribute("kund", cc);
        return "contractCustomer";
    }

//    @GetMapping("/contractCustomer/{id}")
//    public String getContractCustomerTwo(Model model, @PathVariable long id) {
//        DetailedContractCustomerDTO cc = contractCustomerService.getDetailedContractCustomerById(id);
//        model.addAttribute("kund", cc);
//        return "contractCustomer";
//    }

//    @GetMapping("/sort/contractCustomer/{id}")
//    public String getContractCustomerWhileSorted(Model model, @PathVariable long id) {
//        DetailedContractCustomerDTO cc = contractCustomerService.getDetailedContractCustomerById(id);
//        model.addAttribute("kund", cc);
//        return "contractCustomer";
//    }
}
