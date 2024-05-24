package com.example.pensionat.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.mail.javamail.JavaMailSender;
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

    @PostMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("mailSent", true);
        //TODO Hämta mall och shit men asså
        String subject = "Hello";
        String message = "Poop";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("dominique.wiegand@ethereal.email");
//        mailMessage.setTo(email);
        mailMessage.setTo("mireya.gorczany49@ethereal.email");
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailSender.send(mailMessage);
        return "login";
    }

}
