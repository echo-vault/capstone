package com.echovault.capstone.Util;

import java.util.Date;

public class TimeUtil {

    public static String formatDate(Date date){
        String time = String.valueOf(date);
        String newTime = time.substring(5,7) + "/" + time.substring(8, 10) +
                "/" + time.substring(0,4);
        return newTime;
    }

    public static String formatTime(Date date){
        String time = String.valueOf(date);
        String newTime = time.substring(11, 16);
        return newTime;
    }
}
