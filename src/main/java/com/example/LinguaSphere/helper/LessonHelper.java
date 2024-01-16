package com.example.LinguaSphere.helper;

import com.example.LinguaSphere.entity.Lesson;

import java.util.List;

public class LessonHelper {

    public int findLessonByDate(int day, int time, List<Lesson> list) {
        for (Lesson lesson : list
             ) {
            if (lesson.getDay() == day && lesson.getTime() == time) {
                if (lesson.getUserId() != null) {
                    return 2;
                } else {
                    return 1;
                }
            }
        }
        return 0;
    }

}
