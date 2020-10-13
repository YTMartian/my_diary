package com.example.diary;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private DatePicker picker;
    private Dialog dialog;
    private LinearLayout scrollArea;
    private ScrollView scrollView;
    private String[] months_name;
    private int startYear;
    private int lastYear;
    private int currentYear;
    private Calendar curDate;
    private int currentMonthScrollPosition;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);//去掉顶部actionbar下方阴影
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorDark));
        months_name = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月",
                "八月", "九月", "十月", "十一月", "十二月"};
        setContentView(R.layout.activity_main);
        dialog = new Dialog(this);
        scrollArea = findViewById(R.id.scrollArea);
        scrollView = findViewById(R.id.scrollView);
        curDate = Calendar.getInstance();
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
//        scrollView.setOnTouchListener(this);
//        scrollView.getViewTreeObserver().addOnScrollChangedListener((ViewTreeObserver.OnScrollChangedListener) this);
        Calendar current = Calendar.getInstance();
        currentYear = current.get(Calendar.YEAR);
        startYear = currentYear - 1;
        lastYear = currentYear + 1;
        for (int year = currentYear - 1; year <= currentYear + 1; year++) {
            addYear(year, false);
        }
        System.out.println("fuck  " + currentMonthScrollPosition);
        scrollView.scrollTo(0, currentMonthScrollPosition);
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
            //获取当前日期所处位置
            System.out.println(scrollView.getMeasuredHeight() + "***************");
            if (currentYear == year && month == curDate.get(Calendar.MONTH)) {
                currentMonthScrollPosition = scrollView.getMaxScrollAmount();
            }

            GridLayout gl = new GridLayout(this);
            gl.setOrientation(GridLayout.VERTICAL);
            gl.setAlignmentMode(View.TEXT_ALIGNMENT_CENTER);
            c.set(year, month, 1);//月份从0开始
            int totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//            System.out.println(totalDays + "***" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
            //显示月份信息
            TextView tv = new TextView(this);
            tv.setBackgroundColor(getResources().getColor(R.color.colorDark));
            tv.setTextColor(getResources().getColor(R.color.colorWhite2));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(18);
            tv.setText(months_name[month] + " " + Integer.toString(year));
            GridLayout.LayoutParams tvParam = new GridLayout.LayoutParams();
            tvParam.height = GridLayout.LayoutParams.MATCH_PARENT;
            tvParam.width = GridLayout.LayoutParams.MATCH_PARENT;
            tvParam.setGravity(Gravity.CENTER);
            tvParam.topMargin = 60;
            tvParam.bottomMargin = 25;
            tv.setLayoutParams(tvParam);
            gl.addView(tv);

            LinearLayout ll = new LinearLayout(this);
            GridLayout.LayoutParams llParam = new GridLayout.LayoutParams();
            llParam.height = GridLayout.LayoutParams.WRAP_CONTENT;
            llParam.width = GridLayout.LayoutParams.MATCH_PARENT;
            ll.setLayoutParams(llParam);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            //第一周前加入上一个月的最后几天
            MyButton btn0 = new MyButton(this);
            btn0.setHeight(btn0.getWidth());
            btn0.setText(Integer.toString(1));
            btn0.setTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
            addClickEvent(btn0);
            c.add(Calendar.MONTH, -1);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH));
            int week = btn0.dayOfTheWeek;//当前周已经有多少天了
            for (int i = btn0.dayOfTheWeek - 1; i >= 1; i--) {
                MyButton btn1 = new MyButton(this);
                btn1.setText(Integer.toString(c.getActualMaximum(Calendar.DAY_OF_MONTH) - i + 1));
                btn1.setTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH) - i + 1);
                btn1.setTextColor(getResources().getColor(R.color.colorGray));
                btn1.setBackgroundColor(getResources().getColor(R.color.colorDark));
                ll.addView(btn1);
                addClickEvent(btn1);
            }
            ll.addView(btn0);
            c.add(Calendar.MONTH, 1);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
            for (int day = 2; day <= totalDays; day++) {
                if (week == 7) {
                    gl.addView(ll);
                    ll = new LinearLayout(this);
                    llParam = new GridLayout.LayoutParams();
                    llParam.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    llParam.width = GridLayout.LayoutParams.MATCH_PARENT;
                    ll.setLayoutParams(llParam);
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    week = 0;
                }
                MyButton btn = new MyButton(this);
                btn.setText(Integer.toString(day));
                btn.setTime(year, month + 1, day);
                ll.addView(btn);
                addClickEvent(btn);
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
                btn0.setTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 1);
                for (int i = btn0.dayOfTheWeek; i <= 7; i++) {
                    MyButton btn1 = new MyButton(this);
                    btn1.setText(Integer.toString(1 + i - btn0.dayOfTheWeek));
                    btn1.setTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 1 + i - btn0.dayOfTheWeek);
                    btn1.setTextColor(getResources().getColor(R.color.colorGray));
                    btn1.setBackgroundColor(getResources().getColor(R.color.colorDark));
                    ll.addView(btn1);
                    addClickEvent(btn1);
                }
                gl.addView(ll);
            }
            scrollArea.addView(gl, toTop ? 0 : -1);
        }
    }

    public void addClickEvent(MyButton btn) {
        btn.setOnClickListener(new MyOnClickListener(btn.year, btn.month, btn.day,
                btn.dayOfTheWeek, this));
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

    /**
     * ScrollView监视
     */
    public void onScrollChanged() {
        View view = scrollArea.getChildAt(scrollArea.getChildCount() - 1);
        int topDetector = scrollArea.getScrollY();
        int bottomDetector = view.getBottom() - (scrollArea.getHeight() + scrollArea.getScrollY());
        if (bottomDetector == 0) {
            Toast.makeText(getBaseContext(), "Scroll View bottom reached", Toast.LENGTH_SHORT).show();
        }
        if (topDetector <= 0) {
            Toast.makeText(getBaseContext(), "Scroll View top reached", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 实现View.OnTouchListener接口
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public class MyOnClickListener implements View.OnClickListener {

        private int year = -1;
        private int month = -1;
        private int day = -1;
        private int dayOfTheWeek = -1;
        private Context context;

        public MyOnClickListener(int year, int month, int day, int dayOfTheWeek, Context context) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.dayOfTheWeek = dayOfTheWeek;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
//            System.out.println(scrollView.getScrollY());
//            scrollView.scrollTo(0,1100);
            new AlertDialog.Builder(context)
                    .setTitle("日期")
                    .setMessage(this.year + "-" + this.month + "-" + this.day)
                    .setPositiveButton("OK", null)
                    .show();
        }


    }
}
