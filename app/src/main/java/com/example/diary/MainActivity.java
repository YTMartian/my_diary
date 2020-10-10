package com.example.diary;

import android.view.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);//去掉actionbar下方阴影
        setContentView(R.layout.activity_main);
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

    public void export_button(View view) {
        System.out.println("fuck export");
    }

    public void calender_button(View view) {
        System.out.println("fuck calender");
    }
}
