package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.MailTemplateDTO;
import com.example.pensionat.models.MailTemplate;
import com.example.pensionat.repositories.MailTemplateRepo;
import com.example.pensionat.services.interfaces.MailTemplateService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MailTemplateServiceImpl implements MailTemplateService {
    private final MailTemplateRepo mailTemplateRepo;

    MailTemplateServiceImpl(MailTemplateRepo mailTemplateRepo) {
        this.mailTemplateRepo = mailTemplateRepo;
    }

    @Override
    public void updateTemplate(String content, String name) {
        MailTemplate mailTemplate = mailTemplateRepo.findByName(name);
        mailTemplate.setBody(content);
        mailTemplateRepo.save(mailTemplate);
    }

    @Override
    public List<MailTemplateDTO> getAllTemplates() {
        return mailTemplateRepo.findAll().stream()
                .map(m -> MailTemplateDTO.builder().id(m.getId()).name(m.getName()).body(m.getBody()).build()).toList();
    }
}
