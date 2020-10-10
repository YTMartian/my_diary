package com.example.diary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.view.*;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DatePicker picker;
    private Dialog dialog;
    private LinearLayout scrollArea;
    private String[] months_name;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);//去掉actionbar下方阴影
        months_name = new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        setContentView(R.layout.activity_main);
        dialog = new Dialog(this);
        scrollArea = findViewById(R.id.scrollArea);
        initialization();
    }

    /**
     * 设置顶部菜单，top_menu.xml和my_tool.xml
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        ((MenuInflater) menuInflater).inflate(R.menu.top_menu, menu);
        MenuItem myTool = menu.findItem(R.id.my_tool);
        myTool.setActionView(R.layout.my_tool);
        return true;
    }

    /**
     * 初始化
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initialization() {
        Calendar current = Calendar.getInstance();
        int currentYear = current.get(Calendar.YEAR);
        for (int year = currentYear - 1; year <= currentYear + 1; year++) {
            addYear(year);
        }
    }

    /**
     * 添加一年的日记
     *
     * @param year
     */
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addYear(int year) {
        Calendar c = Calendar.getInstance();
        for (int month = 0; month < 12; month++) {
            c.set(year, month, 1);//月份从0开始
            int totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//            System.out.println(totalDays + "***" + c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH));
            TextView tv = new TextView(this);
            tv.setBackgroundColor(getResources().getColor(R.color.colorDark));
            tv.setTextColor(getResources().getColor(R.color.colorWhite2));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(18);
            tv.setPadding(0, 10, 0, 10);
            tv.setText(months_name[month] + " " + Integer.toString(year));
//            scrollArea.addView(tv);
            LinearLayout ll = new LinearLayout(this);

            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setPadding(0, 1, 0, 0);
            for (int day = 1; day <= totalDays; day++) {
                MyButton btn = new MyButton(this);
                btn.setText(Integer.toString(month) + "-" + Integer.toString(day));
                btn.setTime(year, month, day);
                ll.addView(btn);
            }
            scrollArea.addView(ll);
        }
    }

    public void export_button(View view) {
        System.out.println("fuck export");
    }

    public void calender_button(View view) {
        dialog.setContentView(R.layout.popup_calendar);
        dialog.show();
        System.out.println("fuck calendar");
        picker = (DatePicker) findViewById(R.id.my_date_picker);
        System.out.println(picker);
        System.out.println(R.id.my_date_picker);
    }

    public void settings_button(View view) {
        System.out.println("settings calender");
    }

    public void datePickOkButton(View view) {
        picker = (DatePicker) dialog.findViewById(R.id.my_date_picker);
        if (picker == null) {
            return;
        }
        dialog.hide();
        int year = picker.getYear();
        int month = picker.getMonth();
        int day = picker.getDayOfMonth();
    }

    /**
     * 隐藏日历选择dialog
     *
     * @param view
     */
    public void datePickCancelButton(View view) {
        if (dialog != null) {
            dialog.hide();
        }
    }
}
