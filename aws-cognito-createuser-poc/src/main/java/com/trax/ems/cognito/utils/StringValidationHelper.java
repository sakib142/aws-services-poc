package com.trax.ems.cognito.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StringValidationHelper {

    public static String convertDateToString(java.util.Date date, String format) {
        String dateString = null;

        if(null != date) {
            DateFormat df = new SimpleDateFormat(format);
            dateString = df.format(date);
        }

        return dateString;
    }

}
