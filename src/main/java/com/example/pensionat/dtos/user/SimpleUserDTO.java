package com.example.pensionat.dtos.user;

import com.example.pensionat.dtos.SimpleRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleUserDTO {

    private UUID id;
    private String username;
    private Boolean enabled;
    private Collection<SimpleRoleDTO> roles;
}
