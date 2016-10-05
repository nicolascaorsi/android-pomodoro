package com.nicolascaorsi.pomodoro.data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by nicolas on 10/2/16.
 */

public class PomodorosRepository implements PomodorosDataSource {

    private PomodorosDataSource.PomodoroAddedCallback mAddedCallback;

    @Override
    public void getPomodoros(@NonNull LoadPomodorosCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Pomodoro> pomodoros = new ArrayList<Pomodoro>(realm.where(Pomodoro.class).findAllSorted("date", Sort.DESCENDING));
        if(pomodoros.isEmpty()){
            callback.onDataNotAvailable();
        }else{
            callback.onPomodorosLoaded(pomodoros);
        }

    }

    @Override
    public void savePomodoro(@NonNull Pomodoro pomodoro) {
//        pomodoro.setDate(new Date(pomodoro.getDate().getTime() - 1000 * 60 * 60 * 10000 ));
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(pomodoro);
        realm.commitTransaction();
        if (mAddedCallback != null) {
            mAddedCallback.onPomodoroAdded(pomodoro);
        }
    }

    @Override
    public void setAddedCallback(final PomodoroAddedCallback callback) {
        mAddedCallback = callback;
    }
}
