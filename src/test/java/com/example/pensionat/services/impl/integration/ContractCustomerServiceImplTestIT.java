package com.example.pensionat.services.impl.integration;

import com.example.pensionat.repositories.ContractCustomersRepo;
import com.example.pensionat.services.impl.ContractCustomerServiceImpl;
import com.example.pensionat.services.providers.XmlStreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContractCustomerServiceImplTestIT {
    @Autowired
    ContractCustomersRepo contractCustomerRepository;

    @Autowired
    XmlStreamProvider xmlStreamProvider;

    ContractCustomerServiceImpl sut;

    @Test
    void getContractCustomersWillFetch() throws IOException {
        sut = new ContractCustomerServiceImpl(contractCustomerRepository, xmlStreamProvider);
        Scanner s = new Scanner(xmlStreamProvider.getDataStream()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("<allcustomers>"));
        assertTrue(result.contains("</allcustomers>"));
        assertTrue(result.contains("<customers>"));
        assertTrue(result.contains("</customers>"));
        assertTrue(result.contains("<id>"));
        assertTrue(result.contains("</id>"));
        assertTrue(result.contains("<companyName>"));
        assertTrue(result.contains("</companyName>"));
        assertTrue(result.contains("<contactName>"));
        assertTrue(result.contains("</contactName>"));
        assertTrue(result.contains("<contactTitle>"));
        assertTrue(result.contains("</contactTitle>"));
        assertTrue(result.contains("<streetAddress>"));
        assertTrue(result.contains("</streetAddress>"));
        assertTrue(result.contains("<city>"));
        assertTrue(result.contains("</city>"));
        assertTrue(result.contains("<postalCode>"));
        assertTrue(result.contains("</postalCode>"));
        assertTrue(result.contains("<country>"));
        assertTrue(result.contains("</country>"));
        assertTrue(result.contains("<phone>"));
        assertTrue(result.contains("</phone>"));
        assertTrue(result.contains("<fax>"));
        assertTrue(result.contains("</fax>"));
    }


    @Test
    void SaveAllShouldSaveToDatabase() throws IOException {
        XmlStreamProvider xmlStreamProvider = mock(XmlStreamProvider.class);
        when(xmlStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("contract.xml"));

        sut = new ContractCustomerServiceImpl(contractCustomerRepository, xmlStreamProvider);

        // Arrange
        contractCustomerRepository.deleteAll();

        // Act
        sut.saveAll(sut.fetchContractCustomers().getContractCustomerList());

        //Assert
        assertEquals(3, contractCustomerRepository.count());
    }
}
