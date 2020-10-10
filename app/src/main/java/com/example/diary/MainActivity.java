package com.example.diary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.*;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private DatePicker picker;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);//去掉actionbar下方阴影
        setContentView(R.layout.activity_main);
        dialog = new Dialog(this);
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
        initialization();
        myTool.setActionView(R.layout.my_tool);
        return true;
    }

    /**
     * 初始化
     */
    @SuppressLint("SetTextI18n")
    private void initialization() {
        LinearLayout scrollArea = findViewById(R.id.scrollArea);
        for (int i = 0; i < 20; i++) {
            Button btn = new Button(this);
            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btn.setText(Integer.toString(i));
            scrollArea.addView(btn);
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
