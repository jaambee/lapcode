package com.reframe.lapp.utils;

import android.util.Log;

import com.reframe.lapp.constants.Constants;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static String getParsedLevel(String level) {
        if(level.toLowerCase().equals(Constants.BASIC)) {
            return "básico";
        } else {
            return "avanzado";
        }
    }
}
