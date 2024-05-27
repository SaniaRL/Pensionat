package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.DetailedUserDTO;
import com.example.pensionat.dtos.SimpleRoleDTO;
import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.models.Role;
import com.example.pensionat.models.User;
import com.example.pensionat.repositories.RoleRepo;
import com.example.pensionat.repositories.UserRepo;
import com.example.pensionat.services.convert.UserConverter;
import com.example.pensionat.services.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo rolerepo) {
        this.userRepo = userRepo;
        this.roleRepo = rolerepo;
    }

    @Override
    public void addToModel(int currentPage, Model model) {
        Page<SimpleUserDTO> u = getAllUsersPage(currentPage);
        model.addAttribute("allUsers", u.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", u.getTotalElements());
        model.addAttribute("totalPages", u.getTotalPages());
    }

    @Override
    public void addToModelUserSearch(String search, int currentPage, Model model) {
        Page<SimpleUserDTO> u = getUsersBySearch(search, currentPage);
        model.addAttribute("allUsers", u.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", u.getTotalElements());
        model.addAttribute("totalPages", u.getTotalPages());
        model.addAttribute("search", search);
    }

    @Override
    public Page<SimpleUserDTO> getAllUsersPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5);
        Page<User> page = userRepo.findAll(pageable);
        return page.map(UserConverter::userToSimpleUserDTO);
    }

    @Override
    public SimpleUserDTO getSimpleUserDtoByUsername(String username) {
        User user = userRepo.findByUsername(username);
        return UserConverter.userToSimpleUserDTO(user);
    }

    @Override
    public Page<SimpleUserDTO> getUsersBySearch(String search, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5);
        Page<User> page = userRepo.findByUsernameContainsOrRolesNameContains(search, search, pageable);
        System.out.println("SÖKORD2: " + search);
        System.out.println("PAGE: " + page.getTotalElements());
        return page.map(UserConverter::userToSimpleUserDTO);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepo.deleteByUsername(username);
    }

    @Override
    public String updateUser(SimpleUserDTO userDTO, Model model) {
        userDTO.setUsername(userDTO.getUsername().trim());

        if (userDTO.getUsername().isEmpty()) {
            return "Användarnamnet får inte vara enbart mellanslag.";
        }
        if (userRepo.findByUsername(userDTO.getUsername()) != null &&
                !Objects.equals(userRepo.findByUsername(
                        userDTO.getUsername()).getUsername(), model.getAttribute("originalUsername"))) {
            return "Användarnamnet " + userDTO.getUsername() + " är upptaget.";
        }
        System.out.println("ROLES: " + userDTO.getRoles());
        if (userDTO.getRoles().isEmpty()) {
            return "Välj minst en av rollerna.";
        }
        User user = userRepo.findById(userDTO.getId());
        List<Role> roles = new ArrayList<>();
        for (SimpleRoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepo.findByName(roleDTO.getName());
            roles.add(role);
        }
        userRepo.save(UserConverter.simpleUserDtoToUser(userDTO, user, roles));
        model.addAttribute("originalUsername", userDTO.getUsername());

        return "Konto med användarnamn " + userDTO.getUsername() + " uppdaterades!";
    }

    @Override
    public String addUser(DetailedUserDTO userDTO, Model model) {
        model.addAttribute("username", userDTO.getUsername());
        model.addAttribute("password", userDTO.getPassword());

        userDTO.setUsername(userDTO.getUsername().trim());
        if (userDTO.getUsername().isEmpty()) {
            return "Användarnamnet får inte vara enbart mellanslag.";
        }
        if (userDTO.getPassword().contains(" ")) {
            return "Lösenordet får inte innehålla mellanslag.";
        }
        if (userRepo.findByUsername(userDTO.getUsername()) != null) {
            return "Användarnamnet " + userDTO.getUsername() + " är upptaget.";
        }
        if (userDTO.getRoles() == null) {
            return "Välj minst en av rollerna.";
        }
        List<Role> roles = new ArrayList<>();
        for (SimpleRoleDTO roleDTO : userDTO.getRoles()) {
            Role role = roleRepo.findByName(roleDTO.getName());
            roles.add(role);
        }
        String hashPassword = getHashPassword(userDTO.getPassword());
        userDTO.setPassword(hashPassword);

        userRepo.save(UserConverter.detailedUserDtoToUser(userDTO, roles));
        model.addAttribute("username", null);
        model.addAttribute("password", null);

        return "Konto med användarnamn " + userDTO.getUsername() + " skapades!";
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            String hash = getHashPassword(newPassword);
            user.setPassword(hash);
            userRepo.save(user);
        }
    }

    private String getHashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    @Override
    public void createPasswordResetTokenForUser(String email, String token) {
        User user = userRepo.findByUsername(email);
        user.setResetToken(token);
        user.setResetTokenExpire(LocalDateTime.now().plusHours(24)); // ändra till hours
        userRepo.save(user);
    }

    @Override
    public User getUserByResetToken(String token) {
        return userRepo.findByResetToken(token);
    }

    @Override
    public void removeResetToken(User user) {
        user.setResetToken(null);
        user.setResetTokenExpire(null);
        userRepo.save(user);
    }

}