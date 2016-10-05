package com.nicolascaorsi.pomodoro.timer;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nicolascaorsi.pomodoro.R;
import com.nicolascaorsi.pomodoro.data.PomodorosRepository;
import com.nicolascaorsi.pomodoro.timer.history.HistoryFragment;
import com.nicolascaorsi.pomodoro.timer.history.HistoryPresenter;

class PageAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private PomodorosRepository mPomodoroRepository;

    PageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mPomodoroRepository = new PomodorosRepository();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                HistoryFragment historyFragment = HistoryFragment.newInstance();
                new HistoryPresenter(mPomodoroRepository, historyFragment);
                return historyFragment;
            default:
                TimerFragment timerFragment = TimerFragment.newInstance();
                new TimerPresenter(PreferenceManager.getDefaultSharedPreferences(mContext), mPomodoroRepository, timerFragment);
                return timerFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return mContext.getResources().getString(R.string.history_tab);
            default:
                return mContext.getResources().getString(R.string.timer_tab);
        }
    }
}


