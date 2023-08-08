package com.example.aclass.Signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignupService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @POST("/member/sign/user/register")
    Call<SignupResponse> registerUser(@Body SignupRequest request);
}
