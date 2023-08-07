package com.example.aclass.Signup;

import com.google.gson.annotations.SerializedName;

public class SignupResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String message;

    @SerializedName("data")
    private Object data;

    public SignupResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
