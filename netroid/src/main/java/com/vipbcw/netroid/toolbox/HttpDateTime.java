//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vipbcw.netroid.toolbox;

import android.text.format.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpDateTime {
    private static final String HTTP_DATE_RFC_REGEXP = "([0-9]{1,2})[- ]([A-Za-z]{3,9})[- ]([0-9]{2,4})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])";
    private static final String HTTP_DATE_ANSIC_REGEXP = "[ ]([A-Za-z]{3,9})[ ]+([0-9]{1,2})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])[ ]([0-9]{2,4})";
    private static final Pattern HTTP_DATE_RFC_PATTERN = Pattern.compile("([0-9]{1,2})[- ]([A-Za-z]{3,9})[- ]([0-9]{2,4})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])");
    private static final Pattern HTTP_DATE_ANSIC_PATTERN = Pattern.compile("[ ]([A-Za-z]{3,9})[ ]+([0-9]{1,2})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])[ ]([0-9]{2,4})");

    public HttpDateTime() {
    }

    public static long parse(String timeString) throws IllegalArgumentException {
        boolean date = true;
        boolean month = false;
        boolean year = true;
        Matcher rfcMatcher = HTTP_DATE_RFC_PATTERN.matcher(timeString);
        HttpDateTime.TimeOfDay timeOfDay;
        int date1;
        int month1;
        int year1;
        if(rfcMatcher.find()) {
            date1 = getDate(rfcMatcher.group(1));
            month1 = getMonth(rfcMatcher.group(2));
            year1 = getYear(rfcMatcher.group(3));
            timeOfDay = getTime(rfcMatcher.group(4));
        } else {
            Matcher time = HTTP_DATE_ANSIC_PATTERN.matcher(timeString);
            if(!time.find()) {
                throw new IllegalArgumentException();
            }

            month1 = getMonth(time.group(1));
            date1 = getDate(time.group(2));
            timeOfDay = getTime(time.group(3));
            year1 = getYear(time.group(4));
        }

        if(year1 >= 2038) {
            year1 = 2038;
            month1 = 0;
            date1 = 1;
        }

        Time time1 = new Time("UTC");
        time1.set(timeOfDay.second, timeOfDay.minute, timeOfDay.hour, date1, month1, year1);
        return time1.toMillis(false);
    }

    private static int getDate(String dateString) {
        return dateString.length() == 2?(dateString.charAt(0) - 48) * 10 + (dateString.charAt(1) - 48):dateString.charAt(0) - 48;
    }

    private static int getMonth(String monthString) {
        int hash = Character.toLowerCase(monthString.charAt(0)) + Character.toLowerCase(monthString.charAt(1)) + Character.toLowerCase(monthString.charAt(2)) - 291;
        switch(hash) {
        case 9:
            return 11;
        case 10:
            return 1;
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 23:
        case 24:
        case 25:
        case 27:
        case 28:
        case 30:
        case 31:
        case 33:
        case 34:
        case 38:
        case 39:
        case 41:
        case 43:
        case 44:
        case 45:
        case 46:
        case 47:
        default:
            throw new IllegalArgumentException();
        case 22:
            return 0;
        case 26:
            return 7;
        case 29:
            return 2;
        case 32:
            return 3;
        case 35:
            return 9;
        case 36:
            return 4;
        case 37:
            return 8;
        case 40:
            return 6;
        case 42:
            return 5;
        case 48:
            return 10;
        }
    }

    private static int getYear(String yearString) {
        int year;
        if(yearString.length() == 2) {
            year = (yearString.charAt(0) - 48) * 10 + (yearString.charAt(1) - 48);
            return year >= 70?year + 1900:year + 2000;
        } else if(yearString.length() == 3) {
            year = (yearString.charAt(0) - 48) * 100 + (yearString.charAt(1) - 48) * 10 + (yearString.charAt(2) - 48);
            return year + 1900;
        } else {
            return yearString.length() == 4?(yearString.charAt(0) - 48) * 1000 + (yearString.charAt(1) - 48) * 100 + (yearString.charAt(2) - 48) * 10 + (yearString.charAt(3) - 48):1970;
        }
    }

    private static HttpDateTime.TimeOfDay getTime(String timeString) {
        byte i = 0;
        int var5 = i + 1;
        int hour = timeString.charAt(i) - 48;
        if(timeString.charAt(var5) != 58) {
            hour = hour * 10 + (timeString.charAt(var5++) - 48);
        }

        ++var5;
        int minute = (timeString.charAt(var5++) - 48) * 10 + (timeString.charAt(var5++) - 48);
        ++var5;
        int second = (timeString.charAt(var5++) - 48) * 10 + (timeString.charAt(var5++) - 48);
        return new HttpDateTime.TimeOfDay(hour, minute, second);
    }

    private static class TimeOfDay {
        int hour;
        int minute;
        int second;

        TimeOfDay(int h, int m, int s) {
            this.hour = h;
            this.minute = m;
            this.second = s;
        }
    }
}
