package com.example.pensionat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private JavaMailSender emailSender;

    @RequestMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "newPassword", required = false) String newPassword) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        if (newPassword != null) {
            model.addAttribute("newPassword", true);
        }
        return "login";
    }

    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam(value = "mail", required = false) String mail, Model model) {
        model.addAttribute("mailSent", true);
        System.out.println(mail);
        //TODO Hämta mall och shit men asså
        String subject = "Hej";
        String message = "Tjabba, tjena, hallå";

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("dominique.wiegand@ethereal.email");
        mailMessage.setTo("dominique.wiegand@ethereal.email");
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailSender.send(mailMessage);

        return "login";
    }
}
