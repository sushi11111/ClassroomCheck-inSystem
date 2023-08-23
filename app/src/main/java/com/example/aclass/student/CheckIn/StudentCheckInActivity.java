package com.example.aclass.student.CheckIn;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.example.aclass.student.Courses.StudentCourseFragment;

public class StudentCheckInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_student_check_in);

        // 获取传递给 Fragment 的数据
        LoginResponse.UserData userData = getIntent().getParcelableExtra("userData");

        if (savedInstanceState == null) {
            // 在 Activity 中加载 Fragment，并传递数据
            StudentCheckInFragment fragment = StudentCheckInFragment.newInstance(userData);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
