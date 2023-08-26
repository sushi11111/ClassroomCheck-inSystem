package com.example.aclass.teacher.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCheckDetailResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String message;

    @SerializedName("data")
    private
    List<Data> data;

    public GetCheckDetailResponse (int code, String message, List<Data> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public class Data {
        private String id;
        private String appKey;
        private String courseSignId;
        private String courseName;
        private String courseAddr;
        private String userId;
        private String username;
        private int status;
        private int signCode;
        private String signTime;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getCourseSignId() {
            return courseSignId;
        }

        public void setCourseSignId(String courseSignId) {
            this.courseSignId = courseSignId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseAddr() {
            return courseAddr;
        }

        public void setCourseAddr(String courseAddr) {
            this.courseAddr = courseAddr;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSignCode() {
            return signCode;
        }

        public void setSignCode(int signCode) {
            this.signCode = signCode;
        }

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
