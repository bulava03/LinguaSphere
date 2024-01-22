package com.example.LinguaSphere.web;

import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.DailyMessageDtoBytes;
import com.example.LinguaSphere.entity.dto.MaterialDto;
import com.example.LinguaSphere.entity.dto.MaterialDtoBytes;
import com.example.LinguaSphere.entity.dto.TeacherDto;
import com.example.LinguaSphere.helper.LessonHelper;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.spi.FileTypeDetector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.ScrollPosition.forward;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private TeacherMaterialService teacherMaterialService;
    @Autowired
    private TeacherLanguageService teacherLanguageService;
    @Autowired
    private ModelMapper modelMapper;

    private final LessonHelper lessonHelper = new LessonHelper();

    @GetMapping("/teacherPage")
    public String teacherPage(Teacher teacher, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        } else {
            TeacherDto teacherDto = modelMapper.map(teacherFounded, TeacherDto.class);
            model.addAttribute("teacher", teacherDto);
            return "teacher/teacherPage";
        }
    }

    @GetMapping("/teacherSchedule")
    public String teacherSchedule(Teacher teacher, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        TeacherDto teacherDto = modelMapper.map(teacherFounded, TeacherDto.class);
        model.addAttribute("teacher", teacherDto);

        int[][] lessons = new int[7][16];
        List<Lesson> list = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                lessons[i][j] = lessonHelper.findLessonByDate(i, j, list);
            }
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("teacher", teacherDto);
        return "teacher/teacherSchedule";
    }

    @PostMapping("/submitCellUsed")
    public String submitCellUsed(Teacher teacher, Lesson lesson, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        List<Lesson> list = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (Lesson element : list
             ) {
            if (element.getDay() == lesson.getDay() && element.getTime() == lesson.getTime()) {
                lessonService.deleteById(element.getId());
            }
        }

        TeacherDto teacherDto = modelMapper.map(teacherFounded, TeacherDto.class);
        int[][] lessons = new int[7][16];
        list = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                lessons[i][j] = lessonHelper.findLessonByDate(i, j, list);
            }
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("teacher", teacherDto);
        return "teacher/teacherSchedule";
    }

    @PostMapping("/submitCellFree")
    public String submitCellFree(Teacher teacher, Lesson lesson, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        lesson.setTeacherId(teacherFounded.getId());
        lessonService.save(lesson);

        TeacherDto teacherDto = modelMapper.map(teacherFounded, TeacherDto.class);
        int[][] lessons = new int[7][16];
        List<Lesson> list = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                lessons[i][j] = lessonHelper.findLessonByDate(i, j, list);
            }
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("teacher", teacherDto);
        return "teacher/teacherSchedule";
    }

    @GetMapping("/getMaterialsList")
    public String getMaterialsList(Teacher teacher, Model model) throws IOException {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        List<Long> materialIds = teacherMaterialService.findMaterialsByTeacherId(teacherFounded.getId());
        List<Material> materials = materialsService.findAllById(materialIds);
        List<MaterialDtoBytes> newList = new ArrayList<>();
        for (Material material : materials
        ) {
            MaterialDtoBytes dto = modelMapper.map(material, MaterialDtoBytes.class);
            dto.setLanguage(languageService.findById(material.getLanguageId()).getName());
            dto.setFileImg(Base64.encodeBase64String(material.getImage()));
            dto.setFile(Base64.encodeBase64String(material.getStandardFile()));

            byte[] decodedBytes = material.getStandardFile();
            Path tempFile = Files.createTempFile("tempFile", null);
            try (InputStream is = new ByteArrayInputStream(decodedBytes)) {
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            Tika tika = new Tika();
            dto.setFileType(tika.detect(tempFile));

            newList.add(dto);
        }
        model.addAttribute("materials", newList);
        model.addAttribute("teacher", teacherFounded);
        return "teacher/materialsList";
    }

    @GetMapping("/addMaterial")
    public String getMaterialAddingForm(Teacher teacher, Model model) {
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

        model.addAttribute("languages", languages);
        model.addAttribute("teacher", teacherFounded);
        return "teacher/addingMaterialForm";
    }

    @PostMapping("/addMaterial")
    public String addMaterial(Teacher teacher, MaterialDto materialDto, Model model) throws IOException {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        Material material = modelMapper.map(materialDto, Material.class);
        material.setImage(materialDto.getFileImg().getBytes());
        material.setStandardFile(materialDto.getFile().getBytes());
        if (!material.getLinks().isEmpty()) {
            List<String> newLinks = new ArrayList<>();
            for (String link : material.getLinks()
            ) {
                if (!link.equals("")) {
                    newLinks.add(link);
                }
            }
            material.setLinks(newLinks);
        }
        materialsService.save(material);

        TeacherMaterial teacherMaterial = new TeacherMaterial();
        teacherMaterial.setMaterialId(material.getId());
        teacherMaterial.setTeacherId(teacherFounded.getId());
        teacherMaterialService.save(teacherMaterial);

        model.addAttribute("teacher", teacher);
        return "redirect:/teacher/getMaterialsList?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

}
