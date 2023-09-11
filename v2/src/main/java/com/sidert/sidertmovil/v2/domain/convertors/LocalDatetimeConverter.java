package com.sidert.sidertmovil.v2.domain.convertors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import androidx.room.TypeConverter;

public class LocalDatetimeConverter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    @TypeConverter
    public static LocalDateTime fromTimestamp(String value) {
        if (value == null) {
            return null;
        } else {
            return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
        }
    }

    @TypeConverter
    public static String fromLocalDatetime(LocalDateTime value) {
        if (value == null) {
            return null;
        } else {
            return value.format(DATE_TIME_FORMATTER);
        }
    }

}
