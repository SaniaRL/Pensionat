package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.SimpleOrderLineDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.OrderLine;

import static backEnd1.pensionat.services.convert.RoomConverter.roomDtoToRoom;
import static backEnd1.pensionat.services.convert.RoomConverter.roomToRoomDto;

public class OrderLineConverter {

    public static OrderLine simpleOrderLineDtoToOrderLine(SimpleOrderLineDTO orderLine, Booking b) {
        return OrderLine.builder().booking(b).room(roomDtoToRoom(orderLine.getRoom()))
                .extraBeds(orderLine.getExtraBeds()).build();
    }
    public static SimpleOrderLineDTO orderLineTosimpleOrderLineDto(OrderLine orderLine) {
        return SimpleOrderLineDTO.builder().bookingId(orderLine.getBooking().getId())
                .room(roomToRoomDto(orderLine.getRoom()))
                .extraBeds(orderLine.getExtraBeds()).build();
    }
}