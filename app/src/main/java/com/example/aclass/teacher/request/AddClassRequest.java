package com.example.aclass.teacher.request;

import com.google.gson.annotations.SerializedName;

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

    public AddClassRequest(String collegeName, String courseName, String coursePhoto, Long endTime, String introduce, String realName, Long startTime, int userId, String userName) {
        this.collegeName = collegeName;
        this.courseName = courseName;
        this.coursePhoto = coursePhoto;
        this.endTime = endTime;
        this.introduce = introduce;
        this.realName = realName;
        this.startTime = startTime;
        this.userId = userId;
        this.userName = userName;
    }
}
