package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.DetailedShippersDTO;
import com.example.pensionat.repositories.ShippersRepo;
import com.example.pensionat.services.interfaces.ShippersService;
import com.example.pensionat.services.providers.ShippersStreamProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@SpringBootTest
class ShippersServiceImplTest {
    private ShippersStreamProvider shippersStreamProvider = mock(ShippersStreamProvider.class); //Kan förenklas. Temp.
    private ShippersRepo shippersRepo = mock(ShippersRepo.class);
    ShippersService sut;

    @BeforeEach()
    void setup(){
        sut = new ShippersServiceImpl(shippersRepo, shippersStreamProvider);
    }

    @Test
    void whenGetShippersShouldMapCorrectly() throws IOException {
        when(shippersStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));

        DetailedShippersDTO[] shippersArrayMock = sut.getShippersToArray();

        assertEquals(8, shippersArrayMock.length);
        assertEquals("Gävhult", shippersArrayMock[0].getCity() );
        assertEquals("8238-27759", shippersArrayMock[1].getFax());
        assertEquals("55768", shippersArrayMock[3].getPostalCode());
        assertEquals("Göran Östlund", shippersArrayMock[5].getContactName());
        assertEquals("anna.gustafsson@yahoo.com", shippersArrayMock[7].getEmail());

    }
    @Test
    void saveDownAllShippersToDB() {
    }

}