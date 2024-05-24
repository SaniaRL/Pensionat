package com.example.pensionat.controllers;

import com.example.pensionat.models.MailTemplate;
import com.example.pensionat.repositories.MailTemplateRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.HTML;

@RestController
@RequestMapping("/mailTemplate")
public class MailTemplateController {
    private final MailTemplateRepo mailTemplateRepo;

    public MailTemplateController(MailTemplateRepo mailTemplateRepo) {
        this.mailTemplateRepo = mailTemplateRepo;
    }

    @PostMapping("add")
    public void add(@RequestBody String mailTemplate) {
        System.out.println(mailTemplate);
        MailTemplate mailTemplateModel = new MailTemplate();
        mailTemplateModel.setName("Template");
        mailTemplateModel.setBody(mailTemplate);
        mailTemplateRepo.save(mailTemplateModel);
    }
}
