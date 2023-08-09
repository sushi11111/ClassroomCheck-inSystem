package com.example.aclass.teacher;

import com.example.aclass.teacher.request.AddClassRequest;
import com.example.aclass.teacher.response.AddClassResponse;
import com.example.aclass.teacher.response.DeleteClassResponse;
import com.example.aclass.teacher.response.GetClassListResponse;
import com.example.aclass.teacher.response.GetUnFinishClassResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
    @POST("/member/sign/course/all")
    Call<GetClassListResponse> getClass (@Field("current") int current,@Field("size") int size);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @POST("/member/sign/course/all")
    Call<DeleteClassResponse> deleteClass (@Field("courseId") int courseId, @Field("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: 61ddfd666f2e46c98262920c77026d53",
            "appSecret: 33375529044a0bfc94d7abfd682ef4eb9c942"
    })
    @POST("/member/sign/course/all")
    Call<GetUnFinishClassResponse> deleteClass (@Field("current") int current,@Field("size") int size, @Field("userId") int userId);
}
