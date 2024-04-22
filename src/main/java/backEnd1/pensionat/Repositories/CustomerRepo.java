package backEnd1.pensionat.Repositories;

import backEnd1.pensionat.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}