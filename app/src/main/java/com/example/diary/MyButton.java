package com.example.diary;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Calendar;

public class MyButton extends androidx.appcompat.widget.AppCompatButton {
    public int year;
    public int month;
    public int day;
    public int dayOfTheWeek;


    public MyButton(Context context) {
        this(context, null);
        this.year = -1;
        this.month = -1;
        this.day = -1;
        this.dayOfTheWeek = -1;
        this.setBackgroundColor(getResources().getColor(R.color.colorGrayDeep));
        this.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    public void setTime(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        this.dayOfTheWeek = c.get(Calendar.DAY_OF_WEEK) + 3;
        //dayOfTheWeek:Mon Tue Wed Thu Fri Sat Sun
        //value       :5 6 7 1 2 3 4
        if (this.dayOfTheWeek > 7) this.dayOfTheWeek -= 7;
    }

    public MyButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
