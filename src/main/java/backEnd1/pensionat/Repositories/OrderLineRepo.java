package backEnd1.pensionat.Repositories;

import backEnd1.pensionat.Models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepo extends JpaRepository<OrderLine, Long> {
}