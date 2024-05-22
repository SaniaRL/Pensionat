package com.example.pensionat.services.providers;

import com.example.pensionat.models.Role;
import com.example.pensionat.models.User;
import com.example.pensionat.repositories.RoleRepo;
import com.example.pensionat.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoleAndUserDataSeeder {

    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    public void Seed(){
        if (roleRepo.findByName("Admin") == null) {
            addRole("Admin");
        }
        if (roleRepo.findByName("Receptionist") == null) {
            addRole("Receptionist");
        }
        if(userRepo.getUserByUsername("admin@mail.com") == null){
            addUser("admin@mail.com","Admin");
        }
        if(userRepo.getUserByUsername("repan@mail.com") == null){
            addUser("repan@mail.com","Receptionist");
        }/*
        if(userRepo.getUserByUsername("eddie@mail.com") == null){
            addUser("eddie@mail.com","Admin");
        }
        if(userRepo.getUserByUsername("sania@mail.com") == null){
            addUser("sania@mail.com","Admin");
        }
        if(userRepo.getUserByUsername("simon@mail.com") == null){
            addUser("simon@mail.com","Admin");
        }
        if(userRepo.getUserByUsername("basse@mail.com") == null){
            addUser("basse@mail.com","Admin");
        }*/
    }

    private void addUser(String mail, String group) {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName(group));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("Basse123");
        User user = User.builder().enabled(true).password(hash).username(mail).roles(roles).build();
        userRepo.save(user);
    }

    private void addRole(String name) {
        Role role = new Role();
        roleRepo.save(Role.builder().name(name).build());
    }
}