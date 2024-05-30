package com.example.pensionat.services.impl.unit;

import com.example.pensionat.models.User;
import com.example.pensionat.repositories.UserRepo;
import com.example.pensionat.services.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Mock
    UserRepo userRepository;

    @InjectMocks
    UserDetailsServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        User user = new User();
        user.setUsername("admin@mail.com");
        user.setPassword("password");
        when(userRepository.findByUsername(any(String.class))).thenReturn(user);

        UserDetails userDetails = service.loadUserByUsername("admin@mail.com");

        assertEquals("admin@mail.com", userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("nonexistentuser");
        });
    }
}