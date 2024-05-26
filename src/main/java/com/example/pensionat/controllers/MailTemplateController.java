package com.example.pensionat.controllers;

import com.example.pensionat.models.MailTemplate;
import com.example.pensionat.repositories.MailTemplateRepo;
import com.example.pensionat.services.interfaces.MailTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.HTML;

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
        model.addAttribute("text", mailText);
        return "mail/edit/confirmation";
    }

}
