package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.models.Role;
import com.example.pensionat.repositories.RoleRepo;
import com.example.pensionat.services.convert.CustomerConverter;
import com.example.pensionat.services.convert.RoleConverter;
import com.example.pensionat.services.interfaces.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<SimpleRoleDTO> getAllRoles() {
        return roleRepo.findAll().stream().map(RoleConverter::roleToSimpleRoleDTO).toList();
    }
}
