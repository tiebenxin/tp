package com.example.haoyuban111.mubanapplication.help_class;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class LanguageHelper {

    public enum ELanguagePermission {
        NONE,
        DEVELOPER_MODE
    }

    public static final String LOCALE_EN = "en";
    public static final String LOCALE_ZH_CN = "zh-CN";
    public static final String LOCALE_ZH_TW = "zh-TW";
    public static final String LOCALE_RU = "ru";

    protected static final List<Locale> CHINESE_SIMPLIFIED;
    protected static final List<Locale> CHINESE_TRADITIONAL;
    protected static final HashSet<String> RUSSIAN;

    static {
        CHINESE_SIMPLIFIED = new ArrayList<Locale>();
        CHINESE_SIMPLIFIED.add(Locale.CHINA);
        CHINESE_SIMPLIFIED.add(Locale.CHINESE);
        CHINESE_SIMPLIFIED.add(Locale.PRC);
        CHINESE_SIMPLIFIED.add(Locale.SIMPLIFIED_CHINESE);

        CHINESE_TRADITIONAL = new ArrayList<Locale>();
        CHINESE_TRADITIONAL.add(Locale.TAIWAN);
        CHINESE_TRADITIONAL.add(Locale.TRADITIONAL_CHINESE);

        RUSSIAN = new HashSet<String>();
        RUSSIAN.add("uk");
        RUSSIAN.add("be");
        RUSSIAN.add("ru");
    }

    public static String getDefaultPhrasebookLanguage() {
        return LOCALE_EN;
    }

    public static String getDefaultLanguage() {
        return LOCALE_EN;
    }

    public static String getDeviceLocale() {
        return Locale.getDefault().toString();
    }

    protected static Locale validateLocale(String locale) {
        Locale result;
        if (!StringHelper.isEmpty(locale)) {
            if (locale.compareTo(LOCALE_ZH_CN) == 0) {
                result = Locale.SIMPLIFIED_CHINESE;
            } else if (locale.compareTo(LOCALE_ZH_TW) == 0) {
                result = Locale.TRADITIONAL_CHINESE;
            } else {
                result = new Locale(locale);
            }
        } else {
            result = Locale.getDefault();
        }
        return result;
    }
}
