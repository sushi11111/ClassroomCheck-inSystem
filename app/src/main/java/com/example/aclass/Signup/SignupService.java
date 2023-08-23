package com.example.aclass.Signup;

import com.example.aclass.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignupService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @POST("/member/sign/user/register")
    Call<SignupResponse> registerUser(@Body SignupRequest request);
}
