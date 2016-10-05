package com.nicolascaorsi.pomodoro.timer.history;

import com.nicolascaorsi.pomodoro.BasePresenter;
import com.nicolascaorsi.pomodoro.BaseView;
import com.nicolascaorsi.pomodoro.data.Pomodoro;

import java.util.List;

/**
 * Created by nicolas on 10/2/16.
 */

public interface HistoryContract {

        interface View extends BaseView<Presenter> {

            void setPomodoros(List<Pomodoro> pomodoros);
            void addPomodoro(Pomodoro pomodoro);
            void setNoDataAvaliable();

        }

        interface Presenter extends BasePresenter {

            void start();
        }
    }
