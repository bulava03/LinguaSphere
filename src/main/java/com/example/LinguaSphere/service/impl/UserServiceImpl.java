package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.DailyMessage;
import com.example.LinguaSphere.entity.Lesson;
import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.entity.dto.RequestDto;
import com.example.LinguaSphere.helper.UserHelper;
import com.example.LinguaSphere.repository.UserRepository;
import com.example.LinguaSphere.service.DailyMessageService;
import com.example.LinguaSphere.service.LessonService;
import com.example.LinguaSphere.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DailyMessageService dailyMessageService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Validator validator;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserHelper userHelper = new UserHelper();


    @Override
    public Object[] validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage).toList();
            return new Object[] { false, errorMessages.stream().findFirst() };
        } else {
            return new Object[] { true, "" };
        }
    }

    @Override
    public Object[] authenticateUser(RequestDto requestDto) {
        String payload = userHelper.decodeToken(requestDto.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            return new Object[] { false, "authorisation/authorisation" };
        }

        User userFounded = findByEmail(username).orElse(null);
        if (userFounded == null) {
            return new Object[] { false, "authorisation/authorisation" };
        }

        return new Object[] { true, username };
    }

    @Override
    public void addUser(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User defaultScore(User user) {
        if (user.getScore() == null) {
            user.setScore(0L);
            updateUser(user);
        }
        return user;
    }

    @Override
    public void updateUserIfGuessed(User user) {
        user.setLastGuessedCount(user.getLastGuessedCount() + 1);
        user.setScore(user.getScore() + 1);
        if (user.getLastGuessedCount() == 10) {
            user.setLastGuessedCount(0);
            user.setScore(user.getScore() + 10);
        }
        updateUser(user);
    }

    public User setNewDailyDate(User user) {
        if (user.getNewDailyDate() == null) {
            user.setNewDailyDate(LocalDateTime.now().with(LocalTime.MIN).plusDays(1));
            updateUser(user);
        }

        if (LocalDateTime.now().isAfter(user.getNewDailyDate())) {
            user.setDailyId(null);
            user.setNewDailyDate(LocalDateTime.now().with(LocalTime.MIN).plusDays(1));
            updateUser(user);
        }

        return user;
    }

    public User setNewDaily(User user) {
        if (user.getDailyId() == null || dailyMessageService.findById(user.getDailyId()) == null) {
            List<Lesson> lessons = lessonService.findAllByUserId(user.getId());
            List<DailyMessage> variants = dailyMessageService.getDailyMessagesFromUserLessons(lessons);
            if (variants.size() > 0) {
                Random random = new Random();
                int factNumber = random.nextInt(variants.size());
                DailyMessage dailyMessage = variants.get(factNumber);
                user.setDailyId(dailyMessage.getId());
                user.setNewDailyDate(LocalDateTime.now().with(LocalTime.MIN).plusDays(1));
                updateUser(user);
            }
        }
        return user;
    }

    @Override
    public User setDailyDateAndId(User user) {
        user = setNewDailyDate(user);
        return setNewDaily(user);
    }

}
