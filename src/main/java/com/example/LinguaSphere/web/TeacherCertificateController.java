package com.example.LinguaSphere.web;

import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.TeacherCertificateAddingDto;
import com.example.LinguaSphere.entity.dto.TeacherCertificateDto;
import com.example.LinguaSphere.entity.dto.TeacherDtoBytes;
import com.example.LinguaSphere.service.*;
import org.apache.tika.Tika;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/certificate")
public class TeacherCertificateController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherLanguageService teacherLanguageService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private TeacherParamsService teacherParamsService;
    @Autowired
    private TeacherCertificateService teacherCertificateService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getCertificatesList")
    public String getCertificatesList(Teacher teacher, Model model) throws IOException {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        List<TeacherLanguage> teacherLanguages = teacherLanguageService.findAllByTeacherId(teacherFounded.getId());
        List<TeacherCertificateDto> teacherCertificateDtoList = new ArrayList<>();
        for (TeacherLanguage teacherLanguage : teacherLanguages) {
            List<TeacherCertificate> teacherCertificateList = teacherCertificateService.findAllByTeacherIdAndLanguageId(teacherFounded.getId(), teacherLanguage.getLanguageId());
            String language = languageService.findById(teacherLanguage.getLanguageId()).getName();
            for (TeacherCertificate teacherCertificate : teacherCertificateList) {
                TeacherCertificateDto teacherCertificateDto = modelMapper.map(teacherCertificate, TeacherCertificateDto.class);
                teacherCertificateDto.setLanguage(language);

                teacherCertificateDto.setFile(Base64.encodeBase64String(teacherCertificate.getFile()));

                byte[] decodedBytes = teacherCertificate.getFile();
                Path tempFile = Files.createTempFile("tempFile", null);
                try (InputStream is = new ByteArrayInputStream(decodedBytes)) {
                    Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
                }
                Tika tika = new Tika();
                teacherCertificateDto.setFileType(tika.detect(tempFile));

                teacherCertificateDtoList.add(teacherCertificateDto);
            }
        }

        model.addAttribute("certificates", teacherCertificateDtoList);
        model.addAttribute("teacher", teacherDto);
        return "teacher/certificatesListPage";
    }

    @GetMapping("/getCertificateAddingPage")
    public String getCertificateAddingPage(Teacher teacher, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        List<TeacherLanguage> teacherLanguageList = teacherLanguageService.findAllByTeacherId(teacherFounded.getId());
        List<Long> languageIds = new ArrayList<>();
        for (TeacherLanguage element : teacherLanguageList
        ) {
            if (!languageIds.contains(element.getLanguageId())) {
                languageIds.add(element.getLanguageId());
            }
        }
        List<Language> languages = new ArrayList<>();
        for (Long id : languageIds
        ) {
            languages.add(languageService.findById((id)));
        }

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        model.addAttribute("languages", languages);
        model.addAttribute("teacher", teacherDto);
        return "teacher/certificateAddingPage";
    }

    @PostMapping("/addCertificate")
    public String addCertificate(Teacher teacher, TeacherCertificateAddingDto teacherCertificateAddingDto, Model model) throws IOException {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        TeacherCertificate certificate = modelMapper.map(teacherCertificateAddingDto, TeacherCertificate.class);
        certificate.setFile(teacherCertificateAddingDto.getFileToAdd().getBytes());
        certificate.setTeacherId(teacherFounded.getId());
        teacherCertificateService.save(certificate);

        TeacherParams teacherParams = teacherParamsService.findByTeacherIdAndLanguageId(teacherFounded.getId(), certificate.getLanguageId());
        int certificatesCount = teacherCertificateService.findAllByTeacherIdAndLanguageId(teacherFounded.getId(), certificate.getLanguageId()).size();
        teacherParams.setCertificates(certificatesCount);
        teacherParamsService.save(teacherParams);

        return "redirect:/certificate/getCertificatesList?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @PostMapping("/deleteCertificate")
    public String deleteCertificate(Teacher teacher, TeacherCertificate teacherCertificate, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        teacherCertificate = teacherCertificateService.findById(teacherCertificate.getId());
        teacherCertificateService.deleteById(teacherCertificate.getId());

        TeacherParams teacherParams = teacherParamsService.findByTeacherIdAndLanguageId(teacherFounded.getId(), teacherCertificate.getLanguageId());
        int certificatesCount = teacherCertificateService.findAllByTeacherIdAndLanguageId(teacherFounded.getId(), teacherCertificate.getLanguageId()).size();
        teacherParams.setCertificates(certificatesCount);
        teacherParamsService.save(teacherParams);

        return "redirect:/certificate/getCertificatesList?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

}
