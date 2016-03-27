package com.sample.drawer.utils;

import android.provider.Settings;

/**
 * Utility class
 */
public class TimeHelper {
    public static int getDeltaTimeMinutes(String time1, String time2){
        int reqLength = 5, colonIndex = 2, minutesInHour = 60;
        if (time1.length() < reqLength || time2.length() < reqLength)
            return 0;
        int t1h = Integer.parseInt(time1.substring(0,colonIndex));
        int t1m = Integer.parseInt(time1.substring(colonIndex + 1, time1.length() ));
        int t2h = Integer.parseInt(time2.substring(0,colonIndex));
        int t2m = Integer.parseInt(time2.substring(colonIndex + 1, time2.length() ));
        return (t2h * minutesInHour + t2m) - (t1h * minutesInHour + t1m);
    }
    public static String addDeltaTimeMinutes(String time1, int delta){
        int reqLength = 5, colonIndex = 2, minutesInHour = 60;
        if (time1.length() < reqLength)
            return null;
        int h = Integer.parseInt(time1.substring(0,colonIndex));
        int m = Integer.parseInt(time1.substring(colonIndex + 1, time1.length() ));
        int minutes = h * minutesInHour + m + delta;
        return String.format("%02d:%02d",minutes/60, minutes%60);
    }
}
