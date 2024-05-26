package com.example.pensionat.services.impl.unit;

import com.example.pensionat.dtos.DetailedShippersDTO;
import com.example.pensionat.models.Shippers;
import com.example.pensionat.repositories.ShippersRepo;
import com.example.pensionat.services.impl.ShippersServiceImpl;
import com.example.pensionat.services.interfaces.ShippersService;
import com.example.pensionat.services.providers.ShippersStreamProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ShippersServiceImplTest {
    ShippersService sut;
    private final ShippersStreamProvider shippersStreamProvider = mock(ShippersStreamProvider.class); //Kan förenklas. Temp.
    private final ShippersRepo shippersRepo = mock(ShippersRepo.class);

    @MockBean
    private JavaMailSender emailSender;

    @BeforeEach()
    void setup() {
        sut = new ShippersServiceImpl(shippersRepo, shippersStreamProvider);
    }

    @Test
    void whenGetShippersShouldMapCorrectly() throws IOException {
        when(shippersStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));

        DetailedShippersDTO[] shippersArrayMock = sut.getShippersToArray();

        assertEquals(8, shippersArrayMock.length);
        assertEquals("Gävhult", shippersArrayMock[0].getCity());
        assertEquals("8238-27759", shippersArrayMock[1].getFax());
        assertEquals("55768", shippersArrayMock[3].getPostalCode());
        assertEquals("Göran Östlund", shippersArrayMock[5].getContactName());
        assertEquals("anna.gustafsson@yahoo.com", shippersArrayMock[7].getEmail());

    }

    @Test
    void fetchAndSaveShippersShouldInsertNewRecords() throws IOException {
        when(shippersStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));
        when(shippersRepo.findByCompanyName(Mockito.anyString())).thenReturn(Optional.empty());

        DetailedShippersDTO[] tempArray = sut.getShippersToArray();
        sut.saveDownAllShippersToDB(tempArray);

        verify(shippersRepo, times(8)).save(any(Shippers.class));
    }

    @Test
    void fetchAndSaveShippersShouldUpdateNewRecords() throws IOException {
        Shippers existing = new Shippers();
        existing.setCompanyName("Svensson-Karlsson");

        when(shippersStreamProvider.getDataStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));
        when(shippersRepo.findByCompanyName(Mockito.anyString())).thenReturn(Optional.empty());
        when(shippersRepo.findByCompanyName("Svensson-Karlsson")).thenReturn(Optional.of(existing));

        DetailedShippersDTO[] tempArray = sut.getShippersToArray();
        sut.saveDownAllShippersToDB(tempArray);

        verify(shippersRepo, times(8)).save(any(Shippers.class));
        verify(shippersRepo, times(1)).save(argThat(shipper -> shipper.getCompanyName().equals("Svensson-Karlsson")));
    }

}