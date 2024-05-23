package com.example.pensionat.repositories;

import com.example.pensionat.models.User;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepo extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.username = :username")
    void deleteByUsername(@Param("username") String username);
}