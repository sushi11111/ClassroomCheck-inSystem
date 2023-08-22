package com.example.aclass.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;

public class StartingCheckActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_check);
        System.out.println("跳转到发起签到页面...");
        // 获取传递的参数值
//        Intent intent = getIntent();
//        String value = intent.getStringExtra("key");

    }
}
