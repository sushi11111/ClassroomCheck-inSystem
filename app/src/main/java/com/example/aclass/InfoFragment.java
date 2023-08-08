package com.example.aclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.aclass.Login.LoginResponse;

public class InfoFragment extends Fragment {
    public static InfoFragment newInstance(LoginResponse.UserData userData) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取传递的数据
        if (getArguments() != null) {
            LoginResponse.UserData userData = getArguments().getParcelable("userData");
            System.out.println("InfoFragment11111已获得"+userData.getAppKey());
            // 在布局中显示数据
            // ...

        }

        return inflater.inflate(R.layout.fragment_information, container, false);
    }
}
