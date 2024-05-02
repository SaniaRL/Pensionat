package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.DTOs.SimpleOrderLineDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.OrderLine;

public class OrderLineConverter {

    public static OrderLine simpleOrderLineDtoToOrderLine(SimpleOrderLineDTO orderLine, Booking b) {
        return OrderLine.builder().booking(b).room(RoomConverter.roomDtoToRoom(orderLine.getRoom()))
                .extraBeds(orderLine.getExtraBeds()).build();
    }
    public static SimpleOrderLineDTO orderLineTosimpleOrderLineDto(OrderLine orderLine) {
        return SimpleOrderLineDTO.builder().bookingId(orderLine.getBooking().getId())
                .room(RoomConverter.roomToRoomDto(orderLine.getRoom()))
                .extraBeds(orderLine.getExtraBeds()).build();
    }

    public static SimpleOrderLineDTO roomToSimpleOrderLineDTO(RoomDTO roomDTO) {
        int extraBeds = roomDTO.getRoomType().defaultNumberOfBeds;
        return SimpleOrderLineDTO.builder()
                .room(roomDTO)
                .extraBeds(extraBeds).build();
    }
}