package com.example.pensionat.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailTemplate {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @Column(columnDefinition = "TEXT")
    private String body;
}
