package com.example.aclass.student.CheckIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.Constants;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;
import com.example.aclass.student.Courses.Course;
import com.example.aclass.student.Courses.CourseAdapter;
import com.example.aclass.student.Courses.CourseResponse;
import com.example.aclass.student.Courses.SelectedCourseService;
import com.example.aclass.student.Courses.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentCheckInFragment extends Fragment {

    private RecyclerView recyclerView;
    Integer userId;
    private StudentCheckInAdapter adapter;
    private List<CheckInCourse> yesCourseList;
    private List<CheckInCourse> noCourseList;
    private List<Course> selectedCourses;

    private static boolean flag1;
    private static boolean flag2;

    public static StudentCheckInFragment newInstance(LoginResponse.UserData userData) {
        StudentCheckInFragment fragment = new StudentCheckInFragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_check_in, container, false);
        recyclerView = view.findViewById(R.id.recycler_checkIn_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        yesCourseList = new ArrayList<>();
        noCourseList = new ArrayList<>();
        selectedCourses = new ArrayList<>();

        //设置item边框
        int verticalSpaceHeight = 10;
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(verticalSpaceHeight);
        recyclerView.addItemDecoration(itemDecoration);

        // 获取传递的数据
        if (getArguments() != null) {
            LoginResponse.UserData userData = getArguments().getParcelable("userData");
            System.out.println("StudentCheckInFragment已获得" + userData.getAppKey());
            // 在布局中显示数据
            // 进行网络请求和数据加载
            adapter = new StudentCheckInAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), userId);
            recyclerView.setAdapter(adapter);

            userId = Integer.parseInt(userData.getId());
            loadData(userId);
        }

        return view;
    }

    private void loadData(Integer userId){
        //该学生获取所加入的课程
        // 创建 Retrofit 实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL) // 替换为实际的基础 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SelectedCourseService selectedCourseService = retrofit.create(SelectedCourseService.class);
        Call<CourseResponse> selectedCoursesCall = selectedCourseService.getStudentCourses(userId);
        selectedCoursesCall.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                System.out.println(response.body().getMsg());
//                if (response.isSuccessful()){
                if(response.body().getMsg().equals("成功"))
                {
                    System.out.println("成功获得学生选课");
                    selectedCourses.addAll(response.body().getData().getRecords());
                }
//                        System.out.println(selectedCourses.get(0).getCollegeName());
//                        System.out.println(selectedCourses.size());
                // 未选课程
                loadNoCourse(selectedCourses,retrofit,userId);
//                } else {
//                    System.out.println("获取学生选课失败");
//                    // 处理请求失败情况
//                }
            }
            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                System.out.println("获取学生选课失败");
                // 处理网络请求失败情况
            }
        });

    }

    public void loadNoCourse(List<Course> selectedCourses,Retrofit retrofit,Integer usrId){
        RequestCheckInService requestCheckInService = retrofit.create(RequestCheckInService.class);
        for (int i = 0; i < selectedCourses.size(); i++) {
            Call<CheckInResponse> responseCall =
                    requestCheckInService.requestCheckInCourses(Integer.parseInt(selectedCourses.get(i).getCourseId()),0,usrId);
            responseCall.enqueue(new Callback<CheckInResponse>(){
                @Override
                public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                        List<CheckInCourse> records = response.body().getData().getRecords();
                        if (records != null && records.size() > 0) {
                            noCourseList.add(records.get(0));
                            System.out.println(records.get(0).getCourseAddr());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                @Override
                public void onFailure(Call<CheckInResponse> call, Throwable t) {
                    System.out.println("获取学生未选课程失败");
                }
            });
        }
        loadYesCourse(requestCheckInService,selectedCourses,retrofit,usrId);
    }

    public void loadYesCourse(RequestCheckInService requestCheckInService,List<Course> selectedCourses,Retrofit retrofit,Integer usrId){
        for (int i = 0; i < selectedCourses.size(); i++) {
            Call<CheckInResponse> responseCall =
                    requestCheckInService.requestCheckInCourses(Integer.parseInt(selectedCourses.get(i).getCourseId()),1,usrId);
            responseCall.enqueue(new Callback<CheckInResponse>(){
                @Override
                public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                        List<CheckInCourse> records = response.body().getData().getRecords();
                        if (records != null && records.size() > 0) {
                            yesCourseList.add(records.get(0));
                            System.out.println(records.get(0).getCourseAddr());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                @Override
                public void onFailure(Call<CheckInResponse> call, Throwable t) {
                    System.out.println("获取学生已选课程失败");
                }
            });
        }

        adapter = new StudentCheckInAdapter(yesCourseList,noCourseList,selectedCourses,userId);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //System.out.println("noCourseList.size() = "+noCourseList.size());
    }
}
