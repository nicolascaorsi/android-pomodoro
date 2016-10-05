package com.nicolascaorsi.pomodoro.timer.history;

import android.support.annotation.NonNull;

import com.nicolascaorsi.pomodoro.data.Pomodoro;
import com.nicolascaorsi.pomodoro.data.PomodorosDataSource;

import java.util.List;

/**
 * Created by nicolas on 10/2/16.
 */
public class HistoryPresenter implements HistoryContract.Presenter, PomodorosDataSource.LoadPomodorosCallback, PomodorosDataSource.PomodoroAddedCallback {

    @NonNull
    private final PomodorosDataSource mPomodorosRepository;

    @NonNull
    private final HistoryContract.View mPomodoroView;

    /**
     * Creates a presenter for the add/edit view.
     *
     * @param pomodorosRepository a repository of data for tasks
     * @param pomodoroView the add/edit view
     */
    public HistoryPresenter(@NonNull PomodorosDataSource pomodorosRepository,
                            @NonNull HistoryContract.View pomodoroView) {
        mPomodorosRepository = pomodorosRepository;
        mPomodoroView = pomodoroView;
        mPomodoroView.setPresenter(this);
        pomodorosRepository.setAddedCallback(this);
    }

    @Override
    public void start() {
        mPomodorosRepository.getPomodoros(this);
    }

    @Override
    public void onPomodorosLoaded(List<Pomodoro> pomodoros) {
        mPomodoroView.setPomodoros(pomodoros);
    }

    @Override
    public void onDataNotAvailable() {
        mPomodoroView.setNoDataAvaliable();
    }

    @Override
    public void onPomodoroAdded(Pomodoro pomodoro) {
        mPomodoroView.addPomodoro(pomodoro);
    }
}
