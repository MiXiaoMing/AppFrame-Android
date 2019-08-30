package com.appframe.utils;

import android.text.TextUtils;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// 时间工具类

public class DateUtil {

    public static String[] WEEK = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
    public static final int WEEKDAYS = 7;
    /**
     * 日期类型 *
     */
    private static final String yyyyMMDD = "yyyy-MM-dd";
    private static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    private static final String HHmmss = "HH:mm:ss";
    private static final String hhmmss = "hh:mm:ss";
    private static final String LOCALE_DATE_FORMAT = "yyyy年M月d日 HH:mm:ss";
    private static final String DB_DATA_FORMAT = "yyyy-MM-DD HH:mm:ss";

    private static final String NEWS_ITEM_DATE_FORMAT = "hh:mm M月d日 yyyy";
    private static final String yyyyMMdd = "yyyy年M月d日";


    // string date之间转换
    public static String dateToString(Date date, String pattern) throws Exception {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date stringToDate(String dateStr, String pattern) throws Exception {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    /**
     * 格式化时�?
     *
     * @param datetime
     * @return
     */
    public static String format(long datetime) {
        String datetimeStr = null;
        if (datetime != 0) {
            datetimeStr = DateFormat.format(yyyyMMddHHmmss, datetime).toString();
        }
        return datetimeStr;
    }


    public static String dateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }

    /**
     * 将Date类型转换为日期字符串
     *
     * @param date
     *            Date对象
     * @param type
     *            需要的日期格式
     * @return 按照需求格式的日期字符串
     */
    public static String formatDate(Date date, String type) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(type);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getYMDWFromYMD(String dateStr) {
        String result = null;
        try {
            Date date = stringToDate(dateStr, yyyyMMDD);
            String week = dateToWeek(date);
            result = DateFormat.format(yyyyMMdd, date).toString() + " " + week;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将日期字符串转换为Date类型
     *
     * @param dateStr
     *            日期字符串
     * @param type
     *            日期字符串格式
     * @return Date对象
     */
    public static Date parseDate(String dateStr, String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    /**
     * 得到年
     *
     * @param date
     *            Date对象
     * @return 年
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 得到月
     *
     * @param date
     *            Date对象
     * @return 月
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;

    }

    /**
     * 得到日
     *
     * @param date
     *            Date对象
     * @return 日
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 转换日期 将日期转为今天, 昨天, 前天, XXXX-XX-XX, ...
     *
     * @param time
     *            时间
     * @return 当前日期转换为更容易理解的方式
     */
    public static String translateDate(Long time) {
        long oneDay = 24 * 60 * 60 * 1000;
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance(); // 今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        long todayStartTime = today.getTimeInMillis();

        if (time >= todayStartTime && time < todayStartTime + oneDay) { // today
            return "今天";
        } else if (time >= todayStartTime - oneDay && time < todayStartTime) { // yesterday
            return "昨天";
        } else if (time >= todayStartTime - oneDay * 2 && time < todayStartTime - oneDay) { // the
            // day
            // before
            // yesterday
            return "前天";
        } else if (time > todayStartTime + oneDay) { // future
            return "将来某一天";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(time);
            return dateFormat.format(date);
        }
    }

    // 转换日期 转换为更为人性化的时间
    private String translateDate(long time, long curTime) {
        long oneDay = 24 * 60 * 60;
        Calendar today = Calendar.getInstance(); // 今天
        today.setTimeInMillis(curTime * 1000);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        long todayStartTime = today.getTimeInMillis() / 1000;
        if (time >= todayStartTime) {
            long d = curTime - time;
            if (d <= 60) {
                return "1分钟前";
            } else if (d <= 60 * 60) {
                long m = d / 60;
                if (m <= 0) {
                    m = 1;
                }
                return m + "分钟前";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("今天 HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        } else {
            if (time < todayStartTime && time > todayStartTime - oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("昨天 HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {

                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else if (time < todayStartTime - oneDay && time > todayStartTime - 2 * oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("前天 HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        }
    }

}
