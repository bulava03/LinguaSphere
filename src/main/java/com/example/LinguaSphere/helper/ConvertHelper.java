package com.example.LinguaSphere.helper;

import java.util.HashMap;
import java.util.Map;

public class ConvertHelper {

    private static final Map<Integer, String> months = new HashMap<>();
    private static final Map<Integer, String> monthsForAside = new HashMap<>();

    static {
        months.put(1, "Січень");
        months.put(2, "Лютий");
        months.put(3, "Березень");
        months.put(4, "Квітень");
        months.put(5, "Травень");
        months.put(6, "Червень");
        months.put(7, "Липень");
        months.put(8, "Серпень");
        months.put(9, "Вересень");
        months.put(10, "Жовтень");
        months.put(11, "Листопад");
        months.put(12, "Грудень");

        monthsForAside.put(1, "січня");
        monthsForAside.put(2, "лютого");
        monthsForAside.put(3, "березня");
        monthsForAside.put(4, "квітня");
        monthsForAside.put(5, "травня");
        monthsForAside.put(6, "червня");
        monthsForAside.put(7, "липня");
        monthsForAside.put(8, "серпня");
        monthsForAside.put(9, "вересня");
        monthsForAside.put(10, "жовтня");
        monthsForAside.put(11, "листопада");
        monthsForAside.put(12, "грудня");
    }

    public static String monthToString(int month) {
        if (months.containsKey(month)) {
            return months.get(month);
        } else {
            throw new IllegalArgumentException("Невірне значення місяця.");
        }
    }

    public static String monthToStringUserPage(int month) {
        if (monthsForAside.containsKey(month)) {
            return monthsForAside.get(month);
        } else {
            throw new IllegalArgumentException("Невірне значення місяця.");
        }
    }

}
