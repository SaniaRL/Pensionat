package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.RoomDTO;
import com.example.pensionat.dtos.SimpleOrderLineDTO;
import com.example.pensionat.enums.RoomType;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.Customer;
import com.example.pensionat.models.OrderLine;
import com.example.pensionat.models.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineConverterTest {

    String name = "Maria";
    String email = "maria@mail.com";
    LocalDate startDate = LocalDate.parse("2024-05-14");
    LocalDate endDate = LocalDate.parse("2024-05-17");
    Room room = new Room(301L, 2);
    RoomDTO roomDTO = new RoomDTO(401L, RoomType.DOUBLE);

    Customer customer = new Customer(name, email);
    Booking booking = new Booking(customer, startDate, endDate);
    OrderLine orderLine = new OrderLine(booking, room, 2);
    SimpleOrderLineDTO simpleOrderLineDTO = new SimpleOrderLineDTO(booking.getId(), roomDTO, 1);
    RoomDTO testRoomDTO = new RoomDTO(5, RoomType.SINGLE);
    int expectedExtraBeds = RoomType.SINGLE.defaultNumberOfBeds;


    @Test
    void simpleOrderLineDtoToOrderLine() {
        OrderLine actual = OrderLineConverter.simpleOrderLineDtoToOrderLine(simpleOrderLineDTO, booking);

        assertEquals(actual.getBooking().getId(), booking.getId());
        assertEquals(actual.getBooking().getStartDate(), booking.getStartDate());
        assertEquals(actual.getBooking().getEndDate(), booking.getEndDate());

        assertEquals(actual.getBooking().getCustomer().getId(), booking.getCustomer().getId());
        assertEquals(actual.getBooking().getCustomer().getName(), booking.getCustomer().getName());
        assertEquals(actual.getBooking().getCustomer().getEmail(), booking.getCustomer().getEmail());

        assertEquals(actual.getRoom().getId(), simpleOrderLineDTO.getRoom().getId());
        assertEquals(actual.getRoom().getTypeOfRoom(), RoomTypeConverter
                                                .convertToInt(simpleOrderLineDTO.getRoom().getRoomType()));

        assertEquals(actual.getExtraBeds(), simpleOrderLineDTO.getExtraBeds());
    }

    @Test
    void orderLineTosimpleOrderLineDto() {
        SimpleOrderLineDTO actual = OrderLineConverter.orderLineTosimpleOrderLineDto(orderLine);

        assertEquals(actual.getBookingId(), orderLine.getBooking().getId());

        assertEquals(actual.getRoom().getId(), orderLine.getRoom().getId());
        assertEquals(actual.getRoom().getRoomType(), RoomTypeConverter
                .convertFromInt(orderLine.getRoom().getTypeOfRoom()));

        assertEquals(actual.getExtraBeds(), orderLine.getExtraBeds());
    }

    @Test
    void roomToSimpleOrderLineDTO() {
        SimpleOrderLineDTO actualVersion = OrderLineConverter.roomToSimpleOrderLineDTO(testRoomDTO);

        assertEquals(testRoomDTO, actualVersion.getRoom());
        assertEquals(expectedExtraBeds, actualVersion.getExtraBeds());
    }
}