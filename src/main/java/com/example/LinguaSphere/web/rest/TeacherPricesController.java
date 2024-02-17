package com.example.LinguaSphere.web.rest;

import com.example.LinguaSphere.entity.Teacher;
import com.example.LinguaSphere.entity.TeacherLanguage;
import com.example.LinguaSphere.service.TeacherLanguageService;
import com.example.LinguaSphere.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/teacherPrices")
public class TeacherPricesController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherLanguageService teacherLanguageService;

    @PostMapping("/setNewPrice")
    public void setNewPrice(String teacherEmail, String teacherPassword, Long languageId, Double newPrice) {
        Teacher teacherFounded = teacherService.findByEmail(teacherEmail);
        if (teacherFounded != null && teacherFounded.getPassword().equals(teacherPassword)) {
            List<TeacherLanguage> teacherLanguages = teacherLanguageService.findAllByTeacherId(teacherFounded.getId());
            for (TeacherLanguage teacherLanguage : teacherLanguages
                 ) {
                if (Objects.equals(teacherLanguage.getLanguageId(), languageId)) {
                    teacherLanguage.setPrice(newPrice);
                    teacherLanguageService.save(teacherLanguage);
                    break;
                }
            }
        }
    }

}
