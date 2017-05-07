package com.example.haoyuban111.mubanapplication.utils;

import android.annotation.SuppressLint;


import com.example.haoyuban111.mubanapplication.ui.view.CustomDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

//    public static String[] weekName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static CustomDate getNextSunday() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7 - getWeekDay() + 1);
        CustomDate date = new CustomDate(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        return date;
    }

    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;

    }

    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month))
                + "-01";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static boolean isToday(CustomDate date) {
        if (null != date) {
            return (date.year == DateUtil.getYear() &&
                    date.month == DateUtil.getMonth()
                    && date.day == DateUtil.getCurrentMonthDay());
        }
        return false;
    }

    public static boolean isCurrentMonth(CustomDate date) {
        if (null != date) {
            return (date.year == DateUtil.getYear() &&
                    date.month == DateUtil.getMonth());
        }
        return false;
    }

    public static boolean isPreMonth(CustomDate date) {
        if (null != date) {
            if (date.year == DateUtil.getYear()) {
                return date.month < DateUtil.getMonth();
            } else if (date.year == DateUtil.getYear()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean isSelectMonth(CustomDate selDate, CustomDate date) {
        if (null != selDate && null != date) {
            return selDate.month == date.month;
        }
        return false;
    }

    public static boolean isSelectYear(CustomDate selDate, CustomDate date) {
        if (null != selDate && null != date) {
            return selDate.year == date.year;
        }
        return false;
    }


    //检测当前日历显示日期是否是已经过去的日期
    public static Boolean checkIsPastDate(CustomDate date) {
        if (date.year < DateUtil.getYear()) {
            return true;
        } else if (date.year > DateUtil.getYear()) {
            return false;
        } else {
            if (date.month < DateUtil.getMonth()) {
                return true;
            } else if (date.month == DateUtil.getMonth()) {
                if (date.day < DateUtil.getCurrentMonthDay()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    //检测list中是否是含有已经过去的日期
    public static void checkIsHasPastDate(ArrayList<String> list) {
        if (list != null) {
            if (list.size() == 0) {

            } else if (list.size() == 1) {
                String date = list.get(0);
                if (checkPastDate(date)) {//是过期日期
                    list.clear();
                    list.add(DateDFUtils.getNowTime());//添加今天

                }

            } else if (list.size() == 2) {
                String firstDate = list.get(0);
                String lastDate = list.get(1);
                if (checkPastDate(firstDate)) {
                    if (checkPastDate(lastDate)) {
                        list.clear();
                    } else {
                        list.remove(0);
                        list.add(0, DateDFUtils.getNowTime());
                    }
                }

            }
        }
    }


    //检测list中是否是含有已经过去的日期
    public static Boolean checkPastDate(String date) {
        if (null != date && !"".equals(date)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String today = getNowTime();

            Date mDate = null;
            Date mToday = null;
            try {
                mDate = format.parse(date);
                mToday = format.parse(today);
                long diff = mDate.getTime() - mToday.getTime();
                if (diff >= 0) {
                    return false;
                } else {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    //获取当前时间
    public static String getNowTime() {
        Calendar data = Calendar.getInstance();
        int Year = data.get(Calendar.YEAR);
        int monthOfYear = data.get(Calendar.MONTH) + 1;
        int dayOfMonth = data.get(Calendar.DATE);
        return Year + "-" + monthOfYear + "-" + dayOfMonth;
    }


    //将string 转变为 CustomDate
    public static CustomDate getCustomDateFromString(String date) {
        if (null != date && !"".equals(date)) {
//            try {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                Date mDate = format.parse(date); // why the mDate.getYear() = 116, not 2016?
            if (date.length() > 10) {
                date = date.substring(0, 10);
            }

            if (date.contains("-")) {
                String[] array = date.split("-");
                if (array.length == 3) {
                    int year = Integer.parseInt(array[0]);
                    int month = Integer.parseInt(array[1]);
                    int day = Integer.parseInt(array[2]);
                    CustomDate mCustomDate = new CustomDate(year, month, day);
                    return mCustomDate;
                }
            }

//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }


}
