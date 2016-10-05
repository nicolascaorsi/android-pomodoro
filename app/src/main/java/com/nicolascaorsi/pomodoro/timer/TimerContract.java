package com.nicolascaorsi.pomodoro.timer;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.nicolascaorsi.pomodoro.BasePresenter;
import com.nicolascaorsi.pomodoro.BaseView;
import com.nicolascaorsi.pomodoro.data.Pomodoro;

/**
 * Created by nicolas on 10/2/16.
 */

public interface TimerContract {

    interface View extends BaseView<Presenter> {

        void init(String chronometerText);

        void start(String text, long maxProgress);

        void stop(String text);

        void updateProgress(String text, long progress);

        void changeToogleIcon(@DrawableRes int resourceId);

        void showDurationSelectionDialog(long currentDuration);

        void showNotification(@StringRes int resourceId);
    }

    interface Presenter extends BasePresenter {
        void start();

        void togglePomodoro();

        void onUpdateDurationSelected();

        void updateDuration(long duration);
    }
}