package com.example.pensionat.repositories;

import com.example.pensionat.models.customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ContractCustomersRepo extends JpaRepository<customers, Long> {
    Page<customers> findByCompanyNameContainsOrContactNameContains(String searchParam1, String searchParam2, Pageable pageable);
}
