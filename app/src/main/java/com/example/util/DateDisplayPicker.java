package com.example.util;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;


public class DateDisplayPicker extends AppCompatTextView implements DatePickerDialog.OnDateSetListener {

    private Context _context;
    private int day, month, year;
    private Calendar calendar;


    public DateDisplayPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        _context = context;
    }

    public DateDisplayPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        _context = context;
        setAttributes();
    }

    public DateDisplayPicker(Context context) {
        super(context);
        _context = context;
        setAttributes();
    }

    private void setAttributes() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(_context, this, year, month, day);
        dp.show();
    }

    @Override
    public void onDateSet(DatePicker view, int yearParam, int monthParam,
                          int dayParam) {

        day = dayParam;
        month = monthParam;
        year = yearParam;

        setText(new StringBuilder().append(dayParam).append("/")
                .append(monthParam + 1).append("/").append(yearParam));
    }
}
