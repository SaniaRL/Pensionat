package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.ContractCustomerDTO;
import com.example.pensionat.models.customers;
import com.example.pensionat.repositories.ContractCustomersRepo;
import com.example.pensionat.services.convert.ContractCustomerConverter;
import com.example.pensionat.services.interfaces.ContractCustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ContractCustomerServiceImpl implements ContractCustomerService {

    ContractCustomersRepo contractCustomersRepo;

    public ContractCustomerServiceImpl(ContractCustomersRepo contractCustomersRepo) {
        this.contractCustomersRepo = contractCustomersRepo;
    }

    @Override
    public Page<ContractCustomerDTO> getAllCustomersPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 10);

        //förmodligen mappa om till DTO
        Page<customers> page = contractCustomersRepo.findAll(pageable);
        return page.map(ContractCustomerConverter::customersToContractCustomerDto);
    }

    @Override
    public Page<ContractCustomerDTO> getAllCustomersSortedPage(int pageNum, String sortBy, String order) {
        Pageable pageable;
        if(order.equals("asc")) {
            pageable = PageRequest.of(pageNum - 1, 10, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNum - 1, 10, Sort.by(sortBy).descending());
        }

        //förmodligen mappa om till DTO
        Page<customers> page = contractCustomersRepo.findAll(pageable);
        return page.map(ContractCustomerConverter::customersToContractCustomerDto);
    }

    @Override
    public customers getCustomerById(Long id) {
        return contractCustomersRepo.findById(id).orElse(null);
    }
}
