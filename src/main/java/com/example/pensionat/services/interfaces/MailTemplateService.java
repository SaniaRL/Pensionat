package com.example.pensionat.services.interfaces;

import com.example.pensionat.dtos.MailTemplateDTO;
import com.example.pensionat.models.MailTemplate;

import java.util.List;

public interface MailTemplateService {
    void updateTemplate(String content, String name);

    List<MailTemplateDTO> getAllTemplates();

    }
