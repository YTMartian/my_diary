package com.example.diary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.view.*;
import android.widget.*;
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
            addYear(year, false);
        }
    }

    /**
     * 添加一年的日记
     *
     * @param year
     */
    @SuppressLint({"SetTextI18n", "WrongConstant"})
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addYear(int year, boolean toTop) {
        Calendar c = Calendar.getInstance();
        for (int month = 0; month < 12; month++) {
            GridLayout gl = new GridLayout(this);
            FrameLayout.LayoutParams params =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            gl.setLayoutParams(params);
            gl.setOrientation(GridLayout.VERTICAL);
            gl.setAlignmentMode(View.TEXT_ALIGNMENT_CENTER);
            c.set(year, month, 1);//月份从0开始
            int totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//            System.out.println(totalDays + "***" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
//            System.out.println(c.get(Calendar.DAY_OF_WEEK));
            TextView tv = new TextView(this);
            tv.setBackgroundColor(getResources().getColor(R.color.colorDark));
            tv.setTextColor(getResources().getColor(R.color.colorWhite2));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(18);
            tv.setPadding(0, 10, 0, 10);
            tv.setText(months_name[month] + " " + Integer.toString(year));
            gl.addView(tv);
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setPadding(0, 1, 0, 0);
            //第一周前加入上一个月的最后几天
            MyButton btn0 = new MyButton(this);
            btn0.setText(Integer.toString(month + 1) + "-" + Integer.toString(1));
            btn0.setTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
            c.add(Calendar.MONTH, -1);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH));
            int week = btn0.dayOfTheWeek;//当前周已经有多少天了
            for (int i = btn0.dayOfTheWeek - 1; i >= 1; i--) {
                MyButton btn1 = new MyButton(this);
                btn1.setText(Integer.toString(c.get(Calendar.MONTH) + 1) + "-" + Integer.toString(c.getActualMaximum(Calendar.DAY_OF_MONTH) - i + 1));
                btn1.setTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH) - i + 1);
                ll.addView(btn1);
            }
            ll.addView(btn0);
            c.add(Calendar.MONTH, 1);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
            for (int day = 2; day <= totalDays; day++) {
                if (week == 7) {
                    gl.addView(ll);
                    ll = new LinearLayout(this);
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ll.setPadding(0, 1, 0, 0);
                    week = 0;
                }
                MyButton btn = new MyButton(this);
                btn.setText(Integer.toString(month + 1) + "-" + Integer.toString(day));
                btn.setTime(year, month + 1, day);
                ll.addView(btn);
                week++;
            }
            //最后一周后加入下一个月前几天
            if (week == 7) {
                gl.addView(ll);
            } else {
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
                c.add(Calendar.MONTH, 1);
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
                btn0 = new MyButton(this);
                btn0.setText(Integer.toString(c.get(Calendar.MONTH) + 1) + "-" + Integer.toString(1));
                btn0.setTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 1);
                for (int i = btn0.dayOfTheWeek; i <= 7; i++) {
                    MyButton btn1 = new MyButton(this);
                    btn1.setText(Integer.toString(c.get(Calendar.MONTH) + 1) + "-" + Integer.toString(1 + i - btn0.dayOfTheWeek));
                    btn1.setTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 1 + i - btn0.dayOfTheWeek);
                    ll.addView(btn1);
                }
                gl.addView(ll);
            }
            scrollArea.addView(gl, toTop ? 0 : -1);
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
