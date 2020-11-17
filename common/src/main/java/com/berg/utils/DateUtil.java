package com.berg.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 日期格式化
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }


    /**
     * 对月份进行操作
     * @param date
     * @param num
     * @return
     */
    public static Date addDateByMonths(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, num);
        return calendar.getTime();
    }

    /**
     * 对小时进行操作
     * @param date
     * @param num
     * @return
     */
    public static Date addDateByHour(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, num);
        return calendar.getTime();
    }

    /**
     * 对分钟进行操作
     * @param date
     * @param num
     * @return
     */
    public static Date addDateByMins(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, num);
        return calendar.getTime();
    }

    /**
     * 对秒进行操作
     * @param date
     * @param num
     * @return
     */
    public static Date addDateBySecond(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, num);
        return calendar.getTime();
    }

    /**
     * date的起始时间
     * xx-xx-xx 00:00:00
     * @param date
     * @return
     */
    public static Date dayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    /**
     * date的结束时间
     * xx-xx-xx 23:59:59
     * @param date
     * @return
     */
    public static Date dayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 999);
        return calendar.getTime();
    }

    /**
     * 计算两个时间差的天数（date1、与date2的时间差）
     * @param date1
     * @param date2
     * @return 时间差（单位：天）
     */
    public static Integer dayBetweenDate(Date date1, Date date2) {
        if (null != date1 && null != date2) {
            Long diff = date1.getTime() - date2.getTime();
            Long days = diff / (24 * 60 * 60 * 1000L);
            return days.intValue();
        } else {
            return null;
        }
    }

    /**
     * 计算两个时间差的分钟数（date1、与date2的时间差）
     * @param date1
     * @param date2
     * @return 时间差（单位：分钟）
     */
    public static Integer minuteBetweenDate(Date date1, Date date2) {
        if (null != date1 && null != date2) {
            Long diff = date1.getTime() - date2.getTime();
            Long days = diff / (60 * 1000L);
            return days.intValue();
        } else {
            return null;
        }
    }

    /**
     * 计算两个时间差的秒数（date1、与date2的时间差）
     * @param date1
     * @param date2
     * @return 时间差（单位：秒）
     */
    public static Integer secondBetweenDate(Date date1, Date date2) {
        if (null != date1 && null != date2) {
            Long diff = date1.getTime() - date2.getTime();
            Long days = diff / (1000L);
            return days.intValue();
        } else {
            return null;
        }
    }

    /**
     * 判断时间是否在当天
     * @param firstTime
     * @return true包含/false不包含
     */
    public static boolean isInDay(Date firstTime) {
        return secondBetweenDate(firstTime, dayStart(new Date())) >= 0
                && secondBetweenDate(firstTime, dayEnd(new Date())) < 0;
    }

    /**
     * 获取此刻到明天凌晨时间差
     * @param date
     * @return
     */
    public static Integer getTimerDifferenceSecond(Date date){
        if(null!=date){
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH,1);
            c.set(Calendar.HOUR_OF_DAY,0);
            c.set(Calendar.MINUTE,0);
            c.set(Calendar.SECOND,0);
            return secondBetweenDate(c.getTime(),date);
        } else {
            return null;
        }
    }

    /**
     * 获得当前系统的 yyyy
     * @return
     */
    public static String getSysYear()
    {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.YEAR));
    }

    /**
     * 获得当前时间是星期几
     * @return
     */
    public static String getSysWeekDay()
    {
        Calendar date = Calendar.getInstance();
        int i = (date.get(Calendar.DAY_OF_WEEK)-1);
        return String.valueOf(i);
    }

    /**
     * 获得当前系统的日期几号
     * @return
     */
    public static String getSysMonthDay()
    {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 字符转日期
     * @param datestr
     * @param pattern
     * @return
     * @throws Exception
     */
    public static Date parseDate(String datestr,String pattern) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(datestr);
    }
}
