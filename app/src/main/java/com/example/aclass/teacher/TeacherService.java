package com.example.aclass.teacher;

import com.example.aclass.teacher.request.AddClassRequest;
import com.example.aclass.teacher.request.CheckingRequest;
import com.example.aclass.teacher.response.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface TeacherService {
    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @POST("/member/sign/course/teacher")
    Call<AddClassResponse> addClass (@Body AddClassRequest request);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @GET("/member/sign/course/all")
    Call<GetClassListResponse> getClass (@Field("current") int current,@Field("size") int size);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @DELETE("/member/sign/course/teacher")
    Call<DeleteClassResponse> deleteClass (@Field("courseId") int courseId, @Field("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @GET("/member/sign/course/teacher/unfinished")
    Call<GetUnFinishClassResponse> getUnFinishClass (@Field("current") int current,@Field("size") int size, @Field("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @GET("/member/sign/course/teacher/finished")
    Call<GetFinishClassResponse> getFinishClass (@Field("current") int current,@Field("size") int size, @Field("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @POST("/member/sign/course/teacher/initiate")
    Call<CheckingResponse> startChecking (@Body CheckingRequest request);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @GET("/member/sign/course/teacher/page")
    Call<GetCheckDetailResponse> getCheckDetail (@Field("courseId") int courseId, @Field("userId") int userId);
}
