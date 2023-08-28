package com.example.aclass.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aclass.Constants;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.example.aclass.teacher.response.GetFinishClassResponse;
import com.example.aclass.teacher.response.GetUnFinishClassResponse;
import com.example.aclass.teacher.response.vo.RecordAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class TeacherCourseFragment extends Fragment {

    ImageView addCourseBtn;
    private FragmentManager fragmentManager;

    public static TeacherCourseFragment newInstance(LoginResponse.UserData userData) {
        TeacherCourseFragment fragment = new TeacherCourseFragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 使用 inflater.inflate() 方法创建并返回一个有效的 View 对象
        View rootView = inflater.inflate(R.layout.fragment_teacher_course, container, false);
        //fragment管理器设置
        fragmentManager = getFragmentManager();
        //展示教师未结算的课程
        RecyclerView recyclerUnFinishCourse = rootView.findViewById(R.id.unFinishCourse);
        recyclerUnFinishCourse.setLayoutManager(new LinearLayoutManager(getContext()));
        //按钮赋值
        addCourseBtn = rootView.findViewById(R.id.addCourseBtn);
        System.out.println("进入");
        if (getArguments() != null) {
            LoginResponse.UserData userData = getArguments().getParcelable("userData");
            System.out.println("CourseFragment已获得"+userData.getEmail());
            unFinishCourse(0,10,Integer.parseInt(userData.getId()),recyclerUnFinishCourse,userData);
            addCourseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 创建一个Intent对象，指定当前活动为上下文，目标活动为要跳转的活动类
                    Intent intent = new Intent(v.getContext(), AddCourseActivity.class);
                    // 添加需要传递的参数，例如：
                    intent.putExtra("userData", userData);
                    // 启动目标活动
                    v.getContext().startActivity(intent);
                }
            });
        }

        return rootView;
    }

    private void finishCourse(int current ,int size ,int userId)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TeacherService teacherService = retrofit.create(TeacherService.class);
        Call<GetFinishClassResponse> call = teacherService.getFinishClass(current, size, userId);
        call.enqueue(new Callback<GetFinishClassResponse>(){

            @Override
            public void onResponse(Call<GetFinishClassResponse> call, Response<GetFinishClassResponse> response) {
                if (response.isSuccessful()){
                    GetFinishClassResponse getFinishClassResponse = response.body();
                    if (getFinishClassResponse != null && getFinishClassResponse.getCode() == 200) {
                        System.out.println("当前教师课程请求数据为："+getFinishClassResponse.getData().toString());

                    }
                    else {
                        System.out.println("错误码:"+getFinishClassResponse.getCode()+"原因:"+getFinishClassResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetFinishClassResponse> call, Throwable t) {
                System.out.println("请求失败：" + t.getMessage());
            }
        });
    }
    private void unFinishCourse(int current ,int size ,int userId ,RecyclerView recyclerView,LoginResponse.UserData userData)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TeacherService teacherService = retrofit.create(TeacherService.class);
        Call<GetUnFinishClassResponse> call = teacherService.getUnFinishClass(current, size, userId);
        call.enqueue(new Callback<GetUnFinishClassResponse>(){

            @Override
            public void onResponse(Call<GetUnFinishClassResponse> call, Response<GetUnFinishClassResponse> response) {
                if (response.isSuccessful()){
                    GetUnFinishClassResponse getUnFinishClassResponse = response.body();
                    if (getUnFinishClassResponse != null && getUnFinishClassResponse.getCode() == 200) {
                        System.out.println("当前教师课程请求数据为："+getUnFinishClassResponse.getData());
                        GetUnFinishClassResponse.Data data = getUnFinishClassResponse.getData();
                        List<GetUnFinishClassResponse.Record> records = data.getRecords();
                        for (GetUnFinishClassResponse.Record element : records) {
                            System.out.println("课："+element);
                        }
                        RecordAdapter adapter = new RecordAdapter(records,userData,fragmentManager);
                        recyclerView.setAdapter(adapter);
                    }
                    else {
                        System.out.println("错误码:"+getUnFinishClassResponse.getCode()+"原因:"+getUnFinishClassResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUnFinishClassResponse> call, Throwable t) {
                System.out.println("请求失败：" + t.getMessage());
            }
        });
    }
}
