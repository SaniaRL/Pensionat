package com.example.pensionat.repositories;

import com.example.pensionat.models.events.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepo extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.RoomNo = :roomNo")
    Page<Event> findByRoomNo(@Param("roomNo") String roomNo, Pageable pageable);
}