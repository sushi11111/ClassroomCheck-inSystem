package com.example.aclass.student.Courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aclass.Constants;
import com.example.aclass.Login.LoginResponse;
import com.example.aclass.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentCourseFragment extends Fragment {

    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private List<Course> courseList;
    private List<Course> selectedCourses;

    public static StudentCourseFragment newInstance(LoginResponse.UserData userData) {
        StudentCourseFragment fragment = new StudentCourseFragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_courses, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        courseList = new ArrayList<>();
        selectedCourses = new ArrayList<>();

        //设置item边框
        int verticalSpaceHeight = 10;
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(verticalSpaceHeight);
        recyclerView.addItemDecoration(itemDecoration);

        // 获取传递的数据
        if (getArguments() != null) {
            LoginResponse.UserData userData = getArguments().getParcelable("userData");
            System.out.println("StudentCourses已获得" + userData.getAppKey());

            //要获取全部课程和学生选课
            loadCourses(userData.getId());

            adapter = new CourseAdapter(courseList,selectedCourses,Integer.parseInt(userData.getId()));
            recyclerView.setAdapter(adapter);
            // 通知适配器数据已更改
            adapter.notifyDataSetChanged();
        }
        return view;
    }

    //获取所有课程
    private void loadCourses(String userId) {
        // 创建 Retrofit 实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL) // 替换为实际的基础 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CourseService courseService = retrofit.create(CourseService.class);

        // 获取全部课程
        Call<CourseResponse> allCoursesCall = courseService.getCourses();

        allCoursesCall.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                if (response.isSuccessful()) {
                    System.out.println("获取全部课程成功");
                    courseList.addAll(response.body().getData().getRecords());
                    System.out.println(courseList.get(0).getCourseName());

                    adapter.notifyDataSetChanged();
                    //加载学生选课列表
                    Integer userId_Int = Integer.parseInt(userId);
                    loadSelectedCourses(userId_Int, retrofit);
                } else
                    System.out.println("获取课程失败");
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                // 处理请求失败的情况
            }
        });
    }

    private void loadSelectedCourses(Integer userId, Retrofit retrofit) {

        SelectedCourseService selectedCourseService = retrofit.create(SelectedCourseService.class);

        Call<CourseResponse> selectedCoursesCall = selectedCourseService.getStudentCourses(userId);
        selectedCoursesCall.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                //获取到有选课的
                if (response.body().getMsg().equals("成功")) {
                    selectedCourses.addAll(response.body().getData().getRecords());
                    System.out.println("成功获得学生选课");
                    // 通知适配器数据已更改
                    adapter.notifyDataSetChanged();
                } else {
                    System.out.println("获取学生选课失败，学生还没有选课");
                    // 处理请求失败情况
                }
            }
            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                System.out.println("获取学生选课失败");
                // 处理网络请求失败情况
            }
        });
    }
}
