package com.example.aclass.Login;


import com.example.aclass.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/x-www-form-urlencoded",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @FormUrlEncoded
    @POST("/member/sign/user/login")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);
}
