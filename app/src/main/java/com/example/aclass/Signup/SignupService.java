package com.example.aclass.Signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignupService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: a3981d6f1cb7451e858fafe3b4e0bb7c",
            "appSecret: 6002952ec1e29b6a047d29c00ac704ed0802f"
    })
    @POST("member/sign/user/register")
    Call<SignupResponse> registerUser(@Body SignupRequest request);
}
