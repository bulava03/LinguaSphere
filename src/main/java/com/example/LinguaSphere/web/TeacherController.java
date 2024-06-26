package com.example.LinguaSphere.web;

import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.*;
import com.example.LinguaSphere.helper.LessonHelper;
import com.example.LinguaSphere.helper.TeacherHelper;
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
import java.util.Objects;
import java.util.Optional;

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
    private UserMaterialService userMaterialService;
    @Autowired
    private PreferredLinkService preferredLinkService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    private final LessonHelper lessonHelper = new LessonHelper();
    private final TeacherHelper teacherHelper = new TeacherHelper();


    @GetMapping("/teacherPage")
    public String teacherPage(Teacher teacher, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        } else {
            TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
            teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));
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

        int[][] lessons = new int[7][16];
        List<Lesson> list = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                lessons[i][j] = lessonHelper.findLessonByDate(i, j, list);
            }
        }

        TeacherDtoBytes teacherDtoBytes = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDtoBytes.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        model.addAttribute("lessons", lessons);
        model.addAttribute("teacher", teacherDtoBytes);
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

        return "redirect:/teacher/teacherSchedule?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
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

        return "redirect:/teacher/teacherSchedule?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
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

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        model.addAttribute("materials", newList);
        model.addAttribute("teacher", teacherDto);
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

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        model.addAttribute("languages", languages);
        model.addAttribute("teacher", teacherDto);
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

        return "redirect:/teacher/getMaterialsList?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @GetMapping("/updateMaterial")
    public String getUpdateMaterialForm(Teacher teacher, Material material, Model model) throws IOException {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        material = materialsService.findById(material.getId());
        MaterialDtoBytes materialDto = modelMapper.map(material, MaterialDtoBytes.class);
        materialDto.setLanguage(languageService.findById(material.getLanguageId()).getName());
        materialDto.setFileImg(Base64.encodeBase64String(material.getImage()));
        materialDto.setFile(Base64.encodeBase64String(material.getStandardFile()));

        byte[] decodedBytes = material.getStandardFile();
        Path tempFile = Files.createTempFile("tempFile", null);
        try (InputStream is = new ByteArrayInputStream(decodedBytes)) {
            Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        Tika tika = new Tika();
        materialDto.setFileType(tika.detect(tempFile));

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        model.addAttribute("teacher", teacherDto);
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("material", materialDto);
        return "teacher/updateMaterialForm";
    }

    @PostMapping("/updateMaterial")
    public String updateMaterial(Teacher teacher, MaterialDto materialDto, Model model) throws IOException {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        Material materialUpdated = modelMapper.map(materialDto, Material.class);
        materialUpdated.setImage(materialDto.getFileImg().getBytes());
        materialUpdated.setStandardFile(materialDto.getFile().getBytes());
        if (!materialUpdated.getLinks().isEmpty()) {
            List<String> newLinks = new ArrayList<>();
            for (String link : materialUpdated.getLinks()
            ) {
                if (!link.equals("")) {
                    newLinks.add(link);
                }
            }
            materialUpdated.setLinks(newLinks);
        }
        materialsService.save(materialUpdated);

        return "redirect:/teacher/getMaterialsList?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @PostMapping("/deleteMaterial")
    public String deleteMaterial(Teacher teacher, Material material, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        materialsService.deleteById(material.getId());

        return "redirect:/teacher/getMaterialsList?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @GetMapping("/submitUserMaterialPage")
    public String submitUserMaterialPage(Teacher teacher, Lesson lesson, Model model) throws IOException {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        lesson.setTeacherId(teacherFounded.getId());
        List<Lesson> lessonList = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (Lesson element : lessonList
             ) {
            if (element.getDay() == lesson.getDay() && element.getTime() == lesson.getTime()) {
                lesson = element;
                break;
            }
        }

        User user = userService.findById(lesson.getUserId());

        if (user != null) {
            List<Long> materialIds = teacherMaterialService.findMaterialsByTeacherId(teacherFounded.getId());
            List<Material> materials = materialsService.findAllById(materialIds);
            Lesson finalLesson = lesson;
            materials.removeIf(material -> !Objects.equals(material.getLanguageId(), finalLesson.getLanguageId()));
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

            List<Long> availableAll = userMaterialService.findMaterialsIdsByUserId(user.getId());
            List<Long> available = teacherHelper.removeMismatched(availableAll, materials);

            TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
            teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

            model.addAttribute("userId", user.getId());
            model.addAttribute("user", user);
            model.addAttribute("languageId", lesson.getLanguageId());
            model.addAttribute("teacher", teacherDto);
            model.addAttribute("materials", newList);
            model.addAttribute("available", available);
            model.addAttribute("day", lesson.getDay());
            model.addAttribute("time", lesson.getTime());

            return "teacher/userMaterialPage";
        }

        return "redirect:/teacher/teacherSchedule?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @GetMapping("/submitCellLesson")
    public String submitCellLesson(Teacher teacher, Lesson lesson, Model model) throws IOException {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        lesson.setTeacherId(teacherFounded.getId());
        List<Lesson> lessonList = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (Lesson element : lessonList
        ) {
            if (element.getDay() == lesson.getDay() && element.getTime() == lesson.getTime()) {
                lesson = element;
                break;
            }
        }

        User user = userService.findById(lesson.getUserId());
        if (user != null) {

            TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
            teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

            Optional<String> preferredLinkReceived = preferredLinkService.findByUserIdAndTeacherId(user.getId(), teacherFounded.getId());
            String preferredLink = null;
            String program = null;
            if (preferredLinkReceived.isPresent()) {
                preferredLink = preferredLinkReceived.get();

                String[] temp = preferredLink.split(": ");

                preferredLink = temp[1];
                program = temp[0];
            }

            model.addAttribute("userId", user.getId());
            model.addAttribute("user", user);
            model.addAttribute("languageId", lesson.getLanguageId());
            model.addAttribute("teacher", teacherDto);
            model.addAttribute("day", lesson.getDay());
            model.addAttribute("time", lesson.getTime());
            model.addAttribute("preferredLink", preferredLink);
            model.addAttribute("program", program);

            return "teacher/userLessonPage";
        }

        return "redirect:/teacher/teacherSchedule?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @PostMapping("/addAccess")
    public String addAccess(Teacher teacher, UserMaterial userMaterial, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        User user = userService.findById(userMaterial.getUserId());

        if (user != null) {
            userMaterialService.save(userMaterial);

            List<Lesson> lessonList = lessonService.findAllByUserId(userMaterial.getUserId());
            for (Lesson lesson : lessonList
                 ) {
                if (Objects.equals(lesson.getTeacherId(), teacherFounded.getId()) &&
                        Objects.equals(lesson.getLanguageId(), userMaterial.getLanguageId())) {
                    return "redirect:/teacher/submitUserMaterialPage?email=" + teacher.getEmail() + "&password=" + teacher.getPassword()
                            + "&day=" + lesson.getDay() + "&time=" + lesson.getTime();
                }
            }
        }

        return "redirect:/teacher/teacherSchedule?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @PostMapping("/removeAccess")
    public String removeAccess(Teacher teacher, UserMaterial userMaterial, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        User user = userService.findById(userMaterial.getUserId());

        if (user != null) {
            List<UserMaterial> userMaterialList = userMaterialService.findAllByUserId(user.getId());
            for (UserMaterial element : userMaterialList
                 ) {
                if (Objects.equals(element.getMaterialId(), userMaterial.getMaterialId())) {
                    userMaterialService.deleteById(element.getId());
                    break;
                }
            }

            List<Lesson> lessonList = lessonService.findAllByUserId(userMaterial.getUserId());
            for (Lesson lesson : lessonList
            ) {
                if (Objects.equals(lesson.getTeacherId(), teacherFounded.getId()) &&
                        Objects.equals(lesson.getLanguageId(), userMaterial.getLanguageId())) {
                    return "redirect:/teacher/submitUserMaterialPage?email=" + teacher.getEmail() + "&password=" + teacher.getPassword()
                            + "&day=" + lesson.getDay() + "&time=" + lesson.getTime();
                }
            }
        }

        return "redirect:/teacher/teacherSchedule?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @GetMapping("/personalInformation")
    public String getPersonalInformationForm(Teacher teacher, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        model.addAttribute("teacher", teacherDto);
        return "teacher/personalInformationPage";
    }

    @PostMapping("/updatePersonalInformation")
    public String updateTeacherInformation(TeacherDto teacherUpdated, Model model) throws IOException {
        Teacher teacherFounded = teacherService.findByEmail(teacherUpdated.getEmailOld());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacherUpdated.getPasswordOld())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        Teacher teacher = modelMapper.map(teacherUpdated, Teacher.class);
        teacher.setImage(teacherUpdated.getFile().getBytes());
        if (!teacher.getContacts().isEmpty()) {
            List<String> newContacts = new ArrayList<>();
            for (String link : teacher.getContacts()
            ) {
                if (!link.equals("")) {
                    newContacts.add(link);
                }
            }
            teacher.setContacts(newContacts);
        }
        teacher.setId(teacherFounded.getId());
        teacherService.save(teacher);

        return "redirect:/teacher/teacherPage?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @GetMapping("/getPricesPage")
    public String getPricesPage(Teacher teacher, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null || !teacherFounded.getPassword().equals(teacher.getPassword())) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        TeacherDtoBytes teacherDto = modelMapper.map(teacherFounded, TeacherDtoBytes.class);
        teacherDto.setFile(Base64.encodeBase64String(teacherFounded.getImage()));

        List<TeacherLanguageDto> teacherLanguages = teacherLanguageService.getPricesByLanguages(teacherFounded.getId());

        model.addAttribute("languages", teacherLanguages);
        model.addAttribute("teacher", teacherDto);
        return "teacher/pricesPage";
    }

    @GetMapping("/getCertificatesList")
    public String getGradesList(Teacher teacher, Model model) {
        return "redirect:/certificate/getCertificatesList?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

    @GetMapping("/getExperienceList")
    public String getExperienceList(Teacher teacher, Model model) {
        return "redirect:/experience/getExperienceList?email=" + teacher.getEmail() + "&password=" + teacher.getPassword();
    }

}
