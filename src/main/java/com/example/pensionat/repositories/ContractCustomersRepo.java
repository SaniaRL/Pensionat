package com.example.pensionat.repositories;

import com.example.pensionat.models.customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractCustomersRepo extends JpaRepository<customers, Long> {
}
