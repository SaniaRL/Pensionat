package com.example.pensionat.repositories;

import com.example.pensionat.models.User;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepo extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    @Modifying
    @Transactional
    void deleteByUsername(@Param("username") String username);

    Page<User> findByUsernameContainsOrRolesNameContains(String searchParam1, String searchParam2, Pageable pageable);

    User findById(java.util.UUID id);

    //Ändring
    User findByResetToken(String token);

}