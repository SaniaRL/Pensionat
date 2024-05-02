package com.example.pensionat.repositories;

import com.example.pensionat.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {
}