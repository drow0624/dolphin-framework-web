package com.company.common.util;

import java.time.*;
import java.util.Date;

/**
 * 时间工具
 * 时间转化为时间瞬间
 * 时间瞬间+时区转化为本地时间
 * 本地时间+时区转化为时间瞬间
 */
public class DateTimeUtil {

    private DateTimeUtil(){}


    // 01. DATE --> LocalDateTime
    public static LocalDateTime UDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    // 02. DATE --> LocalDate
    public static LocalDate UDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    // 03. DATE --> LocalTime
    public static void UDateToLocalTime(Date date) {
        Instant instant = date.toInstant();//时间片段
        ZoneId zone = ZoneId.systemDefault();//时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalTime localTime = localDateTime.toLocalTime();
    }


    // 04. LocalDateTime --> DATE
    public static Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    // 05. LocalDate --> DATE
    public static Date LocalDateToUdate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    // 06. LocalTime --> DATE
    public static Date LocalTimeToUdate() {
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date dateNow(){
        Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
