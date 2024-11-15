package com.chinesedays.util;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class DateUtilsTest {
    @Test
    public void testFindWorkday() throws ParseException {
        Date now = DateUtils.parseDate("2024-09-30", "yyyy-MM-dd");
        System.out.println(DateUtils.formatDate(now, "yyyy-MM-dd"));
        System.out.println(
                DateUtils.formatDate(
                        DateUtils.findWorkday(now, 3),
                        "yyyy-MM-dd"
                )
        );

        System.out.println("---");

        now = DateUtils.parseDate("2024-10-11", "yyyy-MM-dd");
        System.out.println(DateUtils.formatDate(now, "yyyy-MM-dd"));
        System.out.println(
                DateUtils.formatDate(
                        DateUtils.findWorkday(now, 3),
                        "yyyy-MM-dd"
                )
        );

        System.out.println("---");

        now = DateUtils.parseDate("2024-10-14", "yyyy-MM-dd");
        System.out.println(DateUtils.formatDate(now, "yyyy-MM-dd"));
        System.out.println(
                DateUtils.formatDate(
                        DateUtils.findWorkday(now, 3),
                        "yyyy-MM-dd"
                )
        );
        System.out.println("---");
    }

    @Test
    public void testGetHolidaysInRange() throws ParseException {
        List<Date> holidays = DateUtils.getHolidaysInRange(
                DateUtils.parseDate("2024-09-30", "yyyy-MM-dd"),
                DateUtils.parseDate("2024-10-07", "yyyy-MM-dd"),
                true
        );
        for (Date holiday : holidays) {
            System.out.println(DateUtils.formatDate(holiday, "yyyy-MM-dd"));
        }
        System.out.println("---");
    }

    @Test
    public void testGetWorkdaysInRange() throws ParseException {
        List<Date> workdays = DateUtils.getWorkdaysInRange(
                DateUtils.parseDate("2024-09-10", "yyyy-MM-dd"),
                DateUtils.parseDate("2024-09-20", "yyyy-MM-dd"),
                true
        );
        for (Date workday : workdays) {
            System.out.println(DateUtils.formatDate(workday, "yyyy-MM-dd"));
        }
        System.out.println("---");
    }
}
