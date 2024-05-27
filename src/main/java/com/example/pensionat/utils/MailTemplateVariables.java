package com.example.pensionat.utils;

import java.util.Arrays;
import java.util.List;

public class MailTemplateVariables {

    //TODO idk detta funkar ju inte va
    public static List<String> getBookingConfirmationVariables(){
        return Arrays.asList("Namn", "E-post", "Startdatum", "Slutdatum", "Totalsumma");
    }

}
