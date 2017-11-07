package com.reframe.lapp.utils;

import android.util.Log;

import com.reframe.lapp.constants.Constants;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by Aldo on 08-05-2017 to Lapp.
 */

public class Utils {

    public static String getDateParsed(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        Date result;
        String parsed = "";
        try {

            PrettyTime p = new PrettyTime(new Locale("ES"));
            result = df.parse(date);
            parsed = p.format(result);
        } catch (Exception e) {
            Log.d("ERROR PARSING TIME", e.getMessage());
        }
        return parsed;
    }

    public static String getYears(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        Date result;
        String parsed = "";
        int diff = 0;
        try {

            result = df.parse(date);

            Calendar a = getCalendar(result);
            Calendar b = getCalendar(new Date());
            diff = b.get(YEAR) - a.get(YEAR);
            if (a.get(MONTH) > b.get(MONTH) ||
                    (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
                diff--;
            }
        } catch (Exception e) {
            Log.d("ERROR PARSING TIME", e.getMessage());
        }

        return String.valueOf(diff).concat(" años");
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static String getParsedLevel(String level) {
        if(level.toLowerCase().equals(Constants.BASIC)) {
            return "básico";
        } else {
            return "avanzado";
        }
    }
}
