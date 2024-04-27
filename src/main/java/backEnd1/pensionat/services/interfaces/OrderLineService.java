package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.DTOs.SimpleOrderLineDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.OrderLine;

import java.util.List;

public interface OrderLineService {

    //public SimpleOrderLineDTO orderLineToSimpleOrderLineDto(OrderLine o);
    //public DetailedOrderLineDTO orderLineToDetailedOrderLineDto(OrderLine o);
    //public OrderLine detailedOrderLineDtoToOrderLine (DetailedOrderLineDTO o);

    public List<OrderLine> getAllOrderLines();
    public String addOrderLine(OrderLine o);
    public String removeOrderLineById(Long id);
    String addOrderLineFromSimpleOrderLineDto(SimpleOrderLineDTO orderLine);
    OrderLine simpleOrderLineDtoToOrderLine(SimpleOrderLineDTO orderLine, Booking b);
    SimpleOrderLineDTO orderLineTosimpleOrderLineDto(OrderLine orderLine);
    List<SimpleOrderLineDTO> getOrderLinesByBookingId(Long id);
}
