package com.example.pensionat.services.impl.integration;

import com.example.pensionat.dtos.DetailedShippersDTO;
import com.example.pensionat.repositories.ShippersRepo;
import com.example.pensionat.services.impl.ShippersServiceImpl;
import com.example.pensionat.services.interfaces.ShippersService;
import com.example.pensionat.services.providers.ShippersStreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShippersServiceImplTestIT {
    ShippersService sut;
    @Autowired
    private ShippersStreamProvider shippersStreamProvider;
    @Autowired
    private ShippersRepo shippersRepo;
    @MockBean
    private JavaMailSender emailSender;

    @BeforeEach()
    void setup() {
        sut = new ShippersServiceImpl(shippersRepo, shippersStreamProvider);
    }

    @Test
    void getShippersToArrayWillFetch() throws IOException {
        Scanner s = new Scanner(shippersStreamProvider.getDataStream()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("id"));
        assertTrue(result.contains("email"));
        assertTrue(result.contains("companyName"));
        assertTrue(result.contains("contactName"));
        assertTrue(result.contains("contactTitle"));
        assertTrue(result.contains("streetAddress"));
        assertTrue(result.contains("city"));
        assertTrue(result.contains("postalCode"));
        assertTrue(result.contains("country"));
        assertTrue(result.contains("phone"));
        assertTrue(result.contains("fax"));
    }

    @Test
    void fetchAndSaveShippersShouldSaveToDatabase() throws IOException {
        ShippersStreamProvider shippersStreamProvider = mock(ShippersStreamProvider.class);
        when(shippersStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader()
                                                    .getResourceAsStream("shippers.json"));

        shippersRepo.deleteAll();

        DetailedShippersDTO[] tempArray = sut.getShippersToArray();
        sut.saveDownAllShippersToDB(tempArray);

        assertEquals(8, shippersRepo.count());
    }
}