package com.example.pensionat.controllers;

import com.example.pensionat.dtos.MailTemplateDTO;
import com.example.pensionat.dtos.PasswordFormDTO;
import com.example.pensionat.dtos.ResetPasswordMailVariablesDTO;
import com.example.pensionat.models.User;
import com.example.pensionat.services.interfaces.MailTemplateService;
import com.example.pensionat.services.interfaces.UserService;
import com.example.pensionat.services.providers.EmailConfigProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final JavaMailSender emailSender;
    private final UserService userService;
    private final EmailConfigProvider emailConfigProvider;
    private final MailTemplateService mailTemplateService;

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

    @PostMapping("/forgotPassword-24")
    public String forgotPassword24(@RequestParam(value = "mail", required = false) String mail, Model model) {
        String resetToken = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(mail, resetToken);
        String resetLink = emailConfigProvider.getMailResetlink() + resetToken;

        ResetPasswordMailVariablesDTO rp = ResetPasswordMailVariablesDTO.builder()
                .username(mail)
                .link(resetLink)
                .timeLimit("24")
                .build();

        //TODO hårdkodat
        String name = emailConfigProvider.getMailResetPassword();
        MailTemplateDTO mailTemplateDTO = mailTemplateService.getMailTemplateByName(name);

        String subject = mailTemplateDTO.getSubject();

        String message = getTheRightText(mailTemplateDTO.getBody(), rp);

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message, true);
            helper.setTo(mail);
            helper.setSubject(subject);
            helper.setFrom(emailConfigProvider.getMailUsername());
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            model.addAttribute("mailError", true);
            return "login";
        }

        model.addAttribute("mailSent", true);
        return "login";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam(value = "token") String token, Model model) {

        User user = userService.getUserByResetToken(token);
        if (user == null || user.getResetTokenExpire().isBefore(LocalDateTime.now())) {
            model.addAttribute("tokenError", true);
            return "login";
        }

        model.addAttribute("token", token);
        return "resetPassword";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute PasswordFormDTO passwordFormDTO, Model model) {
        User user = userService.getUserByResetToken(passwordFormDTO.getToken());
        if (user != null && passwordFormDTO.getNewPassword().equals(passwordFormDTO.getConfirmPassword())) {
            userService.updatePassword(user.getUsername(), passwordFormDTO.getNewPassword());
            userService.removeResetToken(user);
            model.addAttribute("passwordUpdated", true);
            return "login";
        }

        model.addAttribute("error", true);
        return "resetPassword";
    }

    private String getTheRightText(String text, ResetPasswordMailVariablesDTO bd) {

        return text.replace("!!!!Användarnamn!!!!", bd.getUsername())
                .replace("!!!!Länk!!!!", bd.getLink())
                .replace("!!!!Tidsbegränsning!!!!", bd.getTimeLimit());
    }

}
