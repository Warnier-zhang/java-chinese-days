package com.chinesedays.util;

import com.chinesedays.model.ChineseDays;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtils {
    public static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
    public static ChineseDays chineseDays;

    static {
        try {
            chineseDays = JSONUtils.parse(ResourceUtils.getFile("classpath:chinese-days.json"), ChineseDays.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否是工作日
     */
    public static boolean isWorkday(Date date) {
        int weekday = date.getDay();
        String formattedDate = formatDate(date, "yyyy-MM-dd");
        return chineseDays.workdays.get(formattedDate) != null || (weekday >= 1 && weekday <= 5 && chineseDays.holidays.get(formattedDate) == null);
    }

    /**
     * 是否是节假日
     */
    public static boolean isHoliday(Date date) {
        return !isWorkday(date);
    }

    /**
     * 获取时间范围之内的节假日
     */
    public static List<Date> getHolidaysInRange(Date startDate, Date endDate, boolean includeWeekends) {
        return Stream.iterate(startDate, date -> addDays(date, 1))
                .limit(getDaysBetween(startDate, endDate) + 1)
                .filter(date -> isHoliday(date) && (includeWeekends || date.getDay() >= 1 && date.getDay() <= 5))
                .collect(Collectors.toList());
    }

    /**
     * 获取时间范围之内的工作日
     */
    public static List<Date> getWorkdaysInRange(Date startDate, Date endDate, boolean includeWeekends) {
        return Stream.iterate(startDate, date -> addDays(date, 1))
                .limit(getDaysBetween(startDate, endDate) + 1)
                .filter(date -> isWorkday(date) && (includeWeekends || date.getDay() >= 1 && date.getDay() <= 5))
                .collect(Collectors.toList());
    }

    /**
     * 查找从某个日期开始的第n个工作日
     */
    public static Date findWorkday(Date date, int deltaDays) {
        Date workday = date;

        if (deltaDays == 0) {
            if (isWorkday(workday)) {
                return workday;
            }
            deltaDays = 1;
        }

        int sign = deltaDays > 0 ? 1 : -1;
        int daysToAdd = Math.abs(deltaDays);
        while (daysToAdd > 0) {
            workday = addDays(workday, sign);
            if (isWorkday(workday)) {
                daysToAdd--;
            }
        }
        return workday;
    }

    /**
     * 格式化日期
     */
    public static String formatDate(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 解析日期
     */
    public static Date parseDate(String date, String pattern) throws ParseException {
        return org.apache.commons.lang3.time.DateUtils.parseDate(date, pattern);
    }

    /**
     * 增减天数
     */
    public static Date addDays(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addDays(date, amount);
    }

    /**
     * 计算天数
     */
    public static long getDaysBetween(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / MILLIS_PER_DAY;
    }
}
