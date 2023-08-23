package com.example.aclass.student.CheckIn;

import com.example.aclass.Constants;
import com.example.aclass.student.Courses.ShortResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CheckInService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })

    @POST("member/sign/course/student/sign")
    Call<ShortResponse>StudentCheckIn
            (@Body CheckInRequest request);
}
