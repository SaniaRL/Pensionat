package com.example.pensionat.controllers;

import com.example.pensionat.dtos.SimpleUserDTO;
import com.example.pensionat.models.User;
import com.example.pensionat.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final JavaMailSender emailSender;
    private final UserService userService;

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

        //Generera lösen

        //Sätt lösen som nytt lösen ? Det känns inte optimalt. Borde finnas gammalt lösen också ju?
        //TODO kolla om lösen verkligen ska ersättas på detta vis
        int passWordLength = 4;
        String newPassword = generateOTP(passWordLength);

        //TODO ändra lösen idk:
        userService.updatePassword(mail, newPassword);

        //TODO Hämta mall och shit men asså
        String subject = "Hej";
        String message = "New password: " + newPassword;

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        //TODO använd mail som är param men inte än
        mailMessage.setFrom("dominique.wiegand@ethereal.email");
        mailMessage.setTo("dominique.wiegand@ethereal.email");
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailSender.send(mailMessage);

        //Uppdatera GUI (Kanske skulle förvara texten här bak idk)
        model.addAttribute("mailSent", true);

        return "login";
    }

    //TODO inte en service - en util? Var ska denna metod bo?

    static String generateOTP(int length)
    {
        String str = "abcdefghijklmnopqrstuvwxyzABCD"
                +"EFGHIJKLMNOPQRSTUVWXYZ0123456789-";
        int n = str.length();

        StringBuilder OTP= new StringBuilder();

        for (int i = 1; i <= length; i++)
            OTP.append(str.charAt((int) ((Math.random() * 10) % n)));

        return(OTP.toString());
    }

//    @PutMapping("")
}
