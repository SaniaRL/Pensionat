package com.example.pensionat.controllers;

import com.example.pensionat.dtos.DetailedContractCustomerDTO;
import com.example.pensionat.services.interfaces.ContractCustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
class ContractCustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContractCustomerService contractCustomerService;

    @Test
    void contractHandleByPageSortedBy() {
    }

    @Test
    void testContractHandleByPageSortedBy() {
    }

    @Test
    void contractSearch() {
    }

    @Test
    void testContractSearch() {
    }

    @Test
    void contractSearchSorted() {
    }

    @Test
    void getContractCustomer() throws Exception {
        DetailedContractCustomerDTO mockDto = new DetailedContractCustomerDTO();
        long customerId = 1L;

        Mockito.when(contractCustomerService.getDetailedContractCustomerById(customerId)).thenReturn(mockDto);

        this.mvc.perform(get("/contractCustomer/contractCustomer/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(view().name("contractCustomer"))
                .andExpect(model().attributeExists("kund"))
                .andExpect(model().attribute("kund", mockDto));
    }
}