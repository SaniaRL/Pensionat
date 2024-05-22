package com.example.pensionat.repositories;

import org.apache.catalina.Role;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, UUID> {

    Role findByName(String name);

}
