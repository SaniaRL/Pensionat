package com.example.pensionat.services.impl.unit;

import com.example.pensionat.dtos.DetailedUserDTO;
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
import static org.mockito.Mockito.*;

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
    Collection<SimpleRoleDTO> rolesDto1 = new ArrayList<>(Arrays.asList(roleDto1, roleDto2));
    Collection<SimpleRoleDTO> rolesDto2 = new ArrayList<>();

    User user1 = new User(UUID.fromString("123e4567-e89b-12d3-a456-556642440000"), username, "password",
                    true, null, null, roles);
    SimpleUserDTO userDto1 = new SimpleUserDTO(UUID.fromString("123e4567-e89b-12d3-a456-556642440000"),
                                username, true, rolesDto1);
    SimpleUserDTO userDto2 = new SimpleUserDTO(UUID.fromString("632e8400-e29b-41d4-a716-446655440000"),
            username, true, rolesDto2);
    DetailedUserDTO userDetailed = new DetailedUserDTO(username, "   ", true, rolesDto1);

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
    void whenUpdateUserUsernameTakenShouldReturnCorrectly() {
        when(userRepo.findByUsername(any(String.class))).thenReturn(user1);
        when(model.getAttribute("originalUsername")).thenReturn("differentUsername");
        UserServiceImpl service = new UserServiceImpl(userRepo, roleRepo);

        String result = service.updateUser(userDto1, model);

        assertEquals(userDto1.getUsername() + " är upptaget.", result);
    }

    @Test
    void whenUpdateUserNoRolesSelectedShouldReturnCorrectly() {
        when(userRepo.findByUsername(any(String.class))).thenReturn(null);
        UserServiceImpl service = new UserServiceImpl(userRepo, roleRepo);

        String result = service.updateUser(userDto2, model);

        assertEquals("Välj minst en av rollerna.", result);
    }

    @Test
    void whenUpdateUserSuccessShouldReturnCorrectly() {
        when(userRepo.findByUsername(any(String.class))).thenReturn(null);
        when(userRepo.findById(any(UUID.class))).thenReturn(user1);
        when(roleRepo.findByName(any(String.class))).thenReturn(role1);
        UserServiceImpl service = new UserServiceImpl(userRepo, roleRepo);

        String result = service.updateUser(userDto1, model);

        verify(roleRepo, times(2)).findByName(any(String.class));
        verify(userRepo, times(1)).save(any(User.class));
        verify(model, times(1)).addAttribute("originalUsername", userDto1.getUsername());
        assertEquals("Konto med användarnamn " + userDto1.getUsername() + " uppdaterades!", result);
    }

    @Test
    void addUser() {
    }

    @Test
    void whenAddUserUserPasswordBlankSpacesShouldReturnCorrectly() {
        UserServiceImpl service = new UserServiceImpl(userRepo, roleRepo);

        String result = service.addUser(userDetailed, model);

        assertEquals("Lösenordet får inte innehålla mellanslag.", result);
    }

    @Test
    void whenAddUserUsernameTakenShouldReturnCorrectly() {
        userDetailed.setPassword("password");
        when(userRepo.findByUsername(any(String.class))).thenReturn(user1);
        UserServiceImpl service = new UserServiceImpl(userRepo, roleRepo);

        String result = service.addUser(userDetailed, model);

        assertEquals(userDetailed.getUsername() + " är upptaget.", result);
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