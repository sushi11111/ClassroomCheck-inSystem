package com.example.aclass.student.Courses;

import com.example.aclass.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SelectedCourseService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })

    @GET("member/sign/course/student")
    Call<CourseResponse> getStudentCourses(@Query("userId") Integer userId);
}
