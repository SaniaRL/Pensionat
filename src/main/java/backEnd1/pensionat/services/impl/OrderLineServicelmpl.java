package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.DetailedOrderLineDTO;
import backEnd1.pensionat.DTOs.SimpleOrderLineDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.OrderLine;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.BookingRepo;
import backEnd1.pensionat.Repositories.OrderLineRepo;
import backEnd1.pensionat.services.convert.BookingConverter;
import backEnd1.pensionat.services.convert.RoomTypeConverter;
import backEnd1.pensionat.services.interfaces.OrderLineService;
import backEnd1.pensionat.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderLineServicelmpl implements OrderLineService {

    private final OrderLineRepo orderLineRepo;
    private final BookingRepo bookingRepo;
    private final RoomService roomService;

    @Override
    public List<OrderLine> getAllOrderLines(){
        return orderLineRepo.findAll();
    }
    @Override
    public String addOrderLine(OrderLine o){
        orderLineRepo.save(o);
        return "Orderline added";
    }

    @Override
    public String addOrderLine(DetailedOrderLineDTO o){

        int extraBeds = o.getExtraBeds();
        Booking booking = BookingConverter.DetailedBookingDTOtoBooking(o.getBooking());
        int roomType = RoomTypeConverter.convertToInt(o.getRoom().getRoomType());
        Room room = Room.builder()
                .id(o.getRoom().getId())
                .typeOfRoom(roomType).build();

        OrderLine orderLine = new OrderLine(booking, room, extraBeds);
        orderLineRepo.save(orderLine);

        return "Orderline added";
    }
    @Override
    public String removeOrderLineById(Long id) {
        orderLineRepo.deleteById(id);
        return "Room " + id + " removed";
    }
    @Override
    public String addOrderLineFromSimpleOrderLineDto(SimpleOrderLineDTO orderLineDTO){
        Booking booking = bookingRepo.findById(orderLineDTO.getBookingId()).orElse(null);
        orderLineRepo.save(simpleOrderLineDtoToOrderLine(orderLineDTO, booking));
        return "Orderline added";
    }

    @Override
    public OrderLine simpleOrderLineDtoToOrderLine(SimpleOrderLineDTO orderLine, Booking b) {
        return OrderLine.builder().booking(b).room(roomService.roomDtoToRoom(orderLine.getRoom()))
                        .extraBeds(orderLine.getExtraBeds()).build();
    }

    @Override
    public SimpleOrderLineDTO orderLineTosimpleOrderLineDto(OrderLine orderLine) {
        return SimpleOrderLineDTO.builder().bookingId(orderLine.getBooking().getId())
                                 .room(roomService.roomToRoomDto(orderLine.getRoom()))
                                 .extraBeds(orderLine.getExtraBeds()).build();
    }

    @Override
    public List<SimpleOrderLineDTO> getOrderLinesByBookingId(Long id) {
        return getAllOrderLines().stream().filter(o -> Objects.equals(o.getBooking().getId(), id))
                                          .map(o -> orderLineTosimpleOrderLineDto(o)).toList();
    }
}
