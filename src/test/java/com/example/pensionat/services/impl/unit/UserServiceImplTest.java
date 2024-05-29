package com.example.pensionat.services.impl.unit;

import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.models.Role;
import com.example.pensionat.models.User;
import com.example.pensionat.repositories.RoleRepo;
import com.example.pensionat.repositories.UserRepo;
import com.example.pensionat.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private RoleRepo roleRepo;
    @Mock
    private Model model;


    String username = "admin@mail.com";
    Collection<User> users = new ArrayList<>();
    Role role1 = new Role(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"), "Admin", users);
    Role role2 = new Role(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Receptionist", users);
    Collection<Role> roles = new ArrayList<>(Arrays.asList(role1, role2));
    SimpleRoleDTO roleDto1 = new SimpleRoleDTO("Admin");
    SimpleRoleDTO roleDto2 = new SimpleRoleDTO("Receptionist");
    Collection<SimpleRoleDTO> rolesDto = new ArrayList<>(Arrays.asList(roleDto1, roleDto2));

    User user1 = new User(UUID.fromString("123e4567-e89b-12d3-a456-556642440000"), username, "password",
                    true, null, null, roles);
    SimpleUserDTO userDTO1 = new SimpleUserDTO(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                                username, true, rolesDto);

    @Test
    void addToModel() {
    }

    @Test
    void addToModelUserSearch() {
    }

    @Test
    void getAllUsersPage() {
    }

    @Test
    void getSimpleUserDtoByUsername() {
    }

    @Test
    void getUsersBySearch() {
    }

    @Test
    void deleteUserByUsername() {
    }

    @Test
    void updateUser() {
    }

    @Test
    public void whenUpdateUserUsernameTakenShouldReturnCorrectly() {
        when(userRepo.findByUsername(any(String.class))).thenReturn(user1);
        when(model.getAttribute("originalUsername")).thenReturn("differentUsername");
        UserServiceImpl service = new UserServiceImpl(userRepo, roleRepo);

        String result = service.updateUser(userDTO1, model);

        assertEquals(userDTO1.getUsername() + " är upptaget.", result);
    }

    @Test
    void addUser() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void createPasswordResetTokenForUser() {
    }

    @Test
    void getUserByResetToken() {
    }

    @Test
    void removeResetToken() {
    }
}