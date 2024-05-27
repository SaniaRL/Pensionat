package com.example.pensionat.services.providers;

import com.example.pensionat.models.MailTemplate;
import com.example.pensionat.repositories.MailTemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailTemplateSeeder {

    @Autowired
    MailTemplateRepo mailTemplateRepo;

    public void Seed() {
        if (mailTemplateRepo.findByName("Bokningsbekräftelse") == null) {
            addMailTemplate(MailTemplate.builder()
                    .name("Bokningsbekräftelse")
                    .subject("Bed&Basse Bokningsbekräftelse")
                    .body("""
                            <html><body><style>
                            h1 {
                            font-size: 36px;
                            color: #fff;
                            }
                            .header {
                            background-color: #4f4b6a;
                            }
                            .icon {
                            display: block;
                            margin-top: 20px;
                            max-width: 100%;
                            height: auto;
                            }
                            </style><div class="header"><h1>Hej, !!!!Namn!!!!!
                            </h1></div>
                            <br><br>
                            Din epost är <b>nice</b>, jag diggar <i>!!!!E-post!!!!</i>
                            <br><br>
                            Bokning !!!!Startdatum!!!! till !!!!Slutdatum!!!! för den
                            lilla slanten !!!!Totalsumma!!!! kr
                            <br><br>
                            Mvh<br>
                            <h2>Bed&Basse</h2>
                            <img class="icon"
                            src="https://cdn0.iconfinder.com/data/icons/free-any-
                            house/96/huge_front_view_house_with_windows-home-256.png"
                            alt="House"></body></html>""").build());
        }
        if (mailTemplateRepo.findByName("ÅterställLösenord") == null) {
            addMailTemplate(MailTemplate.builder()
                    .name("ÅterställLösenord")
                    .subject("Återställ Lösenord - Bed&Basse")
                    .body("<html><body>YO - du har glömt ditt lösenord lalala <a <a \n" +
                            "href=\"!!!!Länk!!!!\">Länk<a></body></html>").build());
        }
    }

    private void addMailTemplate(MailTemplate m) {
        mailTemplateRepo.save(m);
    }
}
