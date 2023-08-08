package com.example.aclass.Signup;

import com.google.gson.annotations.SerializedName;

public class SignupRequest {
    @SerializedName("password")
    private String password;

    @SerializedName("roleId")
    private String roleId;

    @SerializedName("userName")
    private String userName;

    public SignupRequest(String password, String userName,String roleId) {
        this.password = password;
        this.roleId = roleId;
        this.userName = userName;
    }
}
