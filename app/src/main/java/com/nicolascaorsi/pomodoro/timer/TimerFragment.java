package com.nicolascaorsi.pomodoro.timer;

import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nicolascaorsi.pomodoro.R;
import com.nicolascaorsi.pomodoro.ui.MinTimePickerDialog;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class TimerFragment extends Fragment implements TimerContract.View {

    private FloatingActionButton mToggleButton;
    private ProgressBar mProgressBar;
    private TextView mChronometer;
    private TimerContract.Presenter mPresenter;
    private ObjectAnimator mProgressBarAnimation;

    public TimerFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TimerFragment.
     */
    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.timer_fragment, container, false);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        mChronometer = (TextView) v.findViewById(R.id.chronometer);
        mToggleButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.togglePomodoro();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_pomodoro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.onUpdateDurationSelected();
        return true;
    }

    @Override
    public void init(String chronometerText) {
        mChronometer.setText(chronometerText);
    }

    @Override
    public void start(String text, long maxProgress) {
        mChronometer.setText(text);
        mProgressBar.setMax((int)maxProgress);
        mProgressBar.setProgress((int)maxProgress);

        mProgressBarAnimation = ObjectAnimator.ofInt(mProgressBar, "progress", 0);
        mProgressBarAnimation.setDuration(maxProgress);
        mProgressBarAnimation.setInterpolator(new LinearInterpolator());
        mProgressBarAnimation.start();
    }

    @Override
    public void stop(String text) {
        mChronometer.setText(text);
        mProgressBarAnimation.cancel();
        mProgressBarAnimation = ObjectAnimator.ofInt(mProgressBar, "progress", mProgressBar.getMax());
        mProgressBarAnimation.setDuration(500);
        mProgressBarAnimation.setInterpolator(new AccelerateInterpolator());
        mProgressBarAnimation.start();
    }

    @Override
    public void updateProgress(String title, long progress) {
        mChronometer.setText(title);
    }

    @Override
    public void changeToogleIcon(@DrawableRes int resourceId) {
        mToggleButton.setImageResource(resourceId);
    }

    @Override
    public void showDurationSelectionDialog(long currentDuration) {

        int hours = (int) TimeUnit.MILLISECONDS.toHours(currentDuration);
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(currentDuration) % TimeUnit.HOURS.toMinutes(1));


        TimePickerDialog dialog = new MinTimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mPresenter.updateDuration(TimeUnit.MINUTES.toMillis(minute) + TimeUnit.HOURS.toMillis(hourOfDay));

            }
        }, hours, minutes, true);
        dialog.show();
    }

    @Override
    public void showNotification(@StringRes int resourceId) {
        Notification notification = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_notification_small)
                .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.ic_launcher))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(resourceId))
                .build();
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    @Override
    public void setPresenter(TimerContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
