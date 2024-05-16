package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.*;
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
    RoomDTO testRoomDTO = new RoomDTO(5L, RoomType.SINGLE);
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
    void detailedOrderLineDtoToOrderLine() {
        DetailedBookingDTO detailedBookingDTO = new DetailedBookingDTO();
        detailedBookingDTO.setId(booking.getId());
        detailedBookingDTO.setCustomer(new SimpleCustomerDTO(booking.getCustomer().getName(), booking.getCustomer().getEmail()));
        detailedBookingDTO.setStartDate(booking.getStartDate());
        detailedBookingDTO.setEndDate(booking.getEndDate());

        DetailedOrderLineDTO detailedOrderLineDTO = new DetailedOrderLineDTO();
        detailedOrderLineDTO.setId(1L);
        detailedOrderLineDTO.setExtraBeds(2);
        detailedOrderLineDTO.setBooking(detailedBookingDTO);
        detailedOrderLineDTO.setRoom(roomDTO);

        OrderLine actual = OrderLineConverter.detailedOrderLineDtoToOrderLine(detailedOrderLineDTO, booking);

        assertNotNull(actual);
        assertEquals(detailedOrderLineDTO.getId(), actual.getId());
        assertEquals(detailedOrderLineDTO.getExtraBeds(), actual.getExtraBeds());

        assertEquals(detailedOrderLineDTO.getBooking().getId(), actual.getBooking().getId());
        assertEquals(detailedOrderLineDTO.getBooking().getStartDate(), actual.getBooking().getStartDate());
        assertEquals(detailedOrderLineDTO.getBooking().getEndDate(), actual.getBooking().getEndDate());
        assertEquals(detailedOrderLineDTO.getBooking().getCustomer().getName(), actual.getBooking().getCustomer().getName());
        assertEquals(detailedOrderLineDTO.getBooking().getCustomer().getEmail(), actual.getBooking().getCustomer().getEmail());
        assertEquals(detailedOrderLineDTO.getRoom().getId(), actual.getRoom().getId());

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
    void orderLineToDetailedOrderLineDto() {
        OrderLine orderLine = new OrderLine(booking, room, 2);
        orderLine.setId(1L);
        DetailedOrderLineDTO actual = OrderLineConverter.orderLineToDetailedOrderLineDto(orderLine);

        assertNotNull(actual);
        assertEquals(orderLine.getId(), actual.getId());
        assertEquals(orderLine.getExtraBeds(), actual.getExtraBeds());

        assertEquals(orderLine.getBooking().getId(), actual.getBooking().getId());
        assertEquals(orderLine.getBooking().getStartDate(), actual.getBooking().getStartDate());
        assertEquals(orderLine.getBooking().getEndDate(), actual.getBooking().getEndDate());
        assertEquals(orderLine.getBooking().getCustomer().getName(), actual.getBooking().getCustomer().getName());
        assertEquals(orderLine.getBooking().getCustomer().getEmail(), actual.getBooking().getCustomer().getEmail());
        assertEquals(orderLine.getRoom().getId(), actual.getRoom().getId());
    }


    @Test
    void roomToSimpleOrderLineDTO() {
        SimpleOrderLineDTO actualVersion = OrderLineConverter.roomToSimpleOrderLineDTO(testRoomDTO);
        assertEquals(testRoomDTO, actualVersion.getRoom());
        assertEquals(expectedExtraBeds, actualVersion.getExtraBeds());
    }
}