package com.example.LinguaSphere.web;


import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.*;
import com.example.LinguaSphere.service.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private TeacherLanguageService teacherLanguageService;
    @Autowired
    private DailyMessageService dailyMessageService;
    @Autowired
    private CreatureService creatureService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public String getAdminLogin() {
        return "admin/loginPage";
    }

    @PostMapping("/alogin")
    public String getAdminPage(Admin admin, Model model) {
        if (adminService.findByLogin(admin.getLogin()) == null) {
            model.addAttribute("errorText", "Такого адміністратора не існує");
            return "admin/loginPage";
        }

        model.addAttribute("admin", admin);
        return "admin/adminPage";
    }

    @GetMapping("/getTeachersList")
    public String getTeachersList(Admin admin, Model model) {
        List<TeacherRegistration> list = new ArrayList<>();
        for (Teacher element : teacherService.findAll()
             ) {
            TeacherRegistration temp = modelMapper.map(element, TeacherRegistration.class);
            List<TeacherLanguage> temporaryList = teacherLanguageService.findAllByTeacherId(temp.getId());
            List<String> subjectList = new ArrayList<>();
            for (TeacherLanguage elem : temporaryList
                 ) {
                subjectList.add(languageService.findById(elem.getLanguageId()).getName());
            }
            temp.setLanguages(subjectList.toArray(String[]::new));
            list.add(temp);
        }
        model.addAttribute("teachers", list);
        model.addAttribute("admin", admin);
        return "admin/teachersList";
    }

    @GetMapping("/addTeacher")
    public String getTeachersAddingForm(Admin admin, Model model) {
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("admin", admin);
        return "admin/addingTeacherForm";
    }

    @PostMapping("addTeacher")
    public String addTeacher(TeacherRegistration teacherRegistration, Admin admin, Model model) {
        Teacher teacher = modelMapper.map(teacherRegistration, Teacher.class);

        String[] parts = teacher.getPassword().split(",");
        if (parts.length > 1) {
            admin.setPassword(parts[1]);
            teacher.setPassword(parts[0]);
        }

        Object[] validation = teacherService.validateTeacher(teacher);
        if (!(boolean) validation[0]) {
            model.addAttribute("admin", admin);
            model.addAttribute("languages", languageService.findAll());
            model.addAttribute("errorText", validation[1].toString().replaceAll("Optional\\[|\\]", ""));
            return "admin/addingTeacherForm";
        } else if (teacherService.findByEmail(teacher.getEmail()) != null) {
            model.addAttribute("admin", admin);
            model.addAttribute("languages", languageService.findAll());
            model.addAttribute("errorText", "Викладач з такою електронною поштою вже існує!");
            return "admin/addingTeacherForm";
        } else {
            teacherService.save(teacher);
            List<TeacherLanguage> subjects = new ArrayList<>();
            List<String> teachersSubjects = Arrays.stream(teacherRegistration.getLanguages()).toList();
            for (String subject : teachersSubjects
            ) {
                subjects.add(new TeacherLanguage(null, teacher.getId(), (languageService.findByName(subject)).getId()));
            }
            teacherLanguageService.saveAll(subjects);

            List<TeacherRegistration> list = new ArrayList<>();
            for (Teacher element : teacherService.findAll()
            ) {
                TeacherRegistration temp = modelMapper.map(element, TeacherRegistration.class);
                List<TeacherLanguage> temporaryList = teacherLanguageService.findAllByTeacherId(temp.getId());
                List<String> subjectList = new ArrayList<>();
                for (TeacherLanguage elem : temporaryList
                ) {
                    subjectList.add(languageService.findById(elem.getLanguageId()).getName());
                }
                temp.setLanguages(subjectList.toArray(String[]::new));
                list.add(temp);
            }
            model.addAttribute("teachers", list);

            model.addAttribute("admin", admin);
            return "admin/teachersList";
        }
    }

    @GetMapping("/getDailiesList")
    public String getDailiesList(Admin admin, Model model) {
        List<DailyMessage> list = dailyMessageService.findAll();
        List<DailyMessageDtoBytes> newList = new ArrayList<>();
        for (DailyMessage daily : list
             ) {
            DailyMessageDtoBytes dto = modelMapper.map(daily, DailyMessageDtoBytes.class);
            dto.setLanguage(languageService.findById(daily.getLanguageId()).getName());
            dto.setFile(Base64.encodeBase64String(daily.getImage()));
            newList.add(dto);
        }
        model.addAttribute("dailies", newList);
        model.addAttribute("admin", admin);
        return "admin/dailiesList";
    }

    @GetMapping("/addDaily")
    public String getDailiesAddingForm(Admin admin, Model model) {
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("admin", admin);
        return "admin/addingDailiesForm";
    }

    @PostMapping("/addDaily")
    public String addDaily(DailyMessageDto dailyMessageDto, Admin admin, Model model) throws IOException {
        DailyMessage dailyMessage = modelMapper.map(dailyMessageDto, DailyMessage.class);
        dailyMessage.setImage(dailyMessageDto.getFile().getBytes());
        if (!dailyMessage.getLinks().isEmpty()) {
            List<String> newLinks = new ArrayList<>();
            for (String link : dailyMessage.getLinks()
            ) {
                if (!link.equals("")) {
                    newLinks.add(link);
                }
            }
            dailyMessage.setLinks(newLinks);
        }
        dailyMessageService.save(dailyMessage);

        List<DailyMessage> list = dailyMessageService.findAll();
        List<DailyMessageDtoBytes> newList = new ArrayList<>();
        for (DailyMessage daily : list
        ) {
            DailyMessageDtoBytes dto = modelMapper.map(daily, DailyMessageDtoBytes.class);
            dto.setLanguage(languageService.findById(daily.getLanguageId()).getName());
            dto.setFile(Base64.encodeBase64String(daily.getImage()));
            newList.add(dto);
        }

        model.addAttribute("dailies", newList);
        model.addAttribute("admin", admin);
        return "admin/dailiesList";
    }

    @GetMapping("/updateDaily")
    public String getUpdateDailyForm(Admin admin, DailyMessage dailyMessage, Model model) {
        dailyMessage = dailyMessageService.findById(dailyMessage.getId());
        DailyMessageDtoBytes dailyDto = modelMapper.map(dailyMessage, DailyMessageDtoBytes.class);
        dailyDto.setLanguage(languageService.findById(dailyMessage.getLanguageId()).getName());
        dailyDto.setFile(Base64.encodeBase64String(dailyMessage.getImage()));

        model.addAttribute("admin", admin);
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("daily", dailyDto);
        return "admin/updateDailiesForm";
    }

    @PostMapping("/updateDaily")
    public String updateDaily(Admin admin, DailyMessageDto dailyMessageDto, Model model) throws IOException {
        DailyMessage dailyMessage = modelMapper.map(dailyMessageDto, DailyMessage.class);
        dailyMessage.setImage(dailyMessageDto.getFile().getBytes());
        if (!dailyMessage.getLinks().isEmpty()) {
            List<String> newLinks = new ArrayList<>();
            for (String link : dailyMessage.getLinks()
            ) {
                if (!link.equals("")) {
                    newLinks.add(link);
                }
            }
            dailyMessage.setLinks(newLinks);
        }
        dailyMessageService.save(dailyMessage);

        List<DailyMessage> list = dailyMessageService.findAll();
        List<DailyMessageDtoBytes> newList = new ArrayList<>();
        for (DailyMessage daily : list
        ) {
            DailyMessageDtoBytes dto = modelMapper.map(daily, DailyMessageDtoBytes.class);
            dto.setLanguage(languageService.findById(daily.getLanguageId()).getName());
            dto.setFile(Base64.encodeBase64String(daily.getImage()));
            newList.add(dto);
        }
        model.addAttribute("dailies", newList);
        model.addAttribute("admin", admin);
        return "admin/dailiesList";
    }

    @PostMapping("/deleteDaily")
    public String deleteDaily(Admin admin, DailyMessageDto dailyMessageDto, Model model) {
        dailyMessageService.deleteById(dailyMessageDto.getId());
        List<DailyMessage> list = dailyMessageService.findAll();
        List<DailyMessageDtoBytes> newList = new ArrayList<>();
        for (DailyMessage daily : list
        ) {
            DailyMessageDtoBytes dto = modelMapper.map(daily, DailyMessageDtoBytes.class);
            dto.setLanguage(languageService.findById(daily.getLanguageId()).getName());
            dto.setFile(Base64.encodeBase64String(daily.getImage()));
            newList.add(dto);
        }
        model.addAttribute("dailies", newList);
        model.addAttribute("admin", admin);
        return "admin/dailiesList";
    }

    @GetMapping("/getCreaturesList")
    public String getCreaturesList(Admin admin, Model model) {
        List<Creature> creaturesList = creatureService.findAll();
        List<CreatureDtoBytes> listDto = new ArrayList<>();
        for (Creature creature : creaturesList
        ) {
            CreatureDtoBytes dto = modelMapper.map(creature, CreatureDtoBytes.class);
            dto.setLanguage(languageService.findById(creature.getLanguageId()).getName());
            dto.setFile(Base64.encodeBase64String(creature.getImage()));
            listDto.add(dto);
        }

        model.addAttribute("creatures", listDto);
        model.addAttribute("admin", admin);
        return "admin/creaturesList";
    }

    @GetMapping("/addCreature")
    public String getCreaturesAddingForm(Admin admin, Model model) {
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("admin", admin);
        return "admin/addingCreaturesForm";
    }

    @PostMapping("/addCreature")
    public String addCreature(CreatureDto creatureDtoToAdd, Admin admin, Model model) throws IOException {
        Creature creatureToAdd = modelMapper.map(creatureDtoToAdd, Creature.class);
        creatureToAdd.setImage(creatureDtoToAdd.getFile().getBytes());
        if (!creatureToAdd.getHints().isEmpty()) {
            List<String> newHints = new ArrayList<>();
            for (String hint : creatureToAdd.getHints()
            ) {
                if (!hint.equals("")) {
                    newHints.add(hint);
                }
            }
            creatureToAdd.setHints(newHints);
        }
        creatureService.save(creatureToAdd);

        List<Creature> creaturesList = creatureService.findAll();
        List<CreatureDtoBytes> listDto = new ArrayList<>();
        for (Creature creature : creaturesList
        ) {
            CreatureDtoBytes dto = modelMapper.map(creature, CreatureDtoBytes.class);
            dto.setLanguage(languageService.findById(creature.getLanguageId()).getName());
            dto.setFile(Base64.encodeBase64String(creature.getImage()));
            listDto.add(dto);
        }

        model.addAttribute("creatures", listDto);
        model.addAttribute("admin", admin);
        return "admin/creaturesList";
    }

    @GetMapping("/updateCreature")
    public String getUpdateCreatureForm(Admin admin, Creature creature, Model model) {
        creature = creatureService.findById(creature.getId());
        CreatureDtoBytes creatureDto = modelMapper.map(creature, CreatureDtoBytes.class);
        creatureDto.setLanguage(languageService.findById(creature.getLanguageId()).getName());
        creatureDto.setFile(Base64.encodeBase64String(creature.getImage()));

        model.addAttribute("admin", admin);
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("creature", creatureDto);
        return "admin/updateCreaturesForm";
    }

    @PostMapping("/updateCreature")
    public String updateCreature(Admin admin, CreatureDto creatureUpdatedDto, Model model) throws IOException {
        Creature creatureUpdated = modelMapper.map(creatureUpdatedDto, Creature.class);
        creatureUpdated.setImage(creatureUpdatedDto.getFile().getBytes());
        if (!creatureUpdated.getHints().isEmpty()) {
            List<String> newHints = new ArrayList<>();
            for (String hint : creatureUpdated.getHints()
                 ) {
                if (!hint.equals("")) {
                    newHints.add(hint);
                }
            }
            creatureUpdated.setHints(newHints);
        }
        creatureService.save(creatureUpdated);

        List<Creature> creaturesList = creatureService.findAll();
        List<CreatureDtoBytes> listDto = new ArrayList<>();
        for (Creature creature : creaturesList
        ) {
            CreatureDtoBytes dto = modelMapper.map(creature, CreatureDtoBytes.class);
            dto.setLanguage(languageService.findById(creature.getLanguageId()).getName());
            dto.setFile(Base64.encodeBase64String(creature.getImage()));
            listDto.add(dto);
        }

        model.addAttribute("creatures", listDto);
        model.addAttribute("admin", admin);
        return "admin/creaturesList";
    }

    @PostMapping("/deleteCreature")
    public String deleteCreature(Admin admin, Creature creatureToDelete, Model model) {
        creatureService.deleteById(creatureToDelete.getId());

        List<Creature> creaturesList = creatureService.findAll();
        List<CreatureDtoBytes> listDto = new ArrayList<>();
        for (Creature creature : creaturesList
        ) {
            CreatureDtoBytes dto = modelMapper.map(creature, CreatureDtoBytes.class);
            dto.setLanguage(languageService.findById(creature.getLanguageId()).getName());
            dto.setFile(Base64.encodeBase64String(creature.getImage()));
            listDto.add(dto);
        }

        model.addAttribute("creatures", listDto);
        model.addAttribute("admin", admin);
        return "admin/creaturesList";
    }

}
