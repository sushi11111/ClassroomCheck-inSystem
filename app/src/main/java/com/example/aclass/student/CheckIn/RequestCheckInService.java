package com.example.aclass.student.CheckIn;

import com.example.aclass.Constants;
import com.example.aclass.student.Courses.CourseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RequestCheckInService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })

    @GET("member/sign/course/student/signList")
    Call<CheckInResponse> requestCheckInCourses(@Query("courseId") Integer courseId, @Query("status")Integer status, @Query("userId") Integer userId);
}
