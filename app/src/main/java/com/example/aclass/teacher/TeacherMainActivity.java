package com.example.aclass.teacher;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aclass.InfoFragment;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeacherMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_view);
        fragmentContainer = findViewById(R.id.fragment_container);
        // 获取传递的数据
        Intent intent = getIntent();
        if (intent != null) {
            LoginResponse.UserData userData = intent.getParcelableExtra("userData");

            // 设置底部导航栏选项点击事件
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.menu_check_in:
                        fragment = TeacherCheckFragment.newInstance(userData,0); // 传递数据给 OtherFragment
                        break;
                    case R.id.menu_courses:
                        fragment = TeacherCourseFragment.newInstance(userData); // 传递数据给 OtherFragment
                        break;
                    case R.id.menu_guy:
                        fragment = InfoFragment.newInstance(userData); // 传递数据给 OtherFragment
                        break;
                    // 添加其他选项的处理逻辑
                }

                if (fragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.commit();
                    return true;
                }
                return false;
            });

            // 默认显示第二个选项的 Fragment
            bottomNavigationView.setSelectedItemId(R.id.menu_courses);
        }

    }
}
