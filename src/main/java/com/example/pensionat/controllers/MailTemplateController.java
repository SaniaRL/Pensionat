package com.example.pensionat.controllers;

import com.example.pensionat.dtos.MailTemplateDTO;
import com.example.pensionat.dtos.MailText;
import com.example.pensionat.models.MailTemplate;
import com.example.pensionat.repositories.MailTemplateRepo;
import com.example.pensionat.services.interfaces.MailTemplateService;
import lombok.AllArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.HTML;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/mailTemplate")
public class MailTemplateController {

    private final MailTemplateService mailTemplateService;

    @GetMapping("/confirmation")
    public String confirmation(Model model) {
        model.addAttribute("text", "testing");
        return "mail/edit/confirmation";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "mail/edit/forgotPassword";
    }

    @PostMapping("/updateTemplate")
    public String updateTemplate(Model model, @RequestBody String mailText) {
        System.out.println((mailText));
        model.addAttribute("text", (mailText));
        model.addAttribute("content", (mailText));
        return "mail/edit/confirmation";
    }

    @RequestMapping("/edit")
    public String editTemplate(Model model) {
        //TODO Hämta alla variabler som kan användas
        //TODO Hämta alla mallar till en liten lista och lägg in i model
        List<MailTemplateDTO> templateList = mailTemplateService.getAllTemplates();
        model.addAttribute("templateList", templateList);
        return "mail/edit/edit";
    }


/*
    @RequestMapping("/")
    public String getMailTemplate() {
        return
    }

 */
}
