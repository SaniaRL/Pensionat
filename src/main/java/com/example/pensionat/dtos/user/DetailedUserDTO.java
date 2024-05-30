package com.example.pensionat.dtos.user;

import com.example.pensionat.dtos.SimpleRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedUserDTO {

    private String username;
    private String password;
    private Boolean enabled;
    private Collection<SimpleRoleDTO> roles;
}
