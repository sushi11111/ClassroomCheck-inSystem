package com.example.aclass.teacher.request;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

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

    public CheckingRequest(int minutes , String courseAddr, int courseId, String courseName,String signCode, String total, int userId) {
        this.beginTime = System.currentTimeMillis();
        this.courseAddr = courseAddr;
        this.courseId = courseId;
        this.courseName = courseName;
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        long currentTimeMillis = calendar.getTimeInMillis();
       // 增加minutes分钟
        calendar.add(Calendar.MINUTE, minutes);
        this.endTime = calendar.getTimeInMillis();;
        this.signCode = signCode;
        this.total = total;
        this.userId = userId;
    }
}
