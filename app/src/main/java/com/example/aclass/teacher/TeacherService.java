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
            "appId: a3981d6f1cb7451e858fafe3b4e0bb7c",
            "appSecret: 6002952ec1e29b6a047d29c00ac704ed0802f"
    })
    @POST("/member/sign/course/teacher")
    Call<AddClassResponse> addClass (@Body AddClassRequest request);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: a3981d6f1cb7451e858fafe3b4e0bb7c",
            "appSecret: 6002952ec1e29b6a047d29c00ac704ed0802f"
    })
    @GET("/member/sign/course/all")
    Call<GetClassListResponse> getClass (@Query("current") int current, @Query("size") int size);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: a3981d6f1cb7451e858fafe3b4e0bb7c",
            "appSecret: 6002952ec1e29b6a047d29c00ac704ed0802f"
    })
    @DELETE("/member/sign/course/teacher")
    Call<DeleteClassResponse> deleteClass (@Query("courseId") int courseId, @Query("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: a3981d6f1cb7451e858fafe3b4e0bb7c",
            "appSecret: 6002952ec1e29b6a047d29c00ac704ed0802f"
    })
    @GET("/member/sign/course/teacher/unfinished")
    Call<GetUnFinishClassResponse> getUnFinishClass (@Query("current") int current, @Query("size") int size,  @Query("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: a3981d6f1cb7451e858fafe3b4e0bb7c",
            "appSecret: 6002952ec1e29b6a047d29c00ac704ed0802f"
    })
    @GET("/member/sign/course/teacher/finished")
    Call<GetFinishClassResponse> getFinishClass (@Query("current") int current, @Query("size") int size, @Query("userId") int userId);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: a3981d6f1cb7451e858fafe3b4e0bb7c",
            "appSecret: 6002952ec1e29b6a047d29c00ac704ed0802f"
    })
    @POST("/member/sign/course/teacher/initiate")
    Call<CheckingResponse> startChecking (@Body CheckingRequest request);

    @Headers({
            "Accept: application/json, text/plain, */*",
            "Content-Type: application/json",
            "appId: a3981d6f1cb7451e858fafe3b4e0bb7c",
            "appSecret: 6002952ec1e29b6a047d29c00ac704ed0802f"
    })
    @GET("/member/sign/course/teacher/page")
    Call<GetCheckDetailResponse> getCheckDetail (@Query("courseId") int courseId, @Query("userId") int userId);
}
