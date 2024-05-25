package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.BookingFormQueryDTO;
import com.example.pensionat.dtos.DetailedRoomDTO;
import com.example.pensionat.dtos.RoomDTO;
import com.example.pensionat.enums.RoomType;
import com.example.pensionat.models.Room;
import com.example.pensionat.repositories.RoomRepo;
import com.example.pensionat.services.impl.RoomServicelmpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class RoomServicelmplTest {
    @Mock
    private RoomRepo roomRepo;

    @MockBean
    private JavaMailSender emailSender;

    Long roomId = 301L;
    Room room = new Room(roomId, 2);


    @Test
    void addToModel() {

    }

    @Test
    void getAllRoomsPage() {

    }

    @Test
    void getRoomByID() {
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));
        RoomServicelmpl service = new RoomServicelmpl(roomRepo);
        RoomDTO actual = service.getRoomByID(roomId);
        assertEquals(actual.getId(), roomId);
    }

    @Test
    void enoughRooms() {
        RoomServicelmpl service = new RoomServicelmpl(roomRepo);

        LocalDate dateNow = LocalDate.now();
        LocalDate dateYesterday = LocalDate.now().minusDays(1);
        LocalDate dateNowPlusSeven = LocalDate.now().plusDays(7);

        BookingFormQueryDTO query1 = new BookingFormQueryDTO(dateNowPlusSeven, dateNow, 1, 1);

        List<RoomDTO> queryRooms = new ArrayList<>();
        queryRooms.add(new RoomDTO(1L, RoomType.DOUBLE));
        queryRooms.add(new RoomDTO(2L, RoomType.SINGLE));

        String expected = "Startdatum måste vara före slutdatum.";
        String actual = service.enoughRooms(query1, queryRooms);
        assertEquals(expected, actual);

        BookingFormQueryDTO query2 = new BookingFormQueryDTO(dateYesterday, dateNow, 1, 1);

        expected = "Tidigaste accepterade startDatum är dagens datum.";
        actual = service.enoughRooms(query2, queryRooms);
        assertEquals(expected, actual);

        BookingFormQueryDTO query3 = new BookingFormQueryDTO(dateNow, dateNowPlusSeven, 1, 2);

        expected = "Vill du boka fler rum än sängar? Det är orimligt!";
        actual = service.enoughRooms(query3, queryRooms);
        assertEquals(expected, actual);

        BookingFormQueryDTO query4 = new BookingFormQueryDTO(dateNow, dateNowPlusSeven, 5, 4);

        expected = "Det önskade antalet rum överstiger antalet lediga rum.";
        actual = service.enoughRooms(query4, queryRooms);
        assertEquals(expected, actual);

        BookingFormQueryDTO query5 = new BookingFormQueryDTO(dateNow, dateNowPlusSeven, 6, 1);

        expected = "Det önskade antalet sängar överstiger antalet lediga sängar.";
        actual = service.enoughRooms(query5, queryRooms);
        assertEquals(expected, actual);

        BookingFormQueryDTO query6 = new BookingFormQueryDTO(dateNow, dateNowPlusSeven, 2, 1);

        expected = "";
        actual = service.enoughRooms(query6, queryRooms);
        assertEquals(expected, actual);

    }
}