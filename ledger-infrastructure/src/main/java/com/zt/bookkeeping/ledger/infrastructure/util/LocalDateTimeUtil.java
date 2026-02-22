package com.zt.bookkeeping.ledger.infrastructure.util;

import org.springframework.util.ObjectUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTimeUtil {

    public static String TIME_FORMATTER_NORMAL = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_FORMATTER_NORMAL = "yyyy-MM-dd";

    public static String DATE_FORMATTER_yMd = "yyyyMMdd";

    public static String DATE_FORMATTER_MONTH_ONE = "yyyy-MM-01";

    public static Integer ZONE_OFFSET_HOURS = 8;

    public static String format(LocalDateTime time) {
        if (ObjectUtils.isEmpty(time)) {
            return "";
        }
        return LocalDateTimeUtil.format(time,TIME_FORMATTER_NORMAL);
    }

    public static String format(LocalDateTime time, String formatter) {
        if (ObjectUtils.isEmpty(time)) {
            return "";
        }
        return time.format(DateTimeFormatter.ofPattern(formatter));
    }

    public static String format(LocalDateTime time, DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    public static long toTimestampAtSeconds(LocalDateTime time) {
        if (ObjectUtils.isEmpty(time)) {
            return 0L;
        }
        return time.toEpochSecond(ZoneOffset.ofHours(ZONE_OFFSET_HOURS));
    }

    public static long toTimestampAtMilliseconds(LocalDateTime time) {
        if (ObjectUtils.isEmpty(time)) {
            return 0L;
        }
        return time.toInstant(ZoneOffset.ofHours(ZONE_OFFSET_HOURS)).toEpochMilli();
    }


    /**
     *
     * @param localDateTime
     * @return
     */
    public static String localDateTimeChangeLocalDate(LocalDateTime localDateTime) {
        localDateTime.toLocalDate();
        LocalDate localDate = localDateTime.toLocalDate();
        return String.valueOf(localDate);
    }



    public static LocalDateTime toLocalDateTimeByMillis(long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.ofHours(ZONE_OFFSET_HOURS));
    }

    public static LocalDateTime toLocalDateTimeBySeconds(long seconds) {
        if (ObjectUtils.isEmpty(seconds)) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneOffset.ofHours(ZONE_OFFSET_HOURS));
    }

    public static LocalDateTime toLocalDateTimeByString(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(TIME_FORMATTER_NORMAL));
    }

    public static LocalDateTime toLocalDateTimeByString(String dateTime, String formatter) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(formatter));
    }

    public static LocalDate toLocalDateByString(String date) {
        return toLocalDateByString(date, DATE_FORMATTER_NORMAL);
    }

    public static LocalDate toLocalDateByString(String date, String formatter) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(formatter));
    }

    public static String format(LocalDate date) {
        if (ObjectUtils.isEmpty(date)) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMATTER_NORMAL));
    }

    public static String format(LocalDate date, String formatter) {
        if (ObjectUtils.isEmpty(date)) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(formatter));
    }

    /**
     * 获取本月一号
     * @return
     */
    public static LocalDate getThisMonthBegin() {
        LocalDate now = LocalDate.now();
        return now.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取本周周一
     * @return
     */
    public static LocalDateTime getThisWeekBegin() {
        LocalDateTime localDate = LocalDateTime.now();
        return localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    /**
     * 获取本周周日
     * @return
     */
    public static LocalDateTime getThisWeekEnd() {
        LocalDateTime localDate = LocalDateTime.now();
        return localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    /**
     * 获取上周周一
     * @return
     */
    public static LocalDateTime getLastWeekBegin() {
        LocalDateTime localDate = getThisWeekBegin();
        return localDate.minusWeeks(1);
    }

    /**
     * 获取上周周日
     * @return
     */
    public static LocalDateTime getLastWeekEnd() {
        LocalDateTime localDate = getThisWeekBegin();
        return localDate.minusDays(1);
    }

    /**
     * 给定的日期是否在开始时间和结束时间之间
     * @param date
     * @param start
     * @param end
     * @return
     */
    public static Boolean between(LocalDateTime date, LocalDateTime start, LocalDateTime end) {
         return start.isBefore(date) && end.isAfter(date);
    }
}
