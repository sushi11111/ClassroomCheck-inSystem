package com.example.aclass.student.CheckIn;

import com.google.gson.annotations.SerializedName;

    public class CheckInRequest {
        @SerializedName("signCode")
        private Integer signCode;

        @SerializedName("userId")
        private Integer userId;

        @SerializedName("userSignId")
        private Integer userSignId;

        public CheckInRequest(Integer signCode, Integer userId, Integer userSignId) {
            this.signCode = signCode;
            this.userId = userId;
            this.userSignId = userSignId;
        }

        public Integer getSignCode() {
            return signCode;
        }

        public void setSignCode(Integer signCode) {
            this.signCode = signCode;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getUserSignId() {
            return userSignId;
        }

        public void setUserSignId(Integer userSignId) {
            this.userSignId = userSignId;
        }
    }

