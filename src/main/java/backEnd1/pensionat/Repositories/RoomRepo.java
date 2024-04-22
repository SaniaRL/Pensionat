package backEnd1.pensionat.Repositories;

import backEnd1.pensionat.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {
}