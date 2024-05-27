package com.example.pensionat.services.providers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Service
public class EmailConfigProvider {

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.resetlink}")
    private String mailResetlink;

}
