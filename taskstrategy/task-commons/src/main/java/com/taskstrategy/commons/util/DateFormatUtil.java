package com.taskstrategy.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/24/13
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */
public final class DateFormatUtil {

    private DateFormatUtil() {

    }

    // SimpleDateFormat is not thread-safe, so give one to each thread
    private static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM/dd/yyyy");
        }
    };

    public static String formatDate(Date date) {
        return formatter.get().format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return formatter.get().parse(date);
    }

}

