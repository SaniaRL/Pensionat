package com.example.pensionat.dtos;

import com.example.pensionat.models.Role;
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
    private Collection<Role> roles;
}
