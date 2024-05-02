package com.example.pensionat.repositories;

import com.example.pensionat.models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepo extends JpaRepository<OrderLine, Long> {
}