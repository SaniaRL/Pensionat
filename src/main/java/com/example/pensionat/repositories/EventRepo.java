package com.example.pensionat.repositories;

import com.example.pensionat.models.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Long> {

}

