package com.example.LinguaSphere.web;

import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.TeacherDtoBytes;
import com.example.LinguaSphere.entity.dto.TeacherExperienceDto;
import com.example.LinguaSphere.service.LanguageService;
import com.example.LinguaSphere.service.TeacherExperienceService;
import com.example.LinguaSphere.service.TeacherLanguageService;
import com.example.LinguaSphere.service.TeacherService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/experience")
public class TeacherExperienceController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherExperienceService teacherExperienceService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private TeacherLanguageService teacherLanguageService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getExperienceList")
    public String getExperienceList(Teacher teacher, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        List<TeacherExperience> teacherExperiences = teacherExperienceService.findAllByTeacherId(teacherFounded.getId());
        List<TeacherLanguage> teacherLanguages = teacherLanguageService.findAllByTeacherId(teacherFounded.getId());

        Set<Long> languageIds = teacherLanguages.stream()
                .map(TeacherLanguage::getLanguageId)
                .collect(Collectors.toSet());

        teacherExperiences = teacherExperiences.stream()
                .filter(experience -> languageIds.contains(experience.getLanguageId()))
                .toList();

        List<TeacherExperienceDto> teacherExperienceDtos = new ArrayList<>();
        for (TeacherExperience teacherExperience : teacherExperiences) {
            TeacherExperienceDto teacherExperienceDto = modelMapper.map(teacherExperience, TeacherExperienceDto.class);
            Language language = languageService.findById(teacherExperience.getLanguageId());
            teacherExperienceDto.setLanguage(language.getName());
            teacherExperienceDtos.add(teacherExperienceDto);
        }

        model.addAttribute("experiences", teacherExperienceDtos);
        model.addAttribute("teacher", teacherDto);
        return "teacher/teacherExperienceListPage";
    }

    @GetMapping("/updateExperience")
    public String updateExperience(Teacher teacher, Long id, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        TeacherExperience teacherExperience = teacherExperienceService.findById(id);

        model.addAttribute("experience", teacherExperience.getExperience());
        model.addAttribute("languageId", teacherExperience.getLanguageId());
        model.addAttribute("teacher", teacherDto);
        return "teacher/updateTeacherExperiencePage";
    }

    @PostMapping("/setExperience")
    public String setExperience(Teacher teacher, Long languageId, int experience, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        TeacherExperience teacherExperience = teacherExperienceService.findByTeacherIdAndLanguageId(teacherFounded.getId(), languageId);
        teacherExperience.setExperience(experience);
        teacherExperienceService.save(teacherExperience);

        return "redirect:/experience/getExperienceList?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

}
