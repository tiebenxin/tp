package com.example.haoyuban111.mubanapplication.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.help_class.ContextHelper;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by haoyuban111 on 2016/5/19.
 */
public class DateDFUtils {
    static String date;
    static SimpleDateFormat format;
    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    private static final long DAYMS = 86400000L;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long WEEK = 7 * DAY;
    public static final long YEAR = 365 * DAY;
    public static final String LONGFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORTFORMAT = "yyyy-MM-dd";
    public static final double MONTH = YEAR / 12;

    public static List getDataYearMonth(String data) {
        ArrayList<String> list = new ArrayList<String>();
        if (null == data || "".equals(data)) {
            //list = null;
            return list;
        } else {
            String sr = data.trim();
            String[] s = sr.split("-");
            for (int i = 0; i < s.length; i++) {
                String str = s[i];
                list.add(str);
            }
        }
        return list;
    }

    public static List getDataVersion(String data) {
        ArrayList<String> list = new ArrayList<String>();
        if (null == data || "".equals(data)) {
            //list = null;
            return list;
        } else {
            String sr = data.trim();
            String[] s = sr.split(".");
            for (int i = 0; i < s.length; i++) {
                String str = s[i];
                list.add(str);
            }
        }
        return list;
    }

    public static ArrayList<String> getSpileData(String data) {
        ArrayList<String> list = new ArrayList<String>();
        if (null == data || "".equals(data)) {
            return list;
        } else {
            String sr = data.trim();
            String[] s = sr.split(" ");
            for (int i = 0; i < s.length; i++) {
                String str = s[i];
                list.add(str);
            }
        }
        return list;
    }

//    public static String getDateDifferent(String nowTime, String previousTime) {
//        DateFormat df = new SimpleDateFormat(LONGFORMAT);
//        if ((null == nowTime || "".equals(nowTime)) || (null == previousTime || "".equals(previousTime))) {
//            return "";
//        } else {
//
//            try {
//                Date now = df.parse(nowTime);
//                Date previous = df.parse(previousTime);
//                if (null != now && null != previous) {
//                    return DateTimeHelper.getLastSeen(now, previous);
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//
//            return date;
//        }
//    }


    //last online
//    public static String getDiffTimeToNow(String date) {
//        if ("".equals(date) || null == date) {
//            return "";
//        } else {
//            format = new SimpleDateFormat(LONGFORMAT);
//
//            try {
//                Date datePast = format.parse(date);
//                long currentTime = System.currentTimeMillis();
//                Calendar cal = Calendar.getInstance();
//                TimeZone timeZone = cal.getTimeZone();
//                currentTime -= timeZone.getRawOffset();
//                Date now = new Date(currentTime);
//                if (null != datePast && null != now) {
//                    return DateTimeHelper.getLastSeen(now, datePast);
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        return "";
//
//    }

//    public static String getDiffTimeToNowT(String date) {
//        if ("".equals(date) || null == date) {
//            return "";
//        } else {
//            format = new SimpleDateFormat(LONGFORMAT);
//
//            try {
//                Date datePast = format.parse(date);
//                long currentTime = System.currentTimeMillis();
//                Calendar cal = Calendar.getInstance();
//                TimeZone timeZone = cal.getTimeZone();
//                currentTime -= timeZone.getRawOffset();
//                Date now = new Date(currentTime);
//                if (null != datePast && null != now) {
//                    return DateTimeHelper.getLastSeenT(now, datePast);
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        return "";
//
//    }

