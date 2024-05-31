package com.example.pensionat.services.impl.unit;

import com.example.pensionat.dtos.MailTemplateDTO;
import com.example.pensionat.models.MailTemplate;
import com.example.pensionat.repositories.MailTemplateRepo;
import com.example.pensionat.services.convert.MailTemplateConverter;
import com.example.pensionat.services.interfaces.MailTemplateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MailTemplateServiceImplTest {

    @Mock
    private MailTemplateRepo mailTemplateRepo;
    @Mock
    private MailTemplateService mailTemplateService;

    MailTemplate m1 = new MailTemplate(1L, "testTemplate", "testSubject", "testBody");
    MailTemplate m2 = new MailTemplate(2L, "testTemplate1337", "testSubject1337", "testBody1337");
    MailTemplateDTO md1 = new MailTemplateDTO(3L, "testTemplateDto", "testTemplateSubject", "testBodyDto");
    String newContent = "updatedTestContent";

    @Test
    void updateTemplate() {
        when(mailTemplateRepo.findByName("testTemplate")).thenReturn(m1);

        MailTemplate result = mailTemplateRepo.findByName("testTemplate");
        result.setBody(newContent);
        mailTemplateRepo.save(result);

        assertNotNull(result);
        verify(mailTemplateRepo, times(1)).findByName("testTemplate");
        verify(mailTemplateRepo, times(1)).save(any(MailTemplate.class));
        verify(mailTemplateRepo, times(1)).save(result);
    }

    @Test
    void getAllTemplatesMapsCorrectAndFindsTemplates() {
        when(mailTemplateRepo.findAll()).thenReturn(List.of(m1, m2));

        List <MailTemplate> resultList = mailTemplateRepo.findAll();

        List<MailTemplateDTO> resultDtoList = resultList.stream()
                .map(m -> MailTemplateDTO.builder().id(m.getId()).name(m.getName()).body(m.getBody()).build()).toList();

        assertEquals(resultDtoList.get(0).getId(), 1L);
        assertEquals(resultDtoList.get(0).getName(), "testTemplate");
        assertEquals(resultDtoList.get(0).getBody(), "testBody");
        assertEquals(resultDtoList.size(), 2);
        verify(mailTemplateRepo, times(1)).findAll();
    }

    @Test
    void getMailTemplateByIdWillFindMailTemplate() {
        when(mailTemplateRepo.findById(1L)).thenReturn(Optional.of(m1));

        MailTemplate result = mailTemplateRepo.findById(1L).orElse(null);
        MailTemplateDTO testTemplateDtoResult = MailTemplateConverter.mailTemplateToMailTemplateDTO(result);

        assertNotNull(testTemplateDtoResult, "The DTO should not be null");
        assertEquals(testTemplateDtoResult.getId(), 1L);
        assertEquals(testTemplateDtoResult.getName(), "testTemplate");
        assertEquals(testTemplateDtoResult.getSubject(), "testSubject");
        assertEquals(testTemplateDtoResult.getBody(), "testBody");
        verify(mailTemplateRepo, times(1)).findById(1L);
    }

    @Test
    void getMailTemplateByIdIsCalledAsExpected(){
        when(mailTemplateService.getMailTemplateById(3L)).thenReturn(md1);

        MailTemplateDTO result = mailTemplateService.getMailTemplateById(3L);

        assertNotNull(result, "Should not be null");
        verify(mailTemplateService, times(1)).getMailTemplateById(3L);
    }

    @Test
    void getMailTemplateByNameIsCalledAsExpected(){
        when(mailTemplateService.getMailTemplateByName("testTemplateDto")).thenReturn(md1);

        MailTemplateDTO result = mailTemplateService.getMailTemplateByName("testTemplateDto");

        assertNotNull(result, "Should not be null");
        verify(mailTemplateService, times(1)).getMailTemplateByName("testTemplateDto");
    }

    @Test
    void getMailTemplateByIdWillNotFindMailTemplate() {
        when(mailTemplateService.getMailTemplateById(999L)).thenReturn(null);

        MailTemplateDTO result = mailTemplateService.getMailTemplateById(999L);

        assertNull(result, "Should be null");
        verify(mailTemplateService, times(1)).getMailTemplateById(999L);
    }


    @Test
    void getMailTemplateByNameWillFindMailTemplate() {
        when(mailTemplateRepo.findByName("testTemplate1337")).thenReturn(m2);

        MailTemplate result = mailTemplateRepo.findByName("testTemplate1337");
        MailTemplateDTO testTemplateDtoResult = MailTemplateConverter.mailTemplateToMailTemplateDTO(result);

        assertNotNull(testTemplateDtoResult, "The DTO should not be null");
        assertEquals(testTemplateDtoResult.getId(), 2L);
        assertEquals(testTemplateDtoResult.getName(), "testTemplate1337");
        assertEquals(testTemplateDtoResult.getSubject(), "testSubject1337");
        assertEquals(testTemplateDtoResult.getBody(), "testBody1337");
        verify(mailTemplateRepo, times(1)).findByName("testTemplate1337");

    }

    @Test
    void getMailTemplateByNameWillNotFindMailTemplate() {
        when(mailTemplateService.getMailTemplateByName("Ernst")).thenReturn(null);

        MailTemplateDTO result = mailTemplateService.getMailTemplateByName("Ernst");

        assertNull(result, "Should be null");
        verify(mailTemplateService, times(1)).getMailTemplateByName("Ernst");
    }
}