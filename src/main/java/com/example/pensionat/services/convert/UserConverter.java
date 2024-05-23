package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.models.Role;
import com.example.pensionat.models.User;

import java.util.ArrayList;
import java.util.Collection;

public class UserConverter {

    public static SimpleUserDTO userToSimpleUserDTO(User user) {
        Collection<SimpleRoleDTO> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            System.out.println("ROLE: " + role.getName());
            roles.add(RoleConverter.roleToSimpleRoleDTO(role));
        }
        return SimpleUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .enabled(user.getEnabled())
                .roles(roles)
                .build();
    }

    /*public static User simpleUserDTOtoUser(SimpleUserDTO userDTO, User user ) {
        Collection<Role> roles = null;
        for (SimpleRoleDTO role : userDTO.getRoles()) {
            roles.add(RoleConverter.simpleRoleDtoToRole(role));
        }
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(user.getPassword())
                .enabled(userDTO.getEnabled())
                .roles(roles)
                .build();
    }*/
}