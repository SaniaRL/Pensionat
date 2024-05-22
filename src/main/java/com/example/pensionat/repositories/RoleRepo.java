package com.example.pensionat.repositories;


import com.example.pensionat.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {

    Role findByName(String name);

}
