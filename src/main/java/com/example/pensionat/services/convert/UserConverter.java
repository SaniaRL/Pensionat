package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.models.User;

public class UserConverter {

    public static SimpleUserDTO userToSimpleUserDTO(User user) {
        return SimpleUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .enabled(user.getEnabled())
                .roles(user.getRoles())
                .build();
    }

    public static User simpleUserDTOtoUser(SimpleUserDTO userDTO, User user ) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(user.getPassword())
                .enabled(userDTO.getEnabled())
                .roles(userDTO.getRoles())
                .build();
    }
}