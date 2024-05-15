package com.example.pensionat.repositories;

import com.example.pensionat.models.Shippers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippersRepo extends JpaRepository<Shippers, Long> {
    Optional findByCompanyName(String companyName);
}
