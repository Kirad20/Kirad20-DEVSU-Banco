package com.devsu.microservices.cuentasservice.domain.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date convertStringToDate(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }
}
