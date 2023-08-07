package com.example.aclass.Login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String message;

    @SerializedName("data")
    private UserData userData;

    public class UserData {
        @SerializedName("id")
        private String id;

        @SerializedName("appKey")
        private String appKey;

        @SerializedName("userName")
        private String userName;

        @SerializedName("roleId")
        private int roleId;

        @SerializedName("realName")
        private String realName;

        @SerializedName("idNumber")
        private String idNumber;

        @SerializedName("collegeName")
        private String collegeName;

        @SerializedName("gender")
        private boolean gender;

        @SerializedName("phone")
        private String phone;

        @SerializedName("email")
        private String email;

        @SerializedName("avatar")
        private String avatar;

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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getCollegeName() {
            return collegeName;
        }

        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
        }

        public boolean isGender() {
            return gender;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getInSchoolTime() {
            return inSchoolTime;
        }

        public void setInSchoolTime(String inSchoolTime) {
            this.inSchoolTime = inSchoolTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        @SerializedName("inSchoolTime")
        private String inSchoolTime;

        @SerializedName("createTime")
        private String createTime;

        @SerializedName("lastUpdateTime")
        private String lastUpdateTime;
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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
