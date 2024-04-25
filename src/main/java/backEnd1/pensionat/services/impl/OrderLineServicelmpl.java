package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.SimpleOrderLineDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Models.OrderLine;
import backEnd1.pensionat.Repositories.BookingRepo;
import backEnd1.pensionat.Repositories.OrderLineRepo;
import backEnd1.pensionat.services.interfaces.OrderLineService;
import backEnd1.pensionat.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderLineServicelmpl implements OrderLineService {

    private final OrderLineRepo orderLine;
    private final BookingRepo bookingRepo;
    private final RoomService roomService;

    @Override
    public List<OrderLine> getAllOrderLines(){
        return orderLine.findAll();
    }
    @Override
    public String addOrderLine(OrderLine o){
        orderLine.save(o);
        return "Orderline added";
    }
    @Override
    public String removeOrderLineById(Long id) {
        orderLine.deleteById(id);
        return "Room " + id + " removed";
    }
    @Override
    public String addOrderLineFromSimpleOrderLineDto(SimpleOrderLineDTO orderLineDTO){
        Booking booking = bookingRepo.findById(orderLineDTO.getBookingId()).orElse(null);
        orderLine.save(simpleOrderLineDtoToOrderLine(orderLineDTO, booking));
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
