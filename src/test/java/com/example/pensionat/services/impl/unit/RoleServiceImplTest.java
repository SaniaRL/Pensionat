package com.example.pensionat.services.impl.unit;

import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.models.Role;
import com.example.pensionat.models.User;
import com.example.pensionat.repositories.RoleRepo;
import com.example.pensionat.services.convert.RoleConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoleServiceImplTest {

    @Mock
    private RoleRepo roleRepo;

    @Test
    void getAllRolesShouldGetRolesFromDB() {
        Collection<User> userTestCollection = new ArrayList<>();
        UUID randomUUID1 = UUID.randomUUID();
        UUID randomUUID2 = UUID.randomUUID();
        Role r1 = new Role(randomUUID1, "AdminTest", userTestCollection);
        Role r2 = new Role(randomUUID2, "RepanTest", userTestCollection);

        when(roleRepo.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<SimpleRoleDTO> result = roleRepo.findAll().stream().map(RoleConverter::roleToSimpleRoleDTO).toList();

        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "AdminTest");
        assertEquals(result.get(1).getName(), "RepanTest");
        verify(roleRepo, times(1)).findAll();
    }
}