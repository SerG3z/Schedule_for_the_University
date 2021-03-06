package com.sample.drawer.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Periods table
 */
@DatabaseTable
public class Period {

    public static final String FIELD_SUBJECT = "subject";
    public static final String FIELD_ID = "id";
    public static final String FIELD_TIME = "time";

    @DatabaseField(generatedId = true, columnName = FIELD_ID)
    private int id;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Subject subject;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private PeriodType type;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = FIELD_TIME)
    private PeriodTime time;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Classroom classroom;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Teacher teacher;
    @DatabaseField(canBeNull = false)
    private boolean firstWeek;
    @DatabaseField(canBeNull = false)
    private boolean secondWeek;

    private Period(Builder builder) {
        subject = builder.subject;
        time = builder.time;
        firstWeek = builder.firstWeek;
        secondWeek = builder.secondWeek;
        type = builder.type;
        classroom = builder.classroom;
        teacher = builder.teacher;
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


    public static class Builder{
        //required parameters
        private final Subject subject;
        private final PeriodTime time;
        private final boolean firstWeek;
        private final boolean secondWeek;
        //optional parameters
        private PeriodType type = new PeriodType("");
        private Classroom classroom = new Classroom("");
        private Teacher teacher = new Teacher("");

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


        public Period build() {
            return new Period(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return subject.getSubject() + ", " + teacher.getTeacher() + ", " + type.getPeriodType() + ", " + classroom.getClassroom();
    }



}
