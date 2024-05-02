package com.example.pensionat.repositories;

import com.example.pensionat.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Page<Customer> findByEmailContains(String email, Pageable pageable);
    Customer findByEmail(String email);
}