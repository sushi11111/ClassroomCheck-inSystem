package com.example.aclass.student.Courses;

import com.example.aclass.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DeleteService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })

    @DELETE("member/sign/course/student/drop")
    Call<ShortResponse> deleteCourses(
            @Query("courseId") Integer courseId,
            @Query("userId") Integer userId
    );
}

