package com.horyu1234.eventangel.factory;

import java.time.format.DateTimeFormatter;

public class DateFactory {
    public static final DateTimeFormatter PRETTY_FORMAT = DateTimeFormatter.ofPattern("yyyy년MM월dd일 a hh시mm분ss초");
    public static final DateTimeFormatter DATABASE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    private DateFactory() {
        throw new IllegalStateException("Factory class");
    }
}
