package com.example.LinguaSphere.web;

import com.example.LinguaSphere.config.security.JwtTokenService;
import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.*;
import com.example.LinguaSphere.extra.TeacherRatingCalc;
import com.example.LinguaSphere.helper.ConvertHelper;
import com.example.LinguaSphere.helper.CreatureHelper;
import com.example.LinguaSphere.helper.UserHelper;
import com.example.LinguaSphere.service.*;
import com.example.LinguaSphere.service.impl.UserDetailsServiceImpl;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private TeacherMaterialService teacherMaterialService;
    @Autowired
    private PreferredLinkService preferredLinkService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TeacherParamsService teacherParamsService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenService jwtTokenService;

    private final UserHelper userHelper = new UserHelper();
    private final CreatureHelper creatureHelper = new CreatureHelper();


    @GetMapping("userPage")
    public String userPage(@ModelAttribute("request") RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = modelMapper.map(userFounded, UserDtoBytes.class);
        userDto.setDateOfBirth(userHelper.formDate(userFounded));
        userDto.setFile(Base64.encodeBase64String(userFounded.getImage()));

        model.addAttribute("user", userDto);
        model.addAttribute("token", request.getToken());
        return "user/userPage";
    }

    @GetMapping("userSchedule")
    public String getUserSchedule(@ModelAttribute("request") RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        int[][] lessons = userHelper.getUsersLessons(lessonService.findAllByUserId(userFounded.getId()));

        model.addAttribute("user", userDto);
        model.addAttribute("lessons", lessons);
        model.addAttribute("token", request.getToken());
        return "user/userSchedule";
    }

    @GetMapping("getLanguageChoosingPage")
    public String getLanguageChoosingPage(@ModelAttribute("request") RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        model.addAttribute("user", userDto);
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("token", request.getToken());
        return "user/choosingLanguagePage";
    }

    @GetMapping("getLanguageChoosingPageInUserPage")
    public String getLanguageChoosingPageInUserPage(@ModelAttribute("request") RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        model.addAttribute("user", userDto);
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("token", request.getToken());
        return "user/choosingLanguagePageInUserPage";
    }

    @GetMapping("submitChooseLanguageForm")
    public String submitChooseLanguageForm(RequestDto request, LessonTemplate lesson, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        List<TeacherLanguage> teacherLanguages = teacherLanguageService.findAllByLanguageId(lesson.getLanguageId());
        List<Long> list = new ArrayList<>();
        for (TeacherLanguage element : teacherLanguages
        ) {
            list.add(element.getTeacherId());
        }

        List<TeacherDtoBytes> teacherList = new ArrayList<>();
        List<TeacherParams> teacherParamsList = new ArrayList<>();
        for (Long id : list
             ) {
            List<Lesson> lessonList = lessonService.findAllByTeacherId(id);
            lessonList.removeIf(lessonElement -> lessonElement.getUserId() != null);

            if (!lessonList.isEmpty()) {
                Teacher teacherElement = teacherService.findById(id);
                TeacherParams teacherParams = teacherParamsService.findByTeacherIdAndLanguageId(teacherElement.getId(), lesson.getLanguageId());
                teacherParamsList.add(teacherParams);
                TeacherDtoBytes teacherDtoBytes = modelMapper.map(teacherElement, TeacherDtoBytes.class);
                teacherDtoBytes.setFile(Base64.encodeBase64String(teacherElement.getImage()));

                TeacherLanguage teacherLanguage = teacherLanguageService.findByTeacherIdAndLanguageId(
                        teacherElement.getId(), lesson.getLanguageId());
                teacherDtoBytes.setPrice(teacherLanguage.getPrice());

                teacherList.add(teacherDtoBytes);
            }
        }

        List<TeacherDtoBytes> teacherListSorted = new ArrayList<>();
        if (!teacherParamsList.isEmpty()) {
            teacherParamsList = TeacherRatingCalc.sortTeacherByParams(teacherParamsList);
            List<Long> teacherIdsSorted = teacherParamsList.stream()
                    .map(TeacherParams::getTeacherId)
                    .toList();

            for (Long element : teacherIdsSorted) {
                Teacher teacherTemp = teacherService.findById(element);
                String email = teacherTemp.getEmail();
                teacherList.stream()
                        .filter(elem -> elem.getEmail().equals(email))
                        .findFirst().ifPresent(teacherListSorted::add);
            }
        }

        model.addAttribute("user", userDto);
        model.addAttribute("teachers", teacherListSorted);
        model.addAttribute("languageId", lesson.getLanguageId());
        model.addAttribute("token", request.getToken());
        return "user/teachersListPage";
    }

    @GetMapping("teacherSchedulePage")
    public String getTeacherSchedulePage(RequestDto request, LessonTemplate lesson, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        Teacher teacherFounded = teacherService.findByEmail(lesson.getTeacherEmail());
        if (teacherFounded != null) {
            List<Lesson> lessons = lessonService.findAllByTeacherId(teacherFounded.getId());
            lessons.removeIf(element -> element.getUserId() != null);
            List<Lesson> usersLessons = lessonService.findAllByUserId(userFounded.getId());
            List<Lesson> lessonsWithThisTeacher = new ArrayList<>(List.copyOf(usersLessons));
            lessonsWithThisTeacher.removeIf(element -> !Objects.equals(element.getUserId(), userFounded.getId()));

            int[][] lessonsArray = userHelper.getTeachersSchedule(lessons, usersLessons, lessonsWithThisTeacher);

            model.addAttribute("user", userDto);
            model.addAttribute("lessons", lessonsArray);
            model.addAttribute("languageId", lesson.getLanguageId());
            model.addAttribute("teacherEmail", lesson.getTeacherEmail());
            model.addAttribute("token", request.getToken());
            return "user/teachersSchedulePage";
        } else {
            return "redirect:/user/teacherSchedulePage?token=" + request.getToken();
        }
    }

    @GetMapping("getLessonPage")
    public String getLessonPage(RequestDto request, LessonTemplate lesson, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        Lesson usersLesson = lessonService.findAllByUserId(userFounded.getId())
                .stream()
                .filter(element -> element.getDay() == lesson.getDay() && element.getTime() == lesson.getTime())
                .findFirst()
                .orElse(null);

        Teacher teacherFounded = teacherService.findById(usersLesson.getTeacherId());
        if (teacherFounded != null) {
            TeacherLanguage teacherLanguage = teacherLanguageService.findByTeacherIdAndLanguageId(
                    teacherFounded.getId(), usersLesson.getLanguageId());
            double price = teacherLanguage.getPrice();

            Optional<String> preferredLinkReceived = preferredLinkService.findByUserIdAndTeacherId(userFounded.getId(), teacherFounded.getId());
            String preferredLink = null;
            String program = null;
            if (preferredLinkReceived.isPresent()) {
                preferredLink = preferredLinkReceived.get();

                String[] temp = preferredLink.split(": ");

                preferredLink = temp[1];
                program = temp[0];
            }

            model.addAttribute("user", userDto);
            model.addAttribute("price", price);
            model.addAttribute("lesson", usersLesson);
            model.addAttribute("day", lesson.getDay());
            model.addAttribute("time", lesson.getTime());
            model.addAttribute("teacherId", teacherFounded.getId());
            model.addAttribute("token", request.getToken());
            model.addAttribute("preferredLink", preferredLink);
            model.addAttribute("program", program);

            return "user/lessonPage";
        } else {
            return "redirect:/user/submitChooseLanguageForm?token=" + request.getToken() + "&languageid=" + lesson.getLanguageId();
        }
    }

    @GetMapping("getLinksPage")
    public String getLinksPage(RequestDto request, Model model) throws IOException {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        List<Lesson> lessonList = lessonService.findAllByUserId(userFounded.getId());
        List<Long> teacherIds = lessonList.stream()
                .map(Lesson::getTeacherId)
                .distinct()
                .toList();

        List<PreferredLink> preferredLinkList = preferredLinkService.findAllByUserId(userFounded.getId());
        for (Long element : teacherIds) {
            if (preferredLinkList.stream()
                    .noneMatch(elem -> Objects.equals(elem.getTeacherId(), element))) {
                PreferredLink newLink = new PreferredLink(userFounded.getId(), element);
                preferredLinkService.save(newLink);
            }
        }
        preferredLinkList = preferredLinkService.findAllByUserId(userFounded.getId());

        List<PreferredLinkDto> list = new ArrayList<>();
        for (PreferredLink element : preferredLinkList) {
            PreferredLinkDto toAdd = modelMapper.map(element, PreferredLinkDto.class);
            Teacher teacher = teacherService.findById(element.getTeacherId());
            toAdd.setTeacherName(teacher.getName());
            toAdd.setTeacherSurname(teacher.getSurname());
            toAdd.setFile(Base64.encodeBase64String(teacher.getImage()));
            list.add(toAdd);
        }

        model.addAttribute("user", userDto);
        model.addAttribute("preferredLinkList", list);
        model.addAttribute("token", request.getToken());
        return "user/linksPage";
    }

    @GetMapping("getChoosingLinkPage")
    public String getChoosingLinkPage(RequestDto request, PreferredLinkDto preferredLinkDto, Model model) throws IOException {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        Teacher teacher = teacherService.findById(preferredLinkDto.getTeacherId());
        Optional<String> linkOptional = preferredLinkService.findByUserIdAndTeacherId(userFounded.getId(), preferredLinkDto.getTeacherId());

        String preferredLink = null;
        if (linkOptional.isPresent() && !linkOptional.toString().isEmpty()) {
            preferredLink = linkOptional.get();
        }

        model.addAttribute("user", userDto);
        model.addAttribute("preferredLinkList", teacher.getContacts());
        model.addAttribute("preferredLink", preferredLink);
        model.addAttribute("teacherId", preferredLinkDto.getTeacherId());
        model.addAttribute("token", request.getToken());
        return "user/choosingLinkPage";
    }

    @PostMapping("setLink")
    public String setLink(RequestDto request, PreferredLinkDto preferredLinkDto, Model model) throws IOException {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        PreferredLink preferredLink = preferredLinkService.findElementByUserIdAndTeacherId(userFounded.getId(), preferredLinkDto.getTeacherId());
        preferredLink.setPreferredLink(preferredLinkDto.getLink());
        preferredLinkService.save(preferredLink);

        return "redirect:/user/getLinksPage?token=" + request.getToken();
    }

    @GetMapping("submitUserMaterialPage")
    public String submitUserMaterialPage(RequestDto request, LessonTemplate lesson, Model model) throws IOException {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        Long languageId = lesson.getLanguageId();

        List<UserMaterial> userMaterialList = userMaterialService.findAllByUserId(userFounded.getId());
        userMaterialList.removeIf(material -> !Objects.equals(material.getLanguageId(), languageId));
        List<Long> materialsIds = userHelper.getIdsFromUserMaterialsList(userMaterialList);
        List<Long> teacherMaterialIds = teacherMaterialService.findMaterialsByTeacherId(lesson.getTeacherId());

        materialsIds.retainAll(teacherMaterialIds);
        List<Material> materials = materialsService.findAllById(materialsIds);
        List<MaterialDtoBytes> materialsDtoList = languageService.getMaterialDtoBytesFromList(materials);

        model.addAttribute("user", userDto);
        model.addAttribute("day", lesson.getDay());
        model.addAttribute("time", lesson.getTime());
        model.addAttribute("languageId", languageId);
        model.addAttribute("materials", materialsDtoList);
        model.addAttribute("languageSubName", languageService.findById(languageId).getSubName());
        model.addAttribute("token", request.getToken());
        return "user/lessonMaterialsPage";
    }

    @GetMapping("dailyFact")
    public String getDailyFact(RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        userFounded = userService.setDailyDateAndId(userFounded);

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
    }

    @GetMapping("getCreatureLanguageList")
    public String getCreatureLanguageList(@ModelAttribute("request") RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        List<Lesson> userLessons = lessonService.findAllByUserId(userFounded.getId());
        List<Language> languages = languageService.getLanguagesFromUserLessons(userLessons);

        model.addAttribute("user", userDto);
        model.addAttribute("languages", languages);
        model.addAttribute("token", request.getToken());
        return "user/choosingLanguagePageCreature";
    }

    @GetMapping("submitChooseLanguageCreatureForm")
    public String getCreatureGuessPage(LessonTemplate languageSelected, RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

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
    }

    @GetMapping("getHint")
    public String getHint(RequestDto request, CreatureToGuess creatureToGuess, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        Creature creature = creatureService.findById(creatureToGuess.getId());
        CreatureToGuess newCreatureToGuess = languageService.getNewCreatureToGuess(creature, creatureToGuess);

        model.addAttribute("user", userDto);
        model.addAttribute("creature", newCreatureToGuess);
        model.addAttribute("token", request.getToken());
        return "user/creatureGuessingPage";
    }

    @PostMapping("giveUp")
    public String giveUp(RequestDto request, CreatureToGuess creatureToGuess, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        userFounded.setLastGuessedCount(0);
        userService.updateUser(userFounded);
        Creature creature = creatureService.findById(creatureToGuess.getId());
        String language = languageService.findById(creature.getLanguageId()).getName();
        CreatureDtoBytes creatureDtoBytes = creatureHelper.convertCreatureToDtoBytes(creature, language);

        model.addAttribute("user", userDto);
        model.addAttribute("creature", creatureDtoBytes);
        model.addAttribute("token", request.getToken());
        return "user/creatureAnswerPage";
    }

    @PostMapping("checkCreatureAnswerForm")
    public String checkCreatureAnswer(RequestDto request, CreatureToGuess creatureAnswered, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        userFounded = userService.defaultScore(userFounded);
        Creature creature = creatureService.findById(creatureAnswered.getId());

        if (creature.getName().equalsIgnoreCase(creatureAnswered.getName())) {
            userService.updateUserIfGuessed(userFounded);
            String language = languageService.findById(creature.getLanguageId()).getName();

            model.addAttribute("user", userDto);
            model.addAttribute("creature", creatureHelper.convertCreatureToDtoBytes(creature, language));
            model.addAttribute("token", request.getToken());
            return "user/creatureAnswerPage";
        } else {
            CreatureToGuess newCreatureToGuess = creatureHelper.getCreatureIfAnsweredIncorrect(
                    creature, creatureAnswered, languageService.findById(creature.getLanguageId()).getName());

            model.addAttribute("errorText", "Ви не вгадали. Спробуйте ще раз!");
            model.addAttribute("user", userDto);
            model.addAttribute("creature", newCreatureToGuess);
            model.addAttribute("token", request.getToken());
            return "user/creatureGuessingPage";
        }
    }

    @GetMapping("/getMaterialsLanguageList")
    public String getMaterialsLanguageForm(RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        List<Lesson> userLessons = lessonService.findAllByUserId(userFounded.getId());
        List<Language> languages = languageService.getLanguagesFromUserLessons(userLessons);

        model.addAttribute("user", userDto);
        model.addAttribute("languages", languages);
        model.addAttribute("token", request.getToken());
        return "user/choosingMaterialsLanguagePage";
    }

    @GetMapping("/submitChooseLanguageMaterialsForm")
    public String getMaterialsPage(RequestDto request, LessonTemplate lessonTemplate, Model model) throws IOException {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        Long languageId = lessonTemplate.getLanguageId();

        List<UserMaterial> userMaterialList = userMaterialService.findAllByUserId(userFounded.getId());
        userMaterialList.removeIf(material -> !Objects.equals(material.getLanguageId(), languageId));
        List<Long> materialsIds = userHelper.getIdsFromUserMaterialsList(userMaterialList);
        List<Material> materials = materialsService.findAllById(materialsIds);
        List<MaterialDtoBytes> materialsDtoList = languageService.getMaterialDtoBytesFromList(materials);

        model.addAttribute("user", userDto);
        model.addAttribute("languageId", languageId);
        model.addAttribute("materials", materialsDtoList);
        model.addAttribute("languageSubName", languageService.findById(languageId).getSubName());
        model.addAttribute("token", request.getToken());
        return "user/materialsPage";
    }

    @GetMapping("/personalInformation")
    public String getPersonalInformationForm(RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);

        UserDtoBytes userDto = userHelper.getUserDtoBytesWithDate(userFounded);

        model.addAttribute("user", userDto);
        model.addAttribute("token", request.getToken());
        return "user/personalInformationPage";
    }

    @PostMapping("/updatePersonalInformation")
    public String updatePersonalInformation(RequestDto request, UserDtoForm userUpdated, Model model) throws IOException {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);

        User user = userHelper.convertUserDtoFormToUser(userUpdated, userFounded);

        Object[] validation = userService.validateUser(user);
        if (!(boolean) validation[0]) {
            UserDtoBytes userDto = userHelper.getUserDtoBytesWithDate(userFounded);
            model.addAttribute("user", userDto);
            model.addAttribute("errorText", validation[1].toString().replaceAll("Optional\\[|\\]", ""));
            model.addAttribute("token", request.getToken());
            return "user/personalInformationPage";
        }

        User userTest = userService.findByEmail(user.getEmail()).orElse(null);
        if (userTest != null && !Objects.equals(userTest.getId(), user.getId())) {
            UserDtoBytes userDto = userHelper.getUserDtoBytesWithDate(userFounded);
            model.addAttribute("user", userDto);
            model.addAttribute("errorText", "Таку електронну пошту вже зайнято!");
            model.addAttribute("token", request.getToken());
            return "user/personalInformationPage";
        }

        if (!user.getContacts().isEmpty()) {
            List<String> newContacts = new ArrayList<>();
            for (String link : user.getContacts()
            ) {
                if (!link.equals("")) {
                    newContacts.add(link);
                }
            }
            user.setContacts(newContacts);
        }
        userService.updateUser(user);

        return "redirect:/user/userPage?token=" + request.getToken();
    }

    @GetMapping("/getTestPage")
    public String getTestPage(RequestDto request, Long languageId, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        model.addAttribute("languageId", languageId);
        model.addAttribute("user", userDto);
        model.addAttribute("token", request.getToken());
        return "user/testPage";
    }

    @GetMapping("/paymentInformation")
    public String getPaymentInfoPage(RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        Payment userPayment = paymentService.findByUserId(userFounded.getId());
        if (userPayment == null) {
            userPayment = new Payment();
            userPayment.setUserId(userFounded.getId());
            userPayment.setMoney(0);
            paymentService.save(userPayment);
        }

        model.addAttribute("payment", userPayment);
        model.addAttribute("user", userDto);
        model.addAttribute("token", request.getToken());
        return "payment/paymentInfoPage";
    }

    @GetMapping("/getGradesList")
    public String getGradesList(RequestDto request, Model model) {
        return "redirect:/grade/getGradesList?token=" + request.getToken();
    }

}
