package com.example.aclass.teacher.request;

import com.google.gson.annotations.SerializedName;

public class CheckingRequest {
    @SerializedName("beginTime")
    private Long beginTime;

    @SerializedName("courseAddr")
    private String courseAddr;

    @SerializedName("courseId")
    private int courseId;

    @SerializedName("courseName")
    private String courseName;

    @SerializedName("endTime")
    private Long endTime;

    @SerializedName("signCode")//输入字符串
    private String signCode;

    @SerializedName("total")//输入字符串
    private String total;

    @SerializedName("userId")
    private int userId;
}
