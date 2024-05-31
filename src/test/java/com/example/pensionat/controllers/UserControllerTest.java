package com.example.pensionat.controllers;

import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.dtos.user.DetailedUserDTO;
import com.example.pensionat.dtos.user.SimpleUserDTO;
import com.example.pensionat.models.Role;
import com.example.pensionat.models.User;
import com.example.pensionat.services.interfaces.RoleService;
import com.example.pensionat.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin", authorities={"Admin"})
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController controller;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

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

    private void mockAddToModel(Model model, Page<SimpleUserDTO> page) {
        model.addAttribute("allUsers", page.getContent());
        model.addAttribute("currentPage", page.getNumber() + 1);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        Page<SimpleUserDTO> mockPage = new PageImpl<>(List.of(userDto1));
        when(userService.getSimpleUserDtoByUsername(anyString())).thenReturn(userDto1);
        when(roleService.getAllRoles()).thenReturn((List<SimpleRoleDTO>) rolesDto1);
        when(userService.updateUser(any(SimpleUserDTO.class), any(Model.class))).thenReturn(
                                    "Konto med användarnamn " + userDto1.getUsername() + " uppdaterades!");
        when(userService.addUser(any(DetailedUserDTO.class), any(Model.class))).thenReturn(
                                    "Konto med användarnamn " + userDetailed.getUsername() + " skapades!");

        doAnswer(invocation -> {
            Model model = invocation.getArgument(1);
            mockAddToModel(model, mockPage);
            return null;
        }).when(userService).addToModel(anyInt(), any(Model.class));

        doAnswer(invocation -> {
            String search = invocation.getArgument(0);
            int currentPage = invocation.getArgument(1);
            Model model = invocation.getArgument(2);
            mockAddToModel(model, new PageImpl<>(List.of(userDto1), PageRequest.of(currentPage - 1, 5), 1));
            return null;
        }).when(userService).addToModelUserSearch(anyString(), anyInt(), any(Model.class));
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void handleUsers() throws Exception {
        this.mvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(view().name("handleUserAccounts"))
                .andExpect(model().attributeExists("allUsers", "currentPage", "totalItems", "totalPages"));

        verify(userService, times(1)).addToModel(eq(1), any(Model.class));
    }

    @Test
    public void handleByPage() throws Exception {
        int currentPage = 2;
        this.mvc.perform(get("/user/")
                .param("page", String.valueOf(currentPage)))
                .andExpect(status().isOk())
                .andExpect(view().name("handleUserAccounts"))
                .andExpect(model().attributeExists("allUsers", "currentPage", "totalItems", "totalPages"));

        verify(userService, times(1)).addToModel(eq(2), any(Model.class));
    }

    @Test
    public void deleteUserByUsername() throws Exception {
        this.mvc.perform(get("/user/{username}/remove", username))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/"));

        verify(userService, times(1)).deleteUserByUsername(eq(username));
    }

    @Test
    public void editUser() throws Exception {
        this.mvc.perform(get("/user/{username}/edit", username))
                .andExpect(status().isOk())
                .andExpect(view().name("updateUserAccount"))
                .andExpect(model().attribute("originalUsername", username))
                .andExpect(model().attribute("user", userDto1))
                .andExpect(model().attribute("selectableRoles", rolesDto1));

        verify(userService, times(1)).getSimpleUserDtoByUsername(eq(username));
        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    public void updateUser() throws Exception {
        this.mvc.perform(post("/user/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(userDto1.getId()))
                        .param("username", userDto1.getUsername())
                        .param("enabled", String.valueOf(userDto1.getEnabled()))
                        .param("roles", roleDto1.getName())
                        .param("roles", roleDto2.getName())
                        .param("originalUsername", username))
                .andExpect(status().isOk())
                .andExpect(view().name("updateUserAccount"))
                .andExpect(model().attribute("status", "Konto med användarnamn " + userDto1.getUsername()
                                                + " uppdaterades!"))
                .andExpect(model().attribute("originalUsername", username));

        verify(userService, times(1)).updateUser(eq(userDto1), any(Model.class));
    }

    @Test
    public void showCreateUserAccountForm() throws Exception {
        this.mvc.perform(get("/user/create"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("createUserAccount"))
                        .andExpect(model().attribute("selectableRoles", rolesDto1));

        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    public void addUser() throws Exception {
        this.mvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userDetailed.getUsername())
                        .param("password", userDetailed.getPassword())
                        .param("enabled", String.valueOf(userDetailed.getEnabled()))
                        .param("roles", roleDto1.getName())
                        .param("roles", roleDto2.getName()))
                .andExpect(status().isOk())
                .andExpect(view().name("createUserAccount"))
                .andExpect(model().attribute("status", "Konto med användarnamn " + userDetailed.getUsername()
                        + " skapades!"));

        verify(userService, times(1)).addUser(eq(userDetailed), any(Model.class));
    }

    @Test
    public void userSearch() throws Exception {
        this.mvc.perform(get("/user/")
                .param("search", username))
                .andExpect(status().isOk())
                .andExpect(view().name("handleUserAccounts"))
                .andExpect(model().attributeExists("allUsers", "currentPage", "totalItems", "totalPages"));

        verify(userService, times(1)).addToModelUserSearch(eq(username), eq(1), any(Model.class));
    }

    @Test
    public void userSearchByPage() throws Exception {
        int currentPage = 2;
        this.mvc.perform(get("/user/")
                        .param("search", username)
                        .param("page", String.valueOf(currentPage)))
                .andExpect(status().isOk())
                .andExpect(view().name("handleUserAccounts"))
                .andExpect(model().attributeExists("allUsers", "currentPage", "totalItems", "totalPages"));

        verify(userService, times(1)).addToModelUserSearch(eq(username), eq(2), any(Model.class));
    }
}