    public static String getLastTime(String date) {
        if ("".equals(date) || null == date) {
            return "";
        } else {
            format = new SimpleDateFormat(LONGFORMAT);

            try {
                Date datePast = format.parse(date);
                long currentTime = System.currentTimeMillis();
                Calendar cal = Calendar.getInstance();
                TimeZone timeZone = cal.getTimeZone();
                currentTime -= timeZone.getRawOffset();
                Date now = new Date(currentTime);
                if (null != datePast && null != now) {
                    return getLastSeenT(now, datePast);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";

    }

    public static String getLastSeenT(Date current, Date past) {
        long diff = current.getTime() - past.getTime();

        long year = diff / YEAR;
        long month = (long) (diff / MONTH);
        long week = diff / WEEK;
        long day = diff / DAY;
        long hour = diff / HOUR;
        long minute = diff / MINUTE;

        if (year > 0) {
            if (year == 1) {
                return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.year_ago), String.valueOf(1));
            }
            return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.years_ago), String.valueOf(year));
        } else if (month > 0) {
            if (month == 1) {
                return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.month_ago), String.valueOf(1));
            }
            return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.months_ago), String.valueOf(month));
        } else if (week > 0) {
            if (week == 1) {
                return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.week_ago), String.valueOf(1));
            }
            return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.weeks_ago), String.valueOf(week));
        } else if (day > 0) {
            if (day == 1) {
                return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.day_ago), String.valueOf(1));
            }
            return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.days_ago), String.valueOf(day));
        } else if (hour > 0) {
            if (hour == 1) {
                return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.hour_ago), String.valueOf(1));
            }
            return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.hours_ago), String.valueOf(hour));
        } else if (minute <= 4) {
//            return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.minute_ago), String.valueOf(2));
//            return ContextHelper.getString(R.string.online);
//            return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.minutes_ago), String.valueOf(minute));
            return ContextHelper.getString(R.string.online);
        } else {
            return String.format(Locale.ENGLISH, ContextHelper.getApplicationContext().getString(R.string.minutes_ago), String.valueOf(minute));
        }
    }

    public static String getSystemCurrentTime() {
        format = new SimpleDateFormat(LONGFORMAT);
        long currentTime = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        currentTime -= timeZone.getRawOffset();
        Date d = new Date(currentTime);
        return format.format(d);

    }

    public static String getDateString(String date) {
        format = new SimpleDateFormat("yyyy-DD-mm");
        Date d = new Date(date);
        return format.format(d);
    }

    public static String toLocalTime(Calendar calendar, TimeZone timeZone) {

        String result;
        SimpleDateFormat format;

        if (android.text.format.DateFormat.is24HourFormat(ContextHelper.getApplicationContext())) {
            format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(timeZone);
            result = format.format(calendar.getTime());
        } else {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            format = new SimpleDateFormat("h:mm");
            format.setTimeZone(timeZone);
            result = String.format("%s %s", format.format(calendar.getTime()), hour > 12 ? "PM" : "AM");
        }
        return result;

    }

//    public static void setLocaleTime(String timeOffSet, String timeZone, TextView tv) {
//        if (timeOffSet != null && !"".equals(timeOffSet)) {
//            tv.setText(getLocationTimeFromTimeOff(timeOffSet));
//        } else {
//            if (timeZone != null && !"".equals(timeZone)) {
//                tv.setText(getLocationTime(timeZone));
//            } else {
//                tv.setText("");
//            }
//        }
//    }

//    public static String getLocationTime(String timeZone) {
//        if (timeZone != null && !"".equals(timeZone)) {
//            int localTimeZone = Integer.parseInt(timeZone);
//            int timeOff = localTimeZone * 60 * 60;
//            TimeZone gmt = TimeZone.getTimeZone("GMT");
//            Calendar utc = Calendar.getInstance(gmt);
//            utc.add(Calendar.SECOND, timeOff);
//            return ChatTime.toLocalTime(utc, gmt);
//        }
//        return "";
//    }

//    public static String getLocationTimeFromTimeOff(String timeOffSet) {
//        if (timeOffSet != null && !"".equals(timeOffSet)) {
//            int timeoff = Integer.parseInt(timeOffSet);
//            TimeZone gmt = TimeZone.getTimeZone("GMT");
//            Calendar utc = Calendar.getInstance(gmt);
//            utc.add(Calendar.SECOND, timeoff);
//            return ChatTime.toLocalTime(utc, gmt);
//        }
//        return "";
//    }

