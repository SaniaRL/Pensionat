package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.DetailedUserDTO;
import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.models.Role;
import com.example.pensionat.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserConverter {

    public static SimpleUserDTO userToSimpleUserDTO(User user) {
        System.out.println("User " + user.getUsername());
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

    public static User simpleUserDtoToUser(SimpleUserDTO userDTO, User user, List<Role> roles) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(user.getPassword())
                .enabled(userDTO.getEnabled())
                .roles(roles)
                .build();
    }

    public static User detailedUserDtoToUser(DetailedUserDTO userDTO, List<Role> roles) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .enabled(userDTO.getEnabled())
                .roles(roles)
                .build();
    }
}