package com.example.pensionat.controllers;

import com.example.pensionat.dtos.PasswordFormDTO;
import com.example.pensionat.models.User;
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
import java.time.format.DateTimeFormatter;
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
    public String forgotPassword24(@RequestParam(value="mail", required = false) String mail, Model model) {
        String resetToken = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(mail, resetToken);
        String resetLink = emailConfigProvider.getMailResetlink() + resetToken;

        String subject = "Återställ lösenord";
        String message = "<div>Följ denna <a href=" + resetLink + ">länk</a>!</div>";

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
        if (user == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
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
            userService.invalidateResetToken(user);
            model.addAttribute("passwordUpdated", true);
            return "login";
        }

        model.addAttribute("error", true);
        return "resetPassword";
    }

    //TODO inte en service - en util? Var ska denna metod bo?

    private String generateResetPasswordToken(String username) {
        String token = UUID.randomUUID().toString();
        LocalDateTime timestamp = LocalDateTime.now();
        return token + "%7C" + username + "%7C" + timestamp;
    }

    private String[] splitToken(String token) {
        return token.split("\\|");
    }
    private String getMail(String token) {
        String[] tokenArray = splitToken(token);
        return tokenArray[1];
    }
    private LocalDateTime getDateTimeLimit24h(String token) {
        String[] tokenArray = splitToken(token);

        return LocalDateTime.parse(tokenArray[2], DateTimeFormatter.ISO_LOCAL_DATE_TIME).plusHours(24);
    }

    /*
    private String generateOTP(int length)
    {
        String str = "abcdefghijklmnopqrstuvwxyzABCD"
                +"EFGHIJKLMNOPQRSTUVWXYZ0123456789-";
        int n = str.length();

        StringBuilder OTP= new StringBuilder();

        for (int i = 1; i <= length; i++)
            OTP.append(str.charAt((int) ((Math.random() * 10) % n)));

        return(OTP.toString());
    }

     */

}
