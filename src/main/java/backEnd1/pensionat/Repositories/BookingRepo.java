package backEnd1.pensionat.Repositories;

import backEnd1.pensionat.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {
}