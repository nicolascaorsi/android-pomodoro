package com.nicolascaorsi.pomodoro.timer;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.format.DateUtils;

import com.nicolascaorsi.pomodoro.R;
import com.nicolascaorsi.pomodoro.data.Pomodoro;
import com.nicolascaorsi.pomodoro.data.PomodorosDataSource;
import com.nicolascaorsi.pomodoro.utils.DateFormatUtils;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by nicolas on 10/2/16.
 */

public class TimerPresenter implements TimerContract.Presenter
        /*TasksDataSource.GetTaskCallback */ {

    @NonNull
    private final PomodorosDataSource mPomodorosRepository;

    @NonNull
    private final SharedPreferences mSharedPreferences;

    private CountDownTimer mCountDownTimer;

    private long mCurrentTime;

    @NonNull
    private final TimerContract.View mPomodoroView;

    private long mPomodoroDuration;

    /**
     * Creates a presenter for the add/edit view.
     *
     * @param pomodorosRepository a repository of data for tasks
     * @param pomodoroView the add/edit view
     */
    public TimerPresenter(@NonNull SharedPreferences sharedPreferences,
                          @NonNull PomodorosDataSource pomodorosRepository,
                          @NonNull TimerContract.View pomodoroView) {
        mSharedPreferences = sharedPreferences;
        mPomodorosRepository = pomodorosRepository;
        mPomodoroView = pomodoroView;
        mPomodoroView.setPresenter(this);
        mPomodoroDuration = mSharedPreferences.getLong("POMODORO_DURATION", 1000 * 60 * 25);
    }

    @Override
    public void start() {
        mPomodoroView.init(DateFormatUtils.getDateInMinutes((int)mPomodoroDuration));
    }

    @Override
    public void togglePomodoro() {
        if (mCountDownTimer == null) {
            startTimer();
        } else {
            savePomodoro(false, mPomodoroDuration - mCurrentTime);
            stopTimer();
        }
    }

    @Override
    public void onUpdateDurationSelected() {
        mPomodoroView.showDurationSelectionDialog(mPomodoroDuration);
    }

    @Override
    public void updateDuration(long duration){
        mSharedPreferences
                .edit()
                .putLong("POMODORO_DURATION", duration)
                .apply();

        mPomodoroDuration = duration;

        if(mCountDownTimer == null){
            mPomodoroView.updateProgress(DateFormatUtils.getDateInMinutes((int)mPomodoroDuration), mCurrentTime / mPomodoroDuration);
        }
    }

    private void savePomodoro(boolean completed, long pomodoroTime) {
         mPomodorosRepository.savePomodoro(new Pomodoro(new Date(), pomodoroTime, completed));
    }

    private void startTimer() {
        mCurrentTime = mPomodoroDuration;
        mPomodoroView.start(DateFormatUtils.getDateInMinutes((int) mCurrentTime), mPomodoroDuration);

        mCountDownTimer = new CountDownTimer(mCurrentTime, 1000) {

            public void onTick(long millisUntilFinished) {
                mPomodoroView.updateProgress(DateFormatUtils.getDateInMinutes((int) millisUntilFinished), mCurrentTime);
                mCurrentTime -= 1000;
            }

            public void onFinish() {
                savePomodoro(true, mPomodoroDuration);
                stopTimer();
            }
        }.start();
        mPomodoroView.changeToogleIcon(R.drawable.ic_stop_white_24dp);
    }

    private void stopTimer() {
        mCountDownTimer.cancel();
        mCountDownTimer = null;
        mPomodoroView.stop(DateFormatUtils.getDateInMinutes((int) mPomodoroDuration));
        mPomodoroView.changeToogleIcon(R.drawable.ic_play_white_24dp);
        mPomodoroView.showNotification(R.string.pomodoro_completed);
    }


}
