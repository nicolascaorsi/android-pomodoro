package com.nicolascaorsi.pomodoro.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by nicolas on 10/2/16.
 */

public interface PomodorosDataSource {

        interface LoadPomodorosCallback {

            void onPomodorosLoaded(List<Pomodoro> tasks);

            void onDataNotAvailable();
        }

        interface PomodoroAddedCallback{
            void onPomodoroAdded(Pomodoro pomodoro);
        }

        void getPomodoros(@NonNull LoadPomodorosCallback callback);

        void savePomodoro(@NonNull Pomodoro pomodoro);

        void setAddedCallback(PomodoroAddedCallback callback);

    }