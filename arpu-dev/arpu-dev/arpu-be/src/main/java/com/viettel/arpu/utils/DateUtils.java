package com.viettel.arpu.utils;

import com.viettel.arpu.exception.DateIncorrectException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author VuHQ
 * @Since 6/3/2020
 */
public interface DateUtils {
    ZoneId ZONE_ID = ZoneId.systemDefault();

    static Instant convertLocalDateToInstantStartOfDay(LocalDate localDate){
       return localDate.atStartOfDay(ZONE_ID).toInstant();
    }

    static Instant convertLocalDateToInstantEndOfDay(LocalDate localDate){
        LocalTime.from(localDate);
        return localDate.atTime(LocalTime.MAX).atZone(ZONE_ID).toInstant();
    }

    static LocalDate convertInstantToLocalDate(Instant instant){
        return instant.atZone(ZONE_ID).toLocalDate();
    }

    public static Date convertStringToDate(String date){
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException parseException) {
            throw new DateIncorrectException();
        }
    }

    public static LocalDate convertLocalDateToCustomFomat(LocalDate localDate) {
        localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
       return localDate;
    }
}
