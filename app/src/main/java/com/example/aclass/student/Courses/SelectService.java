package com.example.aclass.student.Courses;

import com.example.aclass.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SelectService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @POST("member/sign/course/student/select")
    Call<ShortResponse> selectCourses(@Query("courseId") Integer courseId, @Query("userId") Integer userId);
}
