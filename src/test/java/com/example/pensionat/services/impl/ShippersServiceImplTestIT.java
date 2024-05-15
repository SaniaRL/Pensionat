package com.example.pensionat.services.impl;

import com.example.pensionat.repositories.ShippersRepo;
import com.example.pensionat.services.interfaces.ShippersService;
import com.example.pensionat.services.providers.ShippersStreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ShippersServiceImplTestIT {
    @Autowired
    private ShippersStreamProvider shippersStreamProvider = mock(ShippersStreamProvider.class); //Kan förenklas. Temp.
    @Autowired
    private ShippersRepo shippersRepo = mock(ShippersRepo.class);
    ShippersService sut;

    @BeforeEach()
    void setup(){
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
}
