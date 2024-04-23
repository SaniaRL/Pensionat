package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.Models.OrderLine;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.OrderLineRepo;
import backEnd1.pensionat.services.interfaces.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineServicelmpl implements OrderLineService {

    final private OrderLineRepo orderLine;

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

}