//    public static String getLocationTime(String timeZone) {
//        if (timeZone == null || "".equals(timeZone)) {
//            return " ";
//        } else {
//            int localTimeZone = Integer.parseInt(timeZone);
//            if (localTimeZone > 13 || localTimeZone < -12) {
//                localTimeZone = 0;
//            }
//            String[] ids = TimeZone.getAvailableIDs(localTimeZone * 60 * 60 * 1000);
//            TimeZone locationTimeZone;
//            if (ids.length == 0) {
//                // if no ids were returned, something is wrong. use default TimeZone
//                locationTimeZone = TimeZone.getDefault();
//            } else {
//                locationTimeZone = new SimpleTimeZone(localTimeZone * 60 * 60 * 1000, ids[0]);
//            }
//            //TimeZone currentTimeZone = TimeZone.getDefault();
//            // Date currentDate = new Date(System.currentTimeMillis());
//            Calendar calendarCurrent = Calendar.getInstance();
//
//            return toLocalTime(calendarCurrent, locationTimeZone);
//        }
//    }

    /**
     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
     *
     * @param timeZoneOffset
     * @return
     */
    public String getFormatedDateString(int timeZoneOffset) {
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
        if (ids.length == 0) {
            // if no ids were returned, something is wrong. use default TimeZone
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    public static String getStayTime(String departureTime, String arravelTime) {
        String s;
        int days = -1;
        if ((null != arravelTime && null != departureTime) && !"".equals(arravelTime) && !"".equals(departureTime)) {
            DateFormat df = new SimpleDateFormat(LONGFORMAT);

            try {
                Date arravelDate = df.parse(arravelTime);
                Date leaveDate = df.parse(departureTime);
                long diff = leaveDate.getTime() - arravelDate.getTime();
                days = (int) (diff / DAYMS);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (days < 0) {
                s = "";
            } else {
                s = Integer.toString(days) + "";
            }
        } else {
            s = "";
        }


        return s;
    }

    /**
     * 计算当前页时间的过去时间的毫秒值
     *
     * @param timeNow
     * @param timeOld
     * @return
     */
    public static int getTimeDifference(String timeNow, String timeOld) {
        //1  null    2 小于   3    通过
        if (timeNow != null && !"".equals(timeNow) && timeOld != null && !"".equals(timeOld)) {
            DateFormat df = new SimpleDateFormat(SHORTFORMAT); //"yyyy-MM-dd"
            try {
                Date time_n = df.parse(timeNow);//现在时间     》 旧的时间
                Date time_o = df.parse(timeOld);
                if (time_n.getTime() - time_o.getTime() > 0) {//等于零说明是当天日期
                    return 2;
                } else {
                    return 3;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return 2;
            }

        } else {
            return 1;
        }

    }

    public static String getSplitDate(String date) {
        String mdata = "0000-00-00";
        if (date == null || "".equals(date) || date.contains(mdata)) {
            return "";
        } else {
            if (date.length() >= 11) {
                Locale locale = ContextHelper.getResources().getConfiguration().locale;
                date = getLocalDateFromUTC(date, locale);
                return date.substring(0, 10);
            } else {
                return date;
            }
        }
    }


    public static String getShowFormatDate(String date) {
        String mdata = "0000-00-00";
        if (date == null || "".equals(date) || date.contains(mdata)) {
            return "";
        } else {
            if (date.length() >= 11) {
                Locale locale = ContextHelper.getResources().getConfiguration().locale;
                date = getLocalDateFromUTC(date, locale);
                String preDate = date.substring(0, 10);
                if (preDate != null) {
                    String[] arr = preDate.split("-");
                    StringBuffer s = new StringBuffer();
                    if (locale == Locale.CHINA || locale == Locale.JAPAN || locale == Locale.KOREA || locale == Locale.CHINESE
                            || locale == Locale.TAIWAN || locale == Locale.SIMPLIFIED_CHINESE || locale == Locale.TRADITIONAL_CHINESE) {
                        return preDate;
                    } else if (locale == Locale.US) {
                        s.append(arr[1]).append("-").append(arr[2]).append("-").append(arr[0]);//美食
                        return s.toString();
                    } else {
                        s.append(arr[2]).append("-").append(arr[1]).append("-").append(arr[0]);  //英式
                        return s.toString();
                    }
                }
                return date.substring(0, 10);
            } else {
                return "";
            }
        }
    }

//    public static String ShowFormatDate(String time) {
//        String mData;
//        if (time != null && !"".equals(time)) {
//            Locale locale = ContextHelper.getResources().getConfiguration().locale;
//            mData = getLocalDateFromUTC(time, locale);
//            List list = ServiceUtils.splitEmptyTime(mData);
//
//            if (mData != null) {
//                if (locale == Locale.CHINA || locale == Locale.JAPAN || locale == Locale.KOREA || locale == Locale.CHINESE
//                        || locale == Locale.TAIWAN || locale == Locale.SIMPLIFIED_CHINESE || locale == Locale.TRADITIONAL_CHINESE) {
//                    //中式
//                    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    try {
//                        Date time_n = myFormatter.parse(mData);
//                        return myFormatter.format(time_n);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                } else if (locale == Locale.US) {
//                    if (list != null && list.size() == 2) {
//                        String mT = (String) list.get(0);
//                        String dT = (String) list.get(1);
//                        String[] arr = mT.split("-");
//                        return arr[1] + "-" + arr[2] + "-" + "-" + arr[0] + " " + dT;
//                    }
//
//                } else {
//                    if (list != null && list.size() == 2) {
//                        String mT = (String) list.get(0);
//                        String dT = (String) list.get(1);
//                        String[] arr = mT.split("-");
//                        return arr[1] + "-" + arr[2] + "-" + arr[0] + " " + dT;
//                    }
//                }
//            }
//        }
//        return null;
//    }

    public static String getLongDate(String date) {
        String mdata = "0000-00-00";
        if (date == null || "".equals(date) || date.contains(mdata)) {
            return "";
        } else {
            Date mDate = null;
            try {
                SimpleDateFormat format;
                if (date.length() > mdata.length()) {
                    format = createUTC(LONGFORMAT);
                } else {
                    format = createUTC(SHORTFORMAT);
                }
                mDate = format.parse(date);
                Locale locale = ContextHelper.getResources().getConfiguration().locale;
                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
                return df.format(mDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return "";

    }

    public static SimpleDateFormat createUTC(String format) {
        Locale locale = Locale.getDefault();
        SimpleDateFormat result = new SimpleDateFormat(format, locale);
        result.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
        return result;
    }


//    public static String getAgeByBirthDay(String birthday) {
//        DateFormat df = null;
//        if (birthday != null && !"".equals(birthday)) {
//            try {
//                df = new SimpleDateFormat(SHORTFORMAT);
//                Date first = df.parse(birthday);
//                Date last = Calendar.getInstance().getTime();
//                return DateTimeHelper.getDiffYears(first, last) + "";
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        return "";
//    }


    public static Date getDate(String s) {
        DateFormat df = new SimpleDateFormat(SHORTFORMAT);
        Date date;
        if (null == s) {
            date = null;
        } else {

            try {
                date = df.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
                date = null;
            }
        }
        return date;
    }

    public static String getDepartureDate(String arrivalDate, String day) {
        String time = "";
        if (null != arrivalDate && !"".equals(arrivalDate) && null != day && !"".equals(day) && !ContextHelper.getString(R.string.select).equals(day) && !ContextHelper.getString(R.string.selected).equals(day)) {
            SimpleDateFormat df = new SimpleDateFormat(SHORTFORMAT);
            Date date;
            try {
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                date = df.parse(arrivalDate);
                long arrival = date.getTime();
                long daytime = Integer.parseInt(day) * 1000 * 60 * 60 * 24L;
                long departuretime = arrival + daytime;
                Date departure = new Date(departuretime);
                time = df.format(departure);

            } catch (ParseException e) {
                e.printStackTrace();
                time = "";
            }
        }
        return time;
    }

    //获取当前时间
    public static String getNowTime() {
        Calendar data = Calendar.getInstance();
        int Year = data.get(Calendar.YEAR);
        int monthOfYear = data.get(Calendar.MONTH) + 1;
        int dayOfMonth = data.get(Calendar.DATE);
        return Year + "-" + monthOfYear + "-" + dayOfMonth;
    }

    public static Boolean mGetSharedPreferences(Context context, String flag) {
        SharedPreferences sp = context.getSharedPreferences("mdata", context.MODE_PRIVATE);
        return sp.getBoolean(flag, false);
    }


    //本地时间转化成UTC时间
    public static String getUTCDate(String date) {
        if (null != date && !"".equals(date)) {
            DateFormat df = new SimpleDateFormat(LONGFORMAT);
            if (date.length() <= 10) {
                date = date.trim() + " 00:00:00";
            }

            if (null != df) {
                try {
                    Date mDate = df.parse(date);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(mDate);
                    // 2、取得时间偏移量：
                    int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
                    // 3、取得夏令时差：
                    int dstOffset = calendar.get(Calendar.DST_OFFSET);
                    // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
                    calendar.add(Calendar.MILLISECOND, (zoneOffset + dstOffset));
                    return df.format(calendar.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";

    }

    //UTC时间转变为本地时间??
    public static String getLocalDateFromUTC(String date, Locale locale) {
        if (null != date && !"".equals(date)) {
            try {
                DateFormat df = new SimpleDateFormat(LONGFORMAT);
                Date mDate = df.parse(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mDate);
                // 2、取得时间偏移量：
                int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
                // 3、取得夏令时差：
                int dstOffset = calendar.get(Calendar.DST_OFFSET);
                // 4、从本地时间里加上这些差量，即可以取得UTC时间：
                calendar.add(Calendar.MILLISECOND, (zoneOffset + dstOffset));
                return df.format(calendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String checkHostAndTravel(String hostDate, String travelDate) {
        format = new SimpleDateFormat(LONGFORMAT);
        String s = "0";
        if (null != hostDate && !"".equals(hostDate) && null != travelDate && !"".equals(travelDate)) {
            try {
                Date host = format.parse(hostDate);
                Date travel = format.parse(travelDate);
                long diff = host.getTime() - travel.getTime();
                if (diff > 0) {
                    s = "0";//host
                } else if (diff < 0) {
                    s = "1";//travel
                } else {
                    s = "0";//优先host
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return s;
    }


    //获取系统时间格式
    public static String getSystemFormatTime(String time) {
        if (null != time && !"".equals(time)) {
            DateFormat dateFormat = new SimpleDateFormat(SHORTFORMAT);
            Date date;
            try {
                date = dateFormat.parse(time);
                if (date != null) {
                    Locale locale = ContextHelper.getResources().getConfiguration().locale;
                    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
                    return df.format(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }
        return "";
    }

    //获取系统时间格式
//    public static String getStandardFormatTime(String time) {
//        if (null != time && !"".equals(time)) {
//            Locale locale = com.hellopal.android.common.help_classes.ContextHelper.getResources().getConfiguration().locale;
//            DateFormat dateFormat = new SimpleDateFormat(LONGFORMAT,locale);
//            Date date;
//            try {
//                date = dateFormat.parse(time);
//                if (date != null) {
//                    DateFormat format = new SimpleDateFormat(LONGFORMAT);
//                    return format.format(date);
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//
//            }
//        }
//        return "";
//    }

    //获取系统时间格式
    public static String getSystemFormatTitleTime(String time) {
        if (null != time && !"".equals(time)) {
            DateFormat dateFormat = new SimpleDateFormat(SHORTFORMAT);
            Date date;
            try {
                date = dateFormat.parse(time);
                if (date != null) {
                    Locale locale = ContextHelper.getResources().getConfiguration().locale;
                    DateFormat df;
                    if (locale.getLanguage().equals(Locale.CHINA.getLanguage()) || locale.getLanguage().equals(Locale.TAIWAN.getLanguage()) || locale.getLanguage().equals(Locale.KOREA.getLanguage())
                            || locale.getLanguage().equals(Locale.JAPAN.getLanguage())) {
                        df = new SimpleDateFormat("y-MM", DateFormatSymbols.getInstance(locale));
                    } else {
                        df = new SimpleDateFormat("MMM y", DateFormatSymbols.getInstance(locale));
                    }
                    return df.format(date);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static long getCurrentMilliSecond(String date) {
        if (date != null && !"".equals(date)) {
            DateFormat dateFormat = new SimpleDateFormat(LONGFORMAT);
            Date mDate;
            try {
                mDate = dateFormat.parse(date);
                if (mDate != null) {
                    return mDate.getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;

    }

    public static int MinuteToMillSecond(int minute) {
        return minute * 60 * 1000;
    }


    public static String getFourTime(String date) {
        if ("".equals(date) || null == date) {
            return "";
        } else {
            format = new SimpleDateFormat(LONGFORMAT);

            try {
                Date datePast = format.parse(date);
                long currentTime = System.currentTimeMillis();
                Calendar cal = Calendar.getInstance();
                TimeZone timeZone = cal.getTimeZone();
                currentTime -= timeZone.getRawOffset();
                Date now = new Date(currentTime);
                if (null != datePast && null != now) {
                    long diff = now.getTime() - datePast.getTime();
                    long minute = diff / MINUTE;
                    if (minute <= 4) {
                        return "1";
                    } else {
                        return minute + "";
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";

    }

    public static String getFourOnline(Date current, Date past) {
        long diff = current.getTime() - past.getTime();
        long minute = diff / MINUTE;

        if (minute <= 4) {
            return "1";
        } else {
            return minute + "";
        }
    }
}
