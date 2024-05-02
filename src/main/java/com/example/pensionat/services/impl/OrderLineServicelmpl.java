package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.DetailedOrderLineDTO;
import com.example.pensionat.dtos.SimpleOrderLineDTO;
import com.example.pensionat.models.Booking;
import com.example.pensionat.models.OrderLine;
import com.example.pensionat.models.Room;
import com.example.pensionat.repositories.BookingRepo;
import com.example.pensionat.repositories.OrderLineRepo;
import com.example.pensionat.services.interfaces.OrderLineService;
import com.example.pensionat.services.convert.BookingConverter;
import com.example.pensionat.services.convert.OrderLineConverter;
import com.example.pensionat.services.convert.RoomTypeConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderLineServicelmpl implements OrderLineService {

    private final OrderLineRepo orderLineRepo;
    private final BookingRepo bookingRepo;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<OrderLine> getAllOrderLines(){
        return orderLineRepo.findAll();
    }
    @Override
    public String addOrderLine(OrderLine o){
        orderLineRepo.save(o);
        return "OrderLine added";
    }

    @Override
    public String addOrderLine(DetailedOrderLineDTO o){

        int extraBeds = o.getExtraBeds();
        Booking booking = BookingConverter.detailedBookingDTOtoBooking(o.getBooking());
        int roomType = RoomTypeConverter.convertToInt(o.getRoom().getRoomType());
        Room room = Room.builder()
                .id(o.getRoom().getId())
                .typeOfRoom(roomType).build();

        OrderLine orderLine = new OrderLine(booking, room, extraBeds);
        orderLineRepo.save(orderLine);

        return "OrderLine added";
    }
    @Override
    public String removeOrderLineById(Long id) {
        orderLineRepo.deleteById(id);
        return "Room " + id + " removed";
    }
    @Override
    public String addOrderLineFromSimpleOrderLineDto(SimpleOrderLineDTO orderLineDTO){
        Booking booking = bookingRepo.findById(orderLineDTO.getBookingId()).orElse(null);
        orderLineRepo.save(OrderLineConverter.simpleOrderLineDtoToOrderLine(orderLineDTO, booking));
        return "OrderLine added";
    }

    @Override
    public List<SimpleOrderLineDTO> getOrderLinesByBookingId(Long id) {
        return getAllOrderLines().stream().filter(o -> Objects.equals(o.getBooking().getId(), id))
                                          .map(OrderLineConverter::orderLineTosimpleOrderLineDto).toList();
    }

    @Override
    public List<SimpleOrderLineDTO> findOrderLinesByBookingId(Long bookingId) {
        String query = "SELECT o FROM OrderLine o INNER JOIN o.booking b WHERE b.id = :bookingId";

        return entityManager.createQuery(query, OrderLine.class)
                .setParameter("bookingId", bookingId)
                .getResultList().stream().map(OrderLineConverter::orderLineTosimpleOrderLineDto).toList();
    }

}