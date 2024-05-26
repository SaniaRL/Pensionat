package com.example.pensionat.repositories;

import com.example.pensionat.models.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailTemplateRepo extends JpaRepository<MailTemplate, Long> {
    public MailTemplate findByName(String name);
}
