package backEnd1.pensionat.Repositories;

import backEnd1.pensionat.Models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    @Query
    Page<Customer> findByEmailContains(String email, Pageable pageable);
}