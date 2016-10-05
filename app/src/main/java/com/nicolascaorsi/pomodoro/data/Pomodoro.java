package com.nicolascaorsi.pomodoro.data;

import android.support.annotation.NonNull;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nicolas on 10/2/16.
 */

public class Pomodoro extends RealmObject{
    @NonNull
    private Date date;

    private long duration;

    private boolean completed;

    public Pomodoro(){

    }

    public Pomodoro(@NonNull Date date, long duration, boolean completed) {
        this.date = date;
        this.duration = duration;
        this.completed = completed;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
