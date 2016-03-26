package com.sample.drawer.scheduleDataBase;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

/**
 * Periods table
 */
@DatabaseTable
public class Period {

    public static final String FIELD_SUBJECT_NAME = "subject";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Subject subject;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private PeriodType type;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private PeriodTime time;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Classroom classroom;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Teacher teacher;
    @DatabaseField(canBeNull = false)
    private boolean firstWeek;
    @DatabaseField(canBeNull = false)
    private boolean secondWeek;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Task task;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Day day;

    private Period(Builder builder) {
        subject = builder.subject;
        time = builder.time;
        firstWeek = builder.firstWeek;
        secondWeek = builder.secondWeek;
        type = builder.type;
        classroom = builder.classroom;
        teacher = builder.teacher;
        task = builder.task;
        day = builder.day;
    }

    public Period() {
    }

    public PeriodType getType() {
        return type;
    }

    public void setType(PeriodType type) {
        this.type = type;
    }

    public boolean isFirstWeek() {
        return firstWeek;
    }

    public void setFirstWeek(boolean firstWeek) {
        this.firstWeek = firstWeek;
    }

    public boolean isSecondWeek() {
        return secondWeek;
    }

    public void setSecondWeek(boolean secondWeek) {
        this.secondWeek = secondWeek;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public PeriodTime getTime() {
        return time;
    }

    public void setTime(PeriodTime time) {
        this.time = time;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public static class Builder {
        //required parameters
        private final Subject subject;
        private final PeriodTime time;
        private final boolean firstWeek;
        private final boolean secondWeek;
        //optional parameters
        private PeriodType type = null;
        private Classroom classroom = null;
        private Teacher teacher = null;
        private Task task = null;
        private Day day = null;

        public Builder(Subject subject, PeriodTime time, boolean firstWeek, boolean secondWeek) {
            this.subject = subject;
            this.time = time;
            this.firstWeek = firstWeek;
            this.secondWeek = secondWeek;
        }

        public Builder type(PeriodType val) {
            type = val;
            return this;
        }

        public Builder сlassroom(Classroom val) {
            classroom = val;
            return this;
        }

        public Builder teacher(Teacher val) {
            teacher = val;
            return this;
        }

        public Builder teacher(Task val) {
            task = val;
            return this;
        }

        public Builder day(Day val) {
            day = val;
            return this;
        }

        public Period build() {
            return new Period(this);
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return subject.getSubject() + ", " + teacher.getTeacher() + ", " + type.getPeriodType() + ", " + classroom.getClassroom();
    }

    public class DAO extends BaseDaoImpl<Period, Integer> {

        protected DAO(ConnectionSource connectionSource, Class<Period> dataClass) throws SQLException {
            super(connectionSource, dataClass);
        }
    }
}