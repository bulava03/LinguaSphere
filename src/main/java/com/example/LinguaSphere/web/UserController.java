package com.example.LinguaSphere.web;

import com.example.LinguaSphere.config.security.JwtTokenService;
import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.*;
import com.example.LinguaSphere.helper.UserHelper;
import com.example.LinguaSphere.service.*;
import com.example.LinguaSphere.service.impl.UserDetailsServiceImpl;
import org.apache.tika.Tika;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherLanguageService teacherLanguageService;
    @Autowired
    private DailyMessageService dailyMessageService;
    @Autowired
    private CreatureService creatureService;
    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private UserMaterialService userMaterialService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenService jwtTokenService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserHelper userHelper = new UserHelper();


    @GetMapping("userPage")
    public String userPage(@ModelAttribute("request") RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));
            model.addAttribute("user", userDto);
            model.addAttribute("token", request.getToken());
            return "user/userPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("userSchedule")
    public String getUserSchedule(@ModelAttribute("request") RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            int[][] lessons = userHelper.getUsersLessons(lessonService.findAllByUserId(userFounded.getId()));

            model.addAttribute("user", userDto);
            model.addAttribute("lessons", lessons);
            model.addAttribute("token", request.getToken());
            return "user/userSchedule";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("getLanguageChoosingPage")
    public String getLanguageChoosingPage(@ModelAttribute("request") RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            model.addAttribute("user", userDto);
            model.addAttribute("languages", languageService.findAll());
            model.addAttribute("token", request.getToken());
            return "user/choosingLanguagePage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("submitChooseLanguageForm")
    public String submitChooseLanguageForm(RequestDto request, LessonTemplate lesson, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            List<TeacherLanguage> teacherLanguages = teacherLanguageService.findAllByLanguageId(lesson.getLanguageId());
            List<Long> list = new ArrayList<>();
            for (TeacherLanguage element : teacherLanguages
                 ) {
                list.add(element.getTeacherId());
            }
            List<Lesson> lessons = lessonService.findAllByTeacherIds(list);
            List<Lesson> usersLessons = lessonService.findAllByUserId(userFounded.getId());
            lessons.removeIf(element -> element.getUserId() != null);
            for (Lesson element : usersLessons
                 ) {
                lessons.removeIf(elem -> elem.getDay() == element.getDay() && elem.getTime() == element.getTime());
            }

            List<LessonTemplate> lessonList = new ArrayList<>();
            for (Lesson element : lessons
                 ) {
                Teacher teacherTemp = teacherService.findById(element.getTeacherId());
                String teacherName = teacherTemp.getName() + " " + teacherTemp.getSurname();
                String date = userHelper.convertIntIntoDate(element.getDay(), element.getTime());
                lessonList.add(new LessonTemplate(element.getId(), lesson.getLanguageId(), element.getTeacherId(),
                        teacherName, date));
            }

            model.addAttribute("user", userDto);
            model.addAttribute("lessons", lessonList);
            model.addAttribute("language", languageService.findById(lesson.getLanguageId()));
            model.addAttribute("token", request.getToken());
            return "user/choosingLessonPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @PostMapping("setLesson")
    public String setLesson(RequestDto request, LessonTemplate lesson, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            Lesson lessonToSave = lessonService.findById(lesson.getId());
            lessonToSave.setUserId(userFounded.getId());
            lessonToSave.setLanguageId(lesson.getLanguageId());
            lessonService.save(lessonToSave);

            List<TeacherLanguage> teacherLanguages = teacherLanguageService.findAllByLanguageId(lesson.getLanguageId());
            List<Long> list = new ArrayList<>();
            for (TeacherLanguage element : teacherLanguages
            ) {
                list.add(element.getTeacherId());
            }
            List<Lesson> lessons = lessonService.findAllByTeacherIds(list);
            List<Lesson> usersLessons = lessonService.findAllByUserId(userFounded.getId());
            lessons.removeIf(element -> element.getUserId() != null);
            for (Lesson element : usersLessons
            ) {
                lessons.removeIf(elem -> elem.getDay() == element.getDay() && elem.getTime() == element.getTime());
            }

            List<LessonTemplate> lessonList = new ArrayList<>();
            for (Lesson element : lessons
            ) {
                Teacher teacherTemp = teacherService.findById(element.getTeacherId());
                String teacherName = teacherTemp.getName() + " " + teacherTemp.getSurname();
                String date = userHelper.convertIntIntoDate(element.getDay(), element.getTime());
                lessonList.add(new LessonTemplate(element.getId(), lesson.getLanguageId(), element.getTeacherId(),
                        teacherName, date));
            }

            model.addAttribute("user", userDto);
            model.addAttribute("lessons", lessonList);
            model.addAttribute("language", languageService.findById(lesson.getLanguageId()));
            model.addAttribute("token", request.getToken());
            return "user/choosingLessonPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("dailyFact")
    public String getDailyFact(RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            if (userFounded.getNewDailyDate() == null) {
                userFounded.setNewDailyDate(LocalDateTime.now().with(LocalTime.MIN).plusDays(1));
                userService.updateUser(userFounded);
            }

            if (LocalDateTime.now().isAfter(userFounded.getNewDailyDate())) {
                userFounded.setDailyId(null);
                userFounded.setNewDailyDate(LocalDateTime.now().with(LocalTime.MIN).plusDays(1));
                userService.updateUser(userFounded);
            }

            if (userFounded.getDailyId() == null || dailyMessageService.findById(userFounded.getDailyId()) == null) {
                List<Lesson> lessons = lessonService.findAllByUserId(userFounded.getId());
                List<Long> languagesIds = new ArrayList<>();
                for (Lesson lesson : lessons
                ) {
                    if (!languagesIds.contains(lesson.getLanguageId())) {
                        languagesIds.add(lesson.getLanguageId());
                    }
                }

                List<DailyMessage> variants = new ArrayList<>();
                for (Long languageId : languagesIds
                     ) {
                    variants.addAll(dailyMessageService.findAllByLanguageId(languageId));
                }
                if (variants.size() > 0) {
                    Random random = new Random();
                    int factNumber = random.nextInt(variants.size());
                    DailyMessage dailyMessage = variants.get(factNumber);
                    userFounded.setDailyId(dailyMessage.getId());
                    userFounded.setNewDailyDate(LocalDateTime.now().with(LocalTime.MIN).plusDays(1));
                    userService.updateUser(userFounded);
                }
            }

            if (userFounded.getDailyId() != null) {
                DailyMessage dailyMessage = dailyMessageService.findById(userFounded.getDailyId());
                DailyMessageDtoBytes daily = modelMapper.map(dailyMessage, DailyMessageDtoBytes.class);
                daily.setFile(Base64.encodeBase64String(dailyMessage.getImage()));
                model.addAttribute("dailyMessage", daily);
            } else {
                model.addAttribute("dailyMessage", null);
            }

            model.addAttribute("user", userDto);
            model.addAttribute("token", request.getToken());
            return "user/dailyFactPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("getCreatureLanguageList")
    public String getCreatureLanguageList(@ModelAttribute("request") RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            List<Lesson> userLessons = lessonService.findAllByUserId(userFounded.getId());
            List<Long> languagesIds = new ArrayList<>();
            for (Lesson lesson : userLessons
            ) {
                if (!languagesIds.contains(lesson.getLanguageId())) {
                    languagesIds.add(lesson.getLanguageId());
                }
            }
            List<Language> languages = new ArrayList<>();
            for (Long languageId : languagesIds
                 ) {
                languages.add(languageService.findById(languageId));
            }

            model.addAttribute("user", userDto);
            model.addAttribute("languages", languages);
            model.addAttribute("token", request.getToken());
            return "user/choosingLanguagePageCreature";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("submitChooseLanguageCreatureForm")
    public String getCreatureGuessPage(LessonTemplate languageSelected, RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            Language language = languageService.findById(languageSelected.getLanguageId());
            List<Creature> creatures = creatureService.findAllByLanguageId(language.getId());
            Creature creature = null;
            if (creatures.size() > 0) {
                Random random = new Random();
                int number = random.nextInt(creatures.size());
                creature = creatures.get(number);
            }
            CreatureToGuess creatureToGuess = modelMapper.map(creature, CreatureToGuess.class);
            creatureToGuess.setLanguage(languageService.findById(creature.getLanguageId()).getName());
            creatureToGuess.setHintsLeft(creature.getHints().size() - 1);
            List<String> hints = new ArrayList<>();
            hints.add(creatureToGuess.getHints().get(0));
            creatureToGuess.setHints(hints);

            model.addAttribute("user", userDto);
            model.addAttribute("creature", creatureToGuess);
            model.addAttribute("token", request.getToken());
            return "user/creatureGuessingPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("getHint")
    public String getHint(RequestDto request, CreatureToGuess creatureToGuess, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            Creature creature = creatureService.findById(creatureToGuess.getId());
            CreatureToGuess newCreatureToGuess = modelMapper.map(creature, CreatureToGuess.class);
            newCreatureToGuess.setLanguage(languageService.findById(creature.getLanguageId()).getName());
            newCreatureToGuess.setHintsLeft(creatureToGuess.getHintsLeft() - 1);
            int hintsSize = creature.getHints().size();
            int lastHintIndex = hintsSize - creatureToGuess.getHintsLeft();
            List<String> allHints = creature.getHints();
            List<String> newHints = new ArrayList<>();
            for (int i = 0; i <= lastHintIndex; i++) {
                newHints.add(allHints.get(i));
            }
            newCreatureToGuess.setHints(newHints);

            model.addAttribute("user", userDto);
            model.addAttribute("creature", newCreatureToGuess);
            model.addAttribute("token", request.getToken());
            return "user/creatureGuessingPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @PostMapping("giveUp")
    public String giveUp(RequestDto request, CreatureToGuess creatureToGuess, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            userFounded.setLastGuessedCount(0);
            userService.updateUser(userFounded);

            Creature creature = creatureService.findById(creatureToGuess.getId());
            creature.setHints(new ArrayList<>());
            CreatureDtoBytes creatureDtoBytes = modelMapper.map(creature, CreatureDtoBytes.class);
            creatureDtoBytes.setFile(Base64.encodeBase64String(creature.getImage()));

            model.addAttribute("user", userDto);
            model.addAttribute("creature", creatureDtoBytes);
            model.addAttribute("token", request.getToken());
            return "user/creatureAnswerPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @PostMapping("checkCreatureAnswerForm")
    public String checkCreatureAnswer(RequestDto request, CreatureToGuess creatureAnswered, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            Creature creature = creatureService.findById(creatureAnswered.getId());

            if (userFounded.getScore() == null) {
                userFounded.setScore(0L);
                userService.updateUser(userFounded);
            }

            if (creature.getName().equalsIgnoreCase(creatureAnswered.getName())) {
                userFounded.setLastGuessedCount(userFounded.getLastGuessedCount() + 1);
                userFounded.setScore(userFounded.getScore() + 1);
                if (userFounded.getLastGuessedCount() == 10) {
                    userFounded.setLastGuessedCount(0);
                    userFounded.setScore(userFounded.getScore() + 10);
                }
                userService.updateUser(userFounded);

                creature.setHints(new ArrayList<>());
                CreatureDtoBytes creatureDtoBytes = modelMapper.map(creature, CreatureDtoBytes.class);
                creatureDtoBytes.setFile(Base64.encodeBase64String(creature.getImage()));

                model.addAttribute("user", userDto);
                model.addAttribute("creature", creatureDtoBytes);
                model.addAttribute("token", request.getToken());
                return "user/creatureAnswerPage";
            } else {
                CreatureToGuess newCreatureToGuess = modelMapper.map(creature, CreatureToGuess.class);
                newCreatureToGuess.setLanguage(languageService.findById(creature.getLanguageId()).getName());
                newCreatureToGuess.setHintsLeft(creatureAnswered.getHintsLeft());
                int hintsSize = creature.getHints().size();
                int lastHintIndex = hintsSize - creatureAnswered.getHintsLeft();
                List<String> allHints = creature.getHints();
                List<String> newHints = new ArrayList<>();
                for (int i = 0; i < lastHintIndex; i++) {
                    newHints.add(allHints.get(i));
                }
                newCreatureToGuess.setHints(newHints);

                model.addAttribute("errorText", "Ви не вгадали. Спробуйте ще раз!");
                model.addAttribute("user", userDto);
                model.addAttribute("creature", newCreatureToGuess);
                model.addAttribute("token", request.getToken());
                return "user/creatureGuessingPage";
            }
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("/getMaterialsLanguageList")
    public String getMaterialsLanguageForm(RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            List<Lesson> userLessons = lessonService.findAllByUserId(userFounded.getId());
            List<Long> languagesIds = new ArrayList<>();
            for (Lesson lesson : userLessons
            ) {
                if (!languagesIds.contains(lesson.getLanguageId())) {
                    languagesIds.add(lesson.getLanguageId());
                }
            }
            List<Language> languages = new ArrayList<>();
            for (Long languageId : languagesIds
            ) {
                languages.add(languageService.findById(languageId));
            }

            model.addAttribute("user", userDto);
            model.addAttribute("languages", languages);
            model.addAttribute("token", request.getToken());
            return "user/choosingMaterialsLanguagePage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("/submitChooseLanguageMaterialsForm")
    public String getMaterialsPage(RequestDto request, LessonTemplate lessonTemplate, Model model) throws IOException {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            Long languageId = lessonTemplate.getLanguageId();

            List<UserMaterial> userMaterialList = userMaterialService.findAllByUserId(userFounded.getId());
            userMaterialList.removeIf(material -> !Objects.equals(material.getLanguageId(), languageId));
            List<Long> materialsIds = userHelper.getIdsFromUserMaterialsList(userMaterialList);
            List<Material> materials = materialsService.findAllById(materialsIds);

            List<MaterialDtoBytes> materialsDtoList = new ArrayList<>();
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

                materialsDtoList.add(dto);
            }

            model.addAttribute("user", userDto);
            model.addAttribute("languageId", languageId);
            model.addAttribute("materials", materialsDtoList);
            model.addAttribute("languageSubName", languageService.findById(languageId).getSubName());
            model.addAttribute("token", request.getToken());
            return "user/materialsPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

}
