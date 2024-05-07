package com.example.pensionat.repositories;

import com.example.pensionat.models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderLineRepo extends JpaRepository<OrderLine, Long> {
//    OrderLine deleteByBookingIdAndRoomId(Long bookingId, Long roomId);
    List<OrderLine> findAllByBookingId(Long bookingId);
}