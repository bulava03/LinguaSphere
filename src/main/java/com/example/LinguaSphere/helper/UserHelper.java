package com.example.LinguaSphere.helper;

import com.example.LinguaSphere.entity.Lesson;
import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.entity.UserMaterial;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserHelper {

    public String decodeToken(String token) {
        String[] segments = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(segments[1]));
    }

    public String formDate(User userFounded) {
        String day = String.valueOf(userFounded.getDateOfBirth().getDayOfMonth() + 1);
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

    String[] days = { "пн", "вт", "ср", "чт", "пт", "сб", "нд" };
    String[] times = { "08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00",
            "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00",
            "17:00-18:00", "18:00-19:00", "19:00-20:00", "20:00-21:00", "21:00-22:00", "22:00-23:00", "23:00-24:00" };

    public String convertIntIntoDate(int day, int time) {
        return days[day] + " " + times[time];
    }

    public List<Long> getIdsFromUserMaterialsList(List<UserMaterial> userMaterialList) {
        List<Long> list = new ArrayList<>();
        for (UserMaterial element : userMaterialList
             ) {
            list.add(element.getMaterialId());
        }
        return list;
    }

}
