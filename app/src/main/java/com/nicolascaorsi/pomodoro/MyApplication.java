package com.nicolascaorsi.pomodoro;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by nicolas on 10/2/16.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
