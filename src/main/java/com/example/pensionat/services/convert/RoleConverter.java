package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.models.Role;
import com.example.pensionat.models.User;
import com.example.pensionat.repositories.RoleRepo;

import java.util.Collection;

public class RoleConverter {

    public static SimpleRoleDTO roleToSimpleRoleDTO(Role role) {
        return SimpleRoleDTO.builder()
                .name(role.getName())
                .build();
    }
    /*
    public static Role simpleRoleDtoToRole(SimpleRoleDTO roleDTO, Collection<User> users) {
        return Role.builder()
                .name(role.getName())
                .build();
    }*/
}
