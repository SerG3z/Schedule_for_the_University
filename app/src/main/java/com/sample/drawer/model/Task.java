package com.sample.drawer.model;

/**
 * Created by admin on 3/26/2016.
 */
public class Task {
    private String deadline;
    private String lesson;
    private String info;

    public Task(final String deadline, final String lesson, final String info) {
        this.deadline = deadline;
        this.lesson = lesson;
        this.info = info;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(final String deadline) {
        this.deadline = deadline;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(final String lesson) {
        this.lesson = lesson;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(final String info) {
        this.info = info;
    }
}
