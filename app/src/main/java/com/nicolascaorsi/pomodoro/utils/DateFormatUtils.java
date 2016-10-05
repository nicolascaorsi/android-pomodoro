package com.nicolascaorsi.pomodoro.utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import com.nicolascaorsi.pomodoro.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by nicolas on 10/2/16.
 */

public class DateFormatUtils {
    public static String getFormattedDate(Context context, long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) && now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return context.getResources().getString(R.string.today);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1 && now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return context.getResources().getString(R.string.yesterday);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format("dd/MM", smsTime).toString();
        } else {
            return DateFormat.format("dd/MM/yy", smsTime).toString();
        }
    }

    public static String getDateInMinutes(long time) {
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1);
        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds);
        }
    }

    public static CharSequence getFriendlyDate(Context context, long time) {
        if (DateUtils.isToday(time)) {
            return context.getResources().getString(R.string.today);
        }
        return DateUtils.getRelativeTimeSpanString(time);
    }
}
