package com.sample.drawer.utils;

import android.content.res.Resources;

import com.sample.drawer.R;
import com.sample.drawer.scheduleDataBase.Classroom;
import com.sample.drawer.scheduleDataBase.HelperFactory;
import com.sample.drawer.scheduleDataBase.PeriodTime;
import com.sample.drawer.scheduleDataBase.PeriodType;
import com.sample.drawer.scheduleDataBase.ScheduleDBHelper;
import com.sample.drawer.scheduleDataBase.Subject;
import com.sample.drawer.scheduleDataBase.Teacher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A class to insert default values into database and initialize schedule
 */
public class ScheduleInitializer {
    public static void initializeSchedule(Calendar firstWeekMonday, int weeksCount, int[] controlPoints){
    }

    public static void insertDefaultData(Resources res){
        ScheduleDBHelper helper = HelperFactory.getHelper();
        try {
            String[] lessonTypes = res.getStringArray(R.array.type_lesson_list);
            for (String s : lessonTypes){
                helper.getPeriodTypeDAO().create(new PeriodType(s));
            }
            helper.getClassroomDAO().create(new Classroom("Аудитория 1"));
            helper.getClassroomDAO().create(new Classroom("Аудитория 2"));
            String[] lessonTimes= res.getStringArray(R.array.type_lesson_time);
            for (String begin : lessonTimes){
                String end = String.format("%02d:%02d", TimeHelper.addDeltaTimeMinutes
                        (begin,res.getInteger(R.integer.default_lesson_duration)));
                helper.getPeriodTimeDAO().create(new PeriodTime(begin,end));
            }
            helper.getTeacherDAO().create(new Teacher("Препод 1"));
            helper.getTeacherDAO().create(new Teacher("Препод 2"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
