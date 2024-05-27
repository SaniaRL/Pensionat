package com.example.pensionat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MailTemplateDTO {
    private Long id;
    private String name;
    private String subject;
    private String body;
}
