package com.example.pensionat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResetPasswordMailVariablesDTO {
    private String username;
    private String link;
    private String timeLimit;
}
