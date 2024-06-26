package com.example.pensionat.controllers;

import com.example.pensionat.dtos.MailTemplateDTO;
import com.example.pensionat.services.interfaces.MailTemplateService;
import com.example.pensionat.services.providers.EmailConfigProvider;
import com.example.pensionat.utils.MailTemplateVariables;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/mailTemplate")
public class MailTemplateController {

    private final MailTemplateService mailTemplateService;
    private final EmailConfigProvider emailConfigProvider;

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
        String variable = emailConfigProvider.getMailVerification();
        List<String> variables = MailTemplateVariables.getVariables(variable);
        model.addAttribute("variables", variables);
        List<MailTemplateDTO> templateList = mailTemplateService.getAllTemplates();
        model.addAttribute("templateList", templateList);

        MailTemplateDTO m = mailTemplateService.getMailTemplateByName(variable);
        if(m != null) {
            model.addAttribute("id", m.getId());
            model.addAttribute("name", m.getName());
            model.addAttribute("subject", m.getSubject());
            model.addAttribute("body", m.getBody());
        }
        return "mail/edit/edit";
    }

    @PostMapping("/postTemplate")
    public String postTemplate(Model model, @ModelAttribute MailTemplateDTO mailTemplateDTO) {
        System.out.println("Post template");
        System.out.println("id = " + mailTemplateDTO.getId());
        System.out.println("Mail head = " + mailTemplateDTO.getName());
        System.out.println("Mail body = " + mailTemplateDTO.getBody());

        String variable = "ResetPassword";
        List<String> variables = MailTemplateVariables.getVariables(variable);
        model.addAttribute("variables", variables);
        List<MailTemplateDTO> templateList = mailTemplateService.getAllTemplates();
        model.addAttribute("templateList", templateList);

        MailTemplateDTO savedMailTemplate = mailTemplateService.save(mailTemplateDTO);

        model.addAttribute("id", savedMailTemplate.getId());
        model.addAttribute("name", savedMailTemplate.getName());
        model.addAttribute("subject", savedMailTemplate.getSubject());
        model.addAttribute("body", savedMailTemplate.getBody());

        model.addAttribute("templateSaved", true);

        return "mail/edit/edit";
    }

    @GetMapping("/edit/{id}")
    public String editTemplateById(Model model, @PathVariable Long id) {
        MailTemplateDTO selectedTemplate = mailTemplateService.getMailTemplateById(id);

        List<String> variables = MailTemplateVariables.getVariables(selectedTemplate.getName());
        model.addAttribute("variables", variables);

        List<MailTemplateDTO> templateList = mailTemplateService.getAllTemplates();
        model.addAttribute("templateList", templateList);

        model.addAttribute("id", selectedTemplate.getId());
        model.addAttribute("name", selectedTemplate.getName());
        model.addAttribute("subject", selectedTemplate.getSubject());
        model.addAttribute("body", selectedTemplate.getBody());

        return "mail/edit/edit";
    }

}
