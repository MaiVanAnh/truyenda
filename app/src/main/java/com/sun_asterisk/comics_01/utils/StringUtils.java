package com.sun_asterisk.comics_01.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StringUtils {
    public final static String DIVISION_CHARACTER = "T";
    public final static String SEPARATE_COMMA = ",";
    public final static String SEPARATE_SPACE = " ";
    public final static String SEPARATE_DOLLAR = "@@";

    public static String formatDate(String date) {
        int endIndex = date.indexOf(DIVISION_CHARACTER);
        return date.substring(0, endIndex);
    }

    public static List<String> formatStrToStrUrls(String data) {
        String[] arrayStrUrls = data.split(SEPARATE_COMMA);
        return Arrays.asList(arrayStrUrls);
    }

    public static Boolean isCorrectSize(String data) {
        int size = data.length();
        if (size < 8 || size > 20) return false;
        return true;
    }

    public static Boolean isCorrectFormatPassword(String data) {
        int charCount = 0;
        int numCount = 0;
        if (!isCorrectSize(data)) {
            return false;
        } else {
            for (int i = 0; i < data.length(); i++) {
                char ch = data.charAt(i);
                if (Character.isLetter(ch)) {
                    charCount++;
                } else if (Character.isDigit(ch)) {
                    numCount++;
                } else {
                    return false;
                }
            }
        }
        return (charCount >= 1 && numCount >= 1);
    }

    public static Calendar parseStringToCalendar(String data) {
        StringBuilder str = new StringBuilder();
        String[] split = data.split(DIVISION_CHARACTER);
        for (String s : split) {
            str.append(s).append(" ");
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        try {
            calendar.setTime(sdf.parse(str.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
