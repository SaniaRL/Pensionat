package backEnd1.pensionat.services.interfaces;

import backEnd1.pensionat.DTOs.DetailedOrderLineDTO;
import backEnd1.pensionat.DTOs.SimpleOrderLineDTO;
import backEnd1.pensionat.Models.OrderLine;

import java.util.List;

public interface OrderLineService {

    public List<OrderLine> getAllOrderLines();
    public String addOrderLine(OrderLine o);
    String addOrderLine(DetailedOrderLineDTO o);
    public String removeOrderLineById(Long id);
    String addOrderLineFromSimpleOrderLineDto(SimpleOrderLineDTO orderLine);
    List<SimpleOrderLineDTO> getOrderLinesByBookingId(Long id);
    List<SimpleOrderLineDTO> findOrderLinesByBookingId(Long bookingId);

    }