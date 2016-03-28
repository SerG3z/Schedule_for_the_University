package com.sample.drawer.database;

import android.content.res.Resources;

import com.sample.drawer.R;
import com.sample.drawer.database.Classroom;
import com.sample.drawer.database.Day;
import com.sample.drawer.database.HelperFactory;
import com.sample.drawer.database.PeriodTime;
import com.sample.drawer.database.PeriodType;
import com.sample.drawer.database.ScheduleDBHelper;
import com.sample.drawer.database.Subject;
import com.sample.drawer.database.Teacher;
import com.sample.drawer.database.Week;
import com.sample.drawer.utils.TimeHelper;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * A class to insert default values into database and initialize schedule
 */
public class ScheduleInitializer {
    public static void initializeSchedule(Calendar firstWeekMonday, boolean firstWeekIsEven, int weeksCount, int[] controlPoints) throws SQLException {
        int daysInWeek = 7;
        Calendar calDay = firstWeekMonday;
        ScheduleDBHelper helper = HelperFactory.getHelper();
        boolean isEven = firstWeekIsEven;
        for (int weekN = 1; weekN <= weeksCount; weekN++ ){
            boolean isControl = false;
            for (int i : controlPoints){
                if (i == weekN){
                    isControl = true;
                }
            }
            Week week = new Week(weekN, !isEven, isControl, helper.getWeekDAO());
            helper.getWeekDAO().create(week);
            for (int dayN = 1; dayN <= daysInWeek; dayN++ ){
                Day day = new Day(new Date(calDay.getTimeInMillis()), dayN);
                day.setWeek(week);
                week.addDay(day);
                calDay.add(Calendar.DATE, 1);
            }
            isEven = !isEven;
        }
    }

    public static void insertDefaultData(Resources res){
        ScheduleDBHelper helper = HelperFactory.getHelper();
        try {
            String[] lessonTypes = res.getStringArray(R.array.type_lesson_list);
            for (String s : lessonTypes){
                helper.getPeriodTypeDAO().create(new PeriodType(s));
            }

            helper.getSubjectDAO().create(new Subject("Предмет 1"));
            helper.getSubjectDAO().create(new Subject("Предмет 2"));

            helper.getClassroomDAO().create(new Classroom("Аудитория 1"));
            helper.getClassroomDAO().create(new Classroom("Аудитория 2"));

            String[] lessonTimes= res.getStringArray(R.array.type_lesson_time);
            for (String begin : lessonTimes){
                String end = TimeHelper.addDeltaTimeMinutes(begin, res.getInteger(R.integer.default_lesson_duration));
                helper.getPeriodTimeDAO().create(new PeriodTime(begin,end));
            }

            helper.getTeacherDAO().create(new Teacher("Препод 1"));
            helper.getTeacherDAO().create(new Teacher("Препод 2"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
