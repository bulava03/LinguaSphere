package com.example.LinguaSphere.helper;

import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.CreatureToGuess;
import com.example.LinguaSphere.entity.dto.UserDtoBytes;
import com.example.LinguaSphere.entity.dto.UserDtoForm;
import com.example.LinguaSphere.service.DailyMessageService;
import com.example.LinguaSphere.service.LanguageService;
import com.example.LinguaSphere.service.impl.DailyMessageServiceImpl;
import com.example.LinguaSphere.service.impl.LanguageServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class UserHelper {

    private final ModelMapper modelMapper = new ModelMapper();

    public String decodeToken(String token) {
        String[] segments = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(segments[1]));
    }

    public String formDate(User userFounded) {
        String day = String.valueOf(userFounded.getDateOfBirth().getDayOfMonth());
        String month = ConvertHelper.monthToStringUserPage(userFounded.getDateOfBirth().getMonthValue());
        String year = String.valueOf(userFounded.getDateOfBirth().getYear());
        return day + " " + month + " " + year;
    }

    public int[][] getUsersLessons(List<Lesson> list) {
        int[][] lessons = new int[7][16];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                int finalI = i;
                int finalJ = j;
                if (list.stream().anyMatch(lesson -> (lesson.getDay() == finalI && lesson.getTime() == finalJ))) {
                    lessons[i][j] = 1;
                } else {
                    lessons[i][j] = 0;
                }
            }
        }
        return lessons;
    }

    public int[][] getTeachersSchedule(List<Lesson> teachersLessons, List<Lesson> usersLessons, List<Lesson> lessonsWithThisTeacher) {
        int[][] lessons = new int[7][16];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                lessons[i][j] = 0;
            }
        }

        for (Lesson lesson : usersLessons
        ) {
            if (lessonsWithThisTeacher.stream().anyMatch(element -> (element.getDay() == lesson.getDay() && element.getTime() == lesson.getTime()))) {
                lessons[lesson.getDay()][lesson.getTime()] = 2;
            }
        }

        for (Lesson lesson : teachersLessons
        ) {
            if (lessons[lesson.getDay()][lesson.getTime()] == 0) {
                lessons[lesson.getDay()][lesson.getTime()] = 1;
            }
        }

        return lessons;
    }

    public List<Long> getIdsFromUserMaterialsList(List<UserMaterial> userMaterialList) {
        List<Long> list = new ArrayList<>();
        for (UserMaterial element : userMaterialList
             ) {
            list.add(element.getMaterialId());
        }
        return list;
    }

    public List<Long> getLanguageIdsFromLessons(List<Lesson> userLessons) {
        List<Long> languagesIds = new ArrayList<>();
        for (Lesson lesson : userLessons
        ) {
            if (!languagesIds.contains(lesson.getLanguageId())) {
                languagesIds.add(lesson.getLanguageId());
            }
        }
        return languagesIds;
    }

    public UserDtoBytes getUserDtoBytes(User user) {
        UserDtoBytes userDto = modelMapper.map(user, UserDtoBytes.class);
        userDto.setDateOfBirth(formDate(user));
        userDto.setFile(org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(user.getImage()));
        return userDto;
    }

    public UserDtoBytes getUserDtoBytesWithDate(User user) {
        UserDtoBytes userDto = getUserDtoBytes(user);
        userDto.setDay(user.getDateOfBirth().getDayOfMonth());
        userDto.setMonth(ConvertHelper.monthToString(user.getDateOfBirth().getMonthValue()));
        userDto.setYear(user.getDateOfBirth().getYear());
        return userDto;
    }

    public User convertUserDtoFormToUser(UserDtoForm userDtoForm, User userFounded) throws IOException {
        User user = modelMapper.map(userDtoForm, User.class);
        user.setId(userFounded.getId());
        user.setPassword(userFounded.getPassword());
        user.setScore(userFounded.getScore());
        user.setDailyId(userFounded.getDailyId());
        user.setLastGuessedCount(userFounded.getLastGuessedCount());
        user.setNewDailyDate(userFounded.getNewDailyDate());
        user.setDateOfBirth(LocalDateTime.of(
                userDtoForm.getYear(), userDtoForm.getMonth(), userDtoForm.getDay(), 0, 0, 0));
        user.setImage(userDtoForm.getFile().getBytes());
        return user;
    }

}
