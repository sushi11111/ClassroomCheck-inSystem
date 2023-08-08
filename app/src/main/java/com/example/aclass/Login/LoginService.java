package com.example.aclass.Login;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/x-www-form-urlencoded",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @FormUrlEncoded
    @POST("/member/sign/user/login")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);
}
