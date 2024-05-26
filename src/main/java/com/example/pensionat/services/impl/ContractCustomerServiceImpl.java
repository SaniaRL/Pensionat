package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.AllCustomersDTO;
import com.example.pensionat.dtos.ContractCustomerDTO;
import com.example.pensionat.dtos.DetailedContractCustomerDTO;
import com.example.pensionat.models.allcustomers;
import com.example.pensionat.models.customers;
import com.example.pensionat.repositories.ContractCustomersRepo;
import com.example.pensionat.services.convert.ContractCustomerConverter;
import com.example.pensionat.services.interfaces.ContractCustomerService;
import com.example.pensionat.services.providers.XmlStreamProvider;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


@AllArgsConstructor
@Service
public class ContractCustomerServiceImpl implements ContractCustomerService {

    ContractCustomersRepo contractCustomersRepo;
    final XmlStreamProvider xmlStreamProvider;

    //Används denna ens ?
    @Override
    public Page<ContractCustomerDTO> getAllCustomersPage(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<customers> page = contractCustomersRepo.findAll(pageable);
        return page.map(ContractCustomerConverter::customersToContractCustomerDto);
    }

    @Override
    public Page<ContractCustomerDTO> getAllCustomersSortedPage(int pageNum, String sortBy, String order, int pageSize) {
        Pageable pageable;
        if(order.equals("asc")) {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sortBy).descending());
        }

        Page<customers> page = contractCustomersRepo.findAll(pageable);

        return page.map(ContractCustomerConverter::customersToContractCustomerDto);
    }

    @Override
    public customers getCustomerById(Long id) {
        return contractCustomersRepo.findById(id).orElse(null);
    }

    @Override
    public DetailedContractCustomerDTO getDetailedContractCustomerById(Long id) {
        customers cCustomer = contractCustomersRepo.findById(id).orElse(null);
        if(cCustomer!= null){
            return ContractCustomerConverter.contractCustomerToDetailedContractCustomer(cCustomer);
        }
        return null;
    }

    @Override
    public Page<ContractCustomerDTO> getCustomersBySearch(int pageNum, String search, String sort, String order, int pageSize){
        Pageable pageable;

        if(order.equals("asc")) {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sort).descending());
        }
        Page<customers> page = contractCustomersRepo.findByCompanyNameContainsOrContactNameContains(search, search, pageable);
        return page.map(ContractCustomerConverter::customersToContractCustomerDto);
    }

    @Override
    public void addToModel(int currentPage, Model model, int pageSize){
        Page<ContractCustomerDTO> c = getAllCustomersPage(currentPage, pageSize);
        addToModelUtil(c, model, currentPage);
    }

    @Override
    public void addToModelSorted(int currentPage, String sortBy, String order, Model model, int pageSize){
        Page<ContractCustomerDTO> c = getAllCustomersSortedPage(currentPage, sortBy, order, pageSize);
        addToModelUtil(c, model, currentPage);
        model.addAttribute("order", order);
        model.addAttribute("sort", sortBy);
    }

    @Override
    public void addToModelSearch(int currentPage, String search, String sort, String order, Model model, int pageSize) {
        Page<ContractCustomerDTO> p = getCustomersBySearch(currentPage, search, sort, order, pageSize);
        addToModelUtil(p, model, currentPage);
        model.addAttribute("order", order);
        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
    }

    private void addToModelUtil(Page<ContractCustomerDTO> p, Model model, int currentPage){
        model.addAttribute("allCustomers", p.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", p.getTotalElements());
        model.addAttribute("totalPages", p.getTotalPages());
    }

    @Override
    public void saveAll(List<DetailedContractCustomerDTO> customers){
        contractCustomersRepo.saveAll(customers.stream()
                .map(ContractCustomerConverter::detailedContractCustomerToCustomers).toList());
    }

    @Override
    public AllCustomersDTO fetchContractCustomers() throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        InputStream stream =  xmlStreamProvider.getDataStream();

        allcustomers allCustomers = xmlMapper.readValue(stream, allcustomers.class);

        return ContractCustomerConverter.allCustomerToAllCustomerDTO(allCustomers);
    }
}
