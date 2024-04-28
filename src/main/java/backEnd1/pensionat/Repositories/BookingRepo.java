package backEnd1.pensionat.Repositories;

import backEnd1.pensionat.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookingRepo extends JpaRepository<Booking, Long> {

    @Query
    boolean findByCustomer_id (Long customerId);
    List<Booking> findByCustomerId(Long customerId);
}