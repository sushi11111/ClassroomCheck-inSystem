package com.example.aclass.teacher;

import com.example.aclass.Constants;
import com.example.aclass.teacher.request.AddClassRequest;
import com.example.aclass.teacher.request.ChangeUserRequest;
import com.example.aclass.teacher.request.CheckingRequest;
import com.example.aclass.teacher.response.*;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;


public interface TeacherService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @POST("/member/sign/course/teacher")
    Call<AddClassResponse> addClass (@Body AddClassRequest request);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @GET("/member/sign/course/all")
    Call<GetClassListResponse> getClass (@Query("current") int current, @Query("size") int size);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @DELETE("/member/sign/course/teacher")
    Call<DeleteClassResponse> deleteClass (@Query("courseId") int courseId, @Query("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @GET("/member/sign/course/teacher/unfinished")
    Call<GetUnFinishClassResponse> getUnFinishClass (@Query("current") int current, @Query("size") int size,  @Query("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @GET("/member/sign/course/teacher/finished")
    Call<GetFinishClassResponse> getFinishClass (@Query("current") int current, @Query("size") int size, @Query("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @POST("/member/sign/course/teacher/initiate")
    Call<CheckingResponse> startChecking (@Body CheckingRequest request);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @GET("/member/sign/course/teacher/page")
    Call<GetCheckDetailResponse> getCheckDetail (@Query("courseId") int courseId, @Query("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "appId: " + Constants.APP_ID,
            "appSecret: " + Constants.APP_SECRET,
            "Content-Type: multipart/form-data"
    })
    @Multipart
    @POST("/member/sign/image/upload")
    Call<UploadImageResponse> uploadImage(@Part MultipartBody.Part image);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: "+ Constants.APP_ID,
            "appSecret: "+Constants.APP_SECRET
    })
    @POST("/member/sign/user/update")
    Call<ChangeUserResponse> changeUser(@Body ChangeUserRequest changeUserRequest);

}
