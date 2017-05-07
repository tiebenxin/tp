package com.example.haoyuban111.mubanapplication.help_class;

import android.text.TextUtils;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringHelper {

    public static final long KB = 1024;
    public static final long MB = KB * 1024;
    public static final long GB = MB * 1024;
    public static final long TB = GB * 1024;

    public static final String EMPTY = "";

    public static <T> StringBuilder join(List<T> items, IConvert<T, String> converter, String delimiter) {
        StringBuilder builder = new StringBuilder();
        boolean firstTime = true;
        for (T token : items) {
            if (firstTime) {
                firstTime = false;
            } else {
                builder.append(delimiter);
            }
            builder.append(converter.convert(token));
        }
        return builder;
    }

    public static <T> StringBuilder join(List<T> items, String delimiter) {
        StringBuilder builder = new StringBuilder();
        boolean firstTime = true;
        for (T item : items) {
            if (firstTime) {
                firstTime = false;
            } else {
                builder.append(delimiter);
            }
            builder.append(item.toString());
        }
        return builder;
    }

    public static boolean matches(String regex, String value) {
        return Pattern.matches(regex, value);
    }

    public static String toReadableMb(long kbSize) {
        long mdSize = kbSize / MB;
        return String.valueOf(mdSize == 0 ? 1 : mdSize);
    }

    public static boolean isEmpty(CharSequence x) {
        return TextUtils.isEmpty(x);
    }

    public static String lowerCase(String x) {
        return x == null ? x : x.toLowerCase();
    }

    public static String lowerCase(String x, Locale locale) {
        return x == null ? x : x.toLowerCase(locale);
    }

    public static String capitalize(String x) {
        return capitalize(x, null);
    }

    public static String capitalize(final String str, final char... delimiters) {
        final int delimLen = delimiters == null ? -1 : delimiters.length;
        if (isEmpty(str) || delimLen == 0) {
            return str;
        }
        final char[] buffer = str.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < buffer.length; i++) {
            final char ch = buffer[i];
            if (isDelimiter(ch, delimiters)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer[i] = Character.toTitleCase(ch);
                capitalizeNext = false;
            }
        }
        return new String(buffer);
    }

    private static boolean isDelimiter(final char ch, final char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        }
        for (final char delimiter : delimiters) {
            if (ch == delimiter) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSameStrings(String text1, String text2) {
        boolean isEmpty;
        if ((isEmpty = TextUtils.isEmpty(text1)) == TextUtils.isEmpty(text2)) {
            return isEmpty || text1.equals(text2);
        }
        return false;
    }
}
