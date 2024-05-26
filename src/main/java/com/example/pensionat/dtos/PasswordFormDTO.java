package com.example.pensionat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordFormDTO {
    private String token;
    private String newPassword;
    private String confirmPassword;
}
