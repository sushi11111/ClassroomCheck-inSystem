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
            "appId: a3981d6f1cb7451e858fafe3b4e0bb7c",
            "appSecret: 6002952ec1e29b6a047d29c00ac704ed0802f"
    })
    @FormUrlEncoded
    @POST("member/sign/user/login")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);
}
