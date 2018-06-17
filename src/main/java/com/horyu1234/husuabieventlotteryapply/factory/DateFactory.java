package com.horyu1234.husuabieventlotteryapply.factory;

import java.time.format.DateTimeFormatter;

public class DateFactory {
    public static final DateTimeFormatter DATABASE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    private DateFactory() {
        throw new IllegalStateException("Factory class");
    }
}
