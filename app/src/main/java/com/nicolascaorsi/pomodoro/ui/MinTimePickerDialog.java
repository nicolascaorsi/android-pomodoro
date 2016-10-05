package com.nicolascaorsi.pomodoro.ui;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by nicolas on 10/5/16.
 */

public class MinTimePickerDialog extends TimePickerDialog {
    private Button mOkButton;

    public MinTimePickerDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute,
                               boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkButton = this.getButton(DialogInterface.BUTTON_POSITIVE);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        mOkButton.setEnabled(hourOfDay != 0 || minute != 0);
    }

}