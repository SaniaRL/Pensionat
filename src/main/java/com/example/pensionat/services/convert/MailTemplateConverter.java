package com.example.pensionat.services.convert;

import com.example.pensionat.dtos.MailTemplateDTO;
import com.example.pensionat.models.MailTemplate;

public class MailTemplateConverter {

    public static MailTemplate mailTemplateDTOtoMailTemplate(MailTemplateDTO m){
        return MailTemplate.builder().id(m.getId()).name(m.getName()).body(m.getBody()).build();
    }

    public static MailTemplateDTO mailTemplateToMailTemplateDTO(MailTemplate m){
        return MailTemplateDTO.builder().id(m.getId()).name(m.getName()).body(m.getBody()).build();
    }
}
