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
        if(userRepo.findByUsername("admin@mail.com") == null){
            addUser("admin@mail.com","Admin");
        }
        if(userRepo.findByUsername("repan@mail.com") == null){
            addUser("repan@mail.com","Receptionist");
        }
        if(userRepo.findByUsername("eddie@mail.com") == null){
            addUser("eddie@mail.com","Admin");
        }
        if(userRepo.findByUsername("sania@mail.com") == null){
            addUser("sania@mail.com","Admin");
        }
        if(userRepo.findByUsername("simon@mail.com") == null){
            addUser("simon@mail.com","Admin");
        }
        if(userRepo.findByUsername("basse@mail.com") == null){
            addUser("basse@mail.com","Admin");
        }
    }

    private void addUser(String mail, String group) {
        ArrayList<Role> roles = new ArrayList<>();
        Role role = roleRepo.findByName(group);
        System.out.println("Role id: " + role.getId() + " Role name: " + role.getName());
        roles.add(role);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("Basse123");
        User user = User.builder().enabled(true).password(hash).username(mail).roles(roles).build();
        System.out.println("innan repo");
        userRepo.save(user);
        System.out.println("efter repo");
    }

    private void addRole(String name) {
        roleRepo.save(Role.builder().name(name).build());
    }
}