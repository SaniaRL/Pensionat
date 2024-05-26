package com.example.pensionat.controllers;

import com.example.pensionat.dtos.BookingData;
import com.example.pensionat.dtos.MailTemplateDTO;
import com.example.pensionat.dtos.MailText;
import com.example.pensionat.dtos.OrderLineDTO;
import com.example.pensionat.models.MailTemplate;
import com.example.pensionat.repositories.MailTemplateRepo;
import com.example.pensionat.services.interfaces.MailTemplateService;
import com.example.pensionat.utils.MailTemplateVariables;
import lombok.AllArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
        //TODO Hämta alla variabler som kan användas idk what I am doing yeah freestyle
        List<String> variables = MailTemplateVariables.getBookingConfirmationVariables();
        model.addAttribute("variables", variables);
        //TODO Hämta alla mallar till en liten lista och lägg in i model
        List<MailTemplateDTO> templateList = mailTemplateService.getAllTemplates();
        model.addAttribute("templateList", templateList);
        //Lägg till nån text ändå kanske
        String text = "Skriv ny mall här eller välj befintlig mall att uppdatera";
        model.addAttribute("text", text);
        model.addAttribute("name", "Ämne");

        //TODO fix idk -1 yes
        model.addAttribute("id", null);
        return "mail/edit/edit";
    }

    @PostMapping("/postTemplate")
    public String postTemplate(Model model, @ModelAttribute MailTemplateDTO mailTemplateDTO) {
        System.out.println("Post template");
        System.out.println("id = " + mailTemplateDTO.getId());
        System.out.println("Mail head = " + mailTemplateDTO.getName());
        System.out.println("Mail body = " + mailTemplateDTO.getBody());

        List<String> variables = MailTemplateVariables.getBookingConfirmationVariables();
        model.addAttribute("variables", variables);
        List<MailTemplateDTO> templateList = mailTemplateService.getAllTemplates();
        model.addAttribute("templateList", templateList);

        MailTemplateDTO savedMailTemplate = mailTemplateService.save(mailTemplateDTO);

        model.addAttribute("id", savedMailTemplate.getId());
        model.addAttribute("name", savedMailTemplate.getName());
        model.addAttribute("text", savedMailTemplate.getBody());

        model.addAttribute("templateSaved", true);

        return "mail/edit/edit";
    }

/*
    @RequestMapping("/")
    public String getMailTemplate() {
        return
    }

 */
}
