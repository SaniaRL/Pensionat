package com.example.pensionat.controllers;

import com.example.pensionat.dtos.PasswordFormDTO;
import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.models.User;
import com.example.pensionat.services.interfaces.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final JavaMailSender emailSender;
    private final UserService userService;

    //TODO hämta från properties
    /*
    @Value("${spring.mail.username}")
    */
    private final String fromEmail = "dominique.wiegand@ethereal.email";


    @RequestMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "newPassword", required = false) String newPassword){
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

    /*
    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam(value="mail", required = false) String mail, Model model) {

        //Kolla om user ens finns
        //TODO Göra param obligatorisk men jag är lat nu och vill kunna klicka loss
        if(mail == null) {
            mail = "sania@mail.com";
        }

        SimpleUserDTO user = userService.getSimpleUserDtoByUsername(mail);
        if(user == null){
            model.addAttribute("userNotFound", true);
            return "login";
        }

        //Sätt lösen som nytt lösen ? Det känns inte optimalt. Borde finnas gammalt lösen också ju?
        //TODO kolla om lösen verkligen ska ersättas på detta vis
        int passWordLength = 4;
        String newPassword = generateOTP(passWordLength);

        //TODO ändra lösen idk:
        userService.updatePassword(mail, newPassword);

        //TODO Hämta mall och shit men asså
        String subject = "New Password";
        String message = "New password: " + newPassword;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(mail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailSender.send(mailMessage);

        //Uppdatera GUI (Kanske skulle förvara texten här bak och kunna ha flera text idk)
        model.addAttribute("mailSent", true);

        return "login";
    }

     */


    @PostMapping("/forgotPassword-24")
    public String forgotPassword24(@RequestParam(value="mail", required = false) String mail, Model model) {

        String resetToken = generateResetPasswordToken(mail);
        String resetLink = "localhost:8080/resetPassword?token=" + resetToken;

        //TODO Hämta mall och shit men asså
        String subject = "Återställ lösenord";
        String message = "<div>Följ denna <a href=" + resetLink + ">länk</a> !</div>";

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message, true);
            helper.setTo(mail);
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            model.addAttribute("mailError", true);
            return "login";
        }

        //Uppdatera GUI (Kanske skulle förvara texten här bak och kunna ha flera text idk)
        model.addAttribute("mailSent", true);

        return "login";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam(value = "token") String token, Model model) {

        String[] tokenArray = token.split("\\|");
        if (tokenArray.length != 3) {
            //TODO fixa detta chill idk
            System.out.println("error token != 3");
            System.out.println(tokenArray.length);
            System.out.println(token);
            model.addAttribute("tokenError", true);
            return "login";
        }

        //TODO Måste kolla av allt annat också - eller tid iaf
        LocalDateTime timeLimit = getDateTimeLimit24h(token);

        if(timeLimit.isBefore(LocalDateTime.now())) {
            model.addAttribute("tokenError", true);
            System.out.println("TimeLimit passed");
            return "login";
        }

        model.addAttribute("token", token);
        System.out.println("Token added");

        return "resetPassword";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@ModelAttribute PasswordFormDTO passwordFormDTO, Model model) {

        System.out.println("/updatePassword");
        String token = passwordFormDTO.getToken();
        String newPassword = passwordFormDTO.getNewPassword();
        String confirmPassword = passwordFormDTO.getConfirmPassword();
        String mail = getMail(token);
        System.out.println("mail: " + mail);

        if(newPassword.equals(confirmPassword)){
            userService.updatePassword(mail, newPassword);
            System.out.println("new pass == conf pass");
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
