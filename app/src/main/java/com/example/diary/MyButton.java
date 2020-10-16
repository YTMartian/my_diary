package com.example.diary;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import androidx.annotation.RequiresApi;

import java.util.Calendar;


public class MyButton extends androidx.appcompat.widget.AppCompatButton {
    public int year;
    public int month;
    public int day;
    public int dayOfTheWeek;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public MyButton(Context context) {
        this(context, null);
        this.year = -1;
        this.month = -1;
        this.day = -1;
        this.dayOfTheWeek = -1;
        this.setTextColor(getResources().getColor(R.color.colorWhite));
        this.setBackgroundDrawable(getResources().getDrawable(R.drawable.write_done));
        this.setTextAlignment(TEXT_ALIGNMENT_CENTER);//水平居中
        this.setGravity(Gravity.CENTER);//垂直居中
        this.setTextSize(15);
        LinearLayout.LayoutParams btParam = new LinearLayout.LayoutParams(-1, -1);
        btParam.height = LinearLayout.LayoutParams.MATCH_PARENT;
        btParam.leftMargin = 5;
        btParam.bottomMargin = 5;
        btParam.weight = 1;
        this.setLayoutParams(btParam);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setTime(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        this.dayOfTheWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        //dayOfTheWeek:Mon Tue Wed Thu Fri Sat Sun
        //value       :2 3 4 5 6 7 1
        if (this.dayOfTheWeek == 0) this.dayOfTheWeek = 7;
        Calendar cur = Calendar.getInstance();
        if (cur.get(Calendar.YEAR) == this.year && cur.get(Calendar.MONTH) == this.month - 1 && cur.get(Calendar.DAY_OF_MONTH) == this.day) {
            //通过AI调整圆的大小
            this.setForeground(getResources().getDrawable(R.drawable.ic_circle));
        }
    }

    public MyButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置按钮长宽一一致
     * https://stackoverflow.com/questions/29701234/set-button-height-equal-to-the-button-width
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

}
