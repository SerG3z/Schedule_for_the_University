package com.sample.drawer.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by admin on 3/23/2016.
 */
public class Data implements Parcelable {
    private String time;
    private String typeLesson;
    private String nameLesson;
    private String fioTeacher;
    private String numberAuditory;
    private String typeWeek;

    public Data(final String time, final String typeLesson, final String nameLesson, final String fioTeacher, final String numberAuditory, final String typeWeek) {
        this.time = time;
        this.typeLesson = typeLesson;
        this.nameLesson = nameLesson;
        this.fioTeacher = fioTeacher;
        this.numberAuditory = numberAuditory;
        this.typeWeek = typeWeek;
    }

    public String getTypeWeek() {
        return typeWeek;
    }

    public void setTypeWeek(final String typeWeek) {
        this.typeWeek = typeWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getTypeLesson() {
        return typeLesson;
    }

    public void setTypeLesson(final String typeLesson) {
        this.typeLesson = typeLesson;
    }

    public String getNameLesson() {
        return nameLesson;
    }

    public void setNameLesson(final String nameLesson) {
        this.nameLesson = nameLesson;
    }

    public String getFioTeacher() {
        return fioTeacher;
    }

    public void setFioTeacher(final String fioTeacher) {
        this.fioTeacher = fioTeacher;
    }

    public String getNumberAuditory() {
        return numberAuditory;
    }

    public void setNumberAuditory(final String numberAuditory) {
        this.numberAuditory = numberAuditory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(time);
        parcel.writeString(typeLesson);
        parcel.writeString(nameLesson);
        parcel.writeString(fioTeacher);
        parcel.writeString(numberAuditory);
        parcel.writeString(typeWeek);
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        // распаковываем объект из Parcel
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private Data(Parcel parcel) {
        time = parcel.readString();
        typeLesson = parcel.readString();
        nameLesson = parcel.readString();
        fioTeacher = parcel.readString();
        numberAuditory = parcel.readString();
        typeWeek = parcel.readString();
    }
}
