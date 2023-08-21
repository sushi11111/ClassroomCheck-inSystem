package com.example.aclass.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;

public class TeacherCheckFragment extends Fragment {
    public static TeacherCheckFragment newInstance(LoginResponse.UserData userData) {
        TeacherCheckFragment fragment = new TeacherCheckFragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 使用 inflater.inflate() 方法创建并返回一个有效的 View 对象
        View rootView = inflater.inflate(R.layout.fragment_teacher_check, container, false);
        if (getArguments() != null) {
            LoginResponse.UserData userData = getArguments().getParcelable("userData");
            System.out.println("CheckFragment已获得"+userData.getEmail());
        }
        return rootView;
    }
}
