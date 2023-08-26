package com.example.aclass.teacher.request;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class AddClassRequest {
    @SerializedName("collegeName")
    private String collegeName;

    @SerializedName("courseName")
    private String courseName;

    @SerializedName("coursePhoto")
    private String coursePhoto;

    @SerializedName("endTime")
    private Long endTime;

    @SerializedName("introduce")
    private String introduce;

    @SerializedName("realName")
    private String realName;

    @SerializedName("startTime")
    private Long startTime;

    @SerializedName("userId")
    private int userId;

    @SerializedName("userName")
    private String userName;

    public AddClassRequest(String collegeName, String courseName, String coursePhoto, int learnTime, String introduce, String realName, int startTime, int userId, String userName) {
        this.collegeName = collegeName;
        this.courseName = courseName;
        this.coursePhoto = coursePhoto;
//        this.endTime = endTime;
        this.introduce = introduce;
        this.realName = realName;
//        this.startTime = startTime;
        this.userId = userId;
        this.userName = userName;
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        // 加startTime天
        calendar.add(Calendar.DAY_OF_MONTH, startTime);
        this.startTime = calendar.getTimeInMillis();
        //再加learnTime天
        calendar.add(Calendar.DAY_OF_MONTH, learnTime);
        this.endTime = calendar.getTimeInMillis();
    }
}
