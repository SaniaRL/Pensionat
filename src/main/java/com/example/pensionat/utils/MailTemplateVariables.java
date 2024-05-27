package com.example.pensionat.utils;

import java.util.Arrays;
import java.util.List;

public class MailTemplateVariables {

    //TODO idk detta funkar ju inte va

    public static List<String> getVariables(String variable) {
        return switch (variable) {
            case "Bokningsbekräftelse" -> getBookingConfirmationVariables();
            case "ÅterställLösenord" -> getResetPasswordVariables();
            default -> getOptionVariables();
        };
    }
    private static List<String> getBookingConfirmationVariables(){
        return Arrays.asList("Namn", "E-post", "Startdatum", "Slutdatum", "Totalsumma");
    }
    private static List<String> getResetPasswordVariables(){
        return Arrays.asList("Användarnamn", "Länk", "Tidsbegränsning");
    }
    private static List<String> getOptionVariables(){
        return Arrays.asList("Bekräfta Bokning", "Återställ Lösenord");
    }
}
