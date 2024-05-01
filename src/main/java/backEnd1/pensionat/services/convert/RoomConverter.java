package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.OrderLineDTO;
import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.DTOs.SimpleOrderLineDTO;
import backEnd1.pensionat.Models.Room;

public class RoomConverter {
    public static Room roomDtoToRoom(RoomDTO room) {
        return Room.builder().id(room.getId())
                             .typeOfRoom(RoomTypeConverter.convertToInt(room.getRoomType()))
                             .build();
    }
    public static RoomDTO roomToRoomDto(Room room) {
        return RoomDTO.builder().id(room.getId())
                                .roomType(RoomTypeConverter.convertFromInt(room.getTypeOfRoom()))
                                .build();
    }

    public static RoomDTO orderLineToRoomDTO(SimpleOrderLineDTO orderLine) {
        return RoomDTO.builder()
                .id(orderLine.getRoom().getId())
                .roomType(orderLine.getRoom().getRoomType())
                .build();
    }
}