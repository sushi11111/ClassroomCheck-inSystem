package com.example.aclass.teacher.request;

import com.google.gson.annotations.SerializedName;

public class ChangeUserRequest {
    @SerializedName("avatar")
    private String avatar;

    @SerializedName("collegeName")
    private String collegeName;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private boolean gender;

    @SerializedName("id")
    private Long id;

    @SerializedName("idNumber")
    private Long idNumber;

    @SerializedName("inSchoolTime")
    private Long inSchoolTime;

    @SerializedName("phone")
    private String phone;

    @SerializedName("realName")
    private String realName;

    @SerializedName("userName")
    private String userName;

    public ChangeUserRequest(String collegeName, String email ,boolean gender, Long id, Long idNumber, String phone, String realName, String userName) {
        this.avatar = "https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2023/08/21/fbd0ebb7-3b1c-4290-8e4b-43b64362f621.png";
        this.collegeName = collegeName;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.idNumber = idNumber;
        this.inSchoolTime = 1L;
        this.phone = phone;
        this.realName = realName;
        this.userName = userName;
    }
}
