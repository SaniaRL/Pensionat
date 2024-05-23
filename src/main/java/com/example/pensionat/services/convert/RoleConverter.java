package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.models.Role;

public class RoleConverter {
    public static SimpleRoleDTO roleToSimpleRoleDTO(Role role) {
        return SimpleRoleDTO.builder()
                .name(role.getName())
                .build();
    }
}